package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.bindView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.io.File

import java.util.UUID
import android.os.Build
import android.view.ViewTreeObserver
import android.annotation.TargetApi

class BeansTypeFragment : ListenableFragment() {
    private lateinit var beansType: BeansType
    private val beansNameEditText: EditText by bindView(R.id.beans_name_edit_text)
    private val beansCountryEditText: EditText by bindView(R.id.beans_country_edit_text)
    private val roastLevelButton: Button by bindView(R.id.roast_level_button)
    private val beansPhotoImageView: ImageView by bindView(R.id.beans_photo_image_view)
    private val beansPhotoProgressBar: ProgressBar by bindView(R.id.beans_photo_progress_bar)
    private val takePhotoButton: Button by bindView(R.id.take_photo_button)
    private val beansTypeDataManager: BeansTypeDataManager by lazy {
        BeansTypeDataManager.instance(context)
    }
    private var photoFile: File? = null

    private lateinit var onBeansTypeEditFinishedListener: OnBeansTypeEditFinishedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            val beansTypeId = arguments.getSerializable(ARG_BEANS_TYPE_ID) as UUID
            beansType = beansTypeDataManager.getBeansTypeById(beansTypeId) ?: BeansType()
        } else {
            beansType = BeansType()
        }
        photoFile = beansTypeDataManager.getPhotoFile(beansType)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_beans_type, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roastLevelButton.setOnClickListener {
            val roastPickerFragment = RoastPickerFragment.newInstance(beansType.roastLevel)
            roastPickerFragment.setTargetFragment(this@BeansTypeFragment, REQUEST_ROAST_PICKER)
            roastPickerFragment.show(fragmentManager, TAG_DIALOG_ROAST_PICKER)
        }
        takePhotoButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // TODO: Don't override current picture location until save button is clicked.
            // Instead save new picture in a cached location.
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        }
        beansPhotoImageView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        updatePhotoView()
                        removeOnGlobalLayoutListener(beansPhotoImageView, this)
                    }
                })
        updateUi()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onBeansTypeEditFinishedListener = context as OnBeansTypeEditFinishedListener
        } catch (e: ClassCastException) {
            onNonListenerContextAttached(context)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_beans_type_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_save_beans_type -> {
                onSaveBeansType()
                onBeansTypeEditFinishedListener.onBeansTypeSaved()
                return true
            }
            R.id.menu_item_discard_beans_type -> {
                onBeansTypeEditFinishedListener.onBeansTypeDiscarded()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_ROAST_PICKER -> {
                data?.let {
                    roastLevelButton.text =
                            data.getIntExtra(RoastPickerFragment.EXTRA_ROAST_LEVEL, 0).toString()
                }
            }
            REQUEST_TAKE_PHOTO -> updatePhotoView()
        }
    }

    private fun onSaveBeansType() {
        beansType.name = beansNameEditText.text.toString()
        beansType.country = beansCountryEditText.text.toString()
        beansType.roastLevel = roastLevelButton.text.toString().toInt()
        beansTypeDataManager.saveBeansType(beansType)
    }

    private fun updatePhotoView() {
        onStartUpdatingPhotoView()
        val photoFile = photoFile
        if (photoFile != null && photoFile.exists()) {
            async(UI) {
                val asyncGetScaledBitmap = async(CommonPool) {
                    PictureUtils.getScaledBitmap(
                            photoFile.path, beansPhotoImageView.width, beansPhotoImageView.height)
                }
                val bitmap = asyncGetScaledBitmap.await()
                beansPhotoImageView.setImageBitmap(bitmap)
                onFinishUpdatingPhotoView()
            }
        } else {
            beansPhotoImageView.setImageResource(R.drawable.ic_image)
            onFinishUpdatingPhotoView()
        }
    }

    private fun onStartUpdatingPhotoView() {
        beansPhotoImageView.visibility = View.INVISIBLE
        beansPhotoProgressBar.visibility = View.VISIBLE
    }

    private fun onFinishUpdatingPhotoView() {
        beansPhotoProgressBar.visibility = View.GONE
        beansPhotoImageView.visibility = View.VISIBLE
    }

    private fun updateUi() {
        beansNameEditText.setText(beansType.name)
        roastLevelButton.text = beansType.roastLevel.toString()
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun removeOnGlobalLayoutListener(v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.viewTreeObserver.removeGlobalOnLayoutListener(listener)
        } else {
            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    internal interface OnBeansTypeEditFinishedListener {
        fun onBeansTypeSaved()

        fun onBeansTypeDiscarded()
    }

    companion object {
        private val ARG_BEANS_TYPE_ID = "beans_type_id"
        private val TAG_DIALOG_ROAST_PICKER = "DialogRoastPicker"
        private val REQUEST_ROAST_PICKER = 0
        private val REQUEST_TAKE_PHOTO = 1

        fun newInstance(beansTypeId: UUID): BeansTypeFragment {
            val args = Bundle()
            args.putSerializable(ARG_BEANS_TYPE_ID, beansTypeId)
            val fragment = newInstance()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): BeansTypeFragment {
            return BeansTypeFragment()
        }
    }
}
