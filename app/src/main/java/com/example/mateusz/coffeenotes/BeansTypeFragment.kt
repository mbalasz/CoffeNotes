package com.example.mateusz.coffeenotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.io.File

import android.os.Build
import android.view.ViewTreeObserver
import android.annotation.TargetApi
import android.app.DatePickerDialog
import android.support.v4.app.Fragment
import android.widget.*
import com.example.mateusz.coffeenotes.application.MyApplication
import com.example.mateusz.coffeenotes.database.BeansTypeDataManager
import kotterknife.bindView
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class BeansTypeFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    @Inject lateinit var beansTypeDataManager: BeansTypeDataManager

    private lateinit var beansType: BeansType
    private val beansNameEditText: EditText by bindView(R.id.beans_name_edit_text)
    private val beansCountryEditText: EditText by bindView(R.id.beans_country_edit_text)
    private val roastLevelButton: Button by bindView(R.id.roast_level_button)
    private val dateButton: Button by bindView(R.id.date_picker_button)
    private val beansPhotoImageView: ImageView by bindView(R.id.beans_photo_image_view)
    private val beansPhotoProgressBar: ProgressBar by bindView(R.id.beans_photo_progress_bar)
    private val takePhotoButton: Button by bindView(R.id.take_photo_button)
    private var photoFile: File? = null

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var onBeansTypeEditFinishedListener: OnBeansTypeEditFinishedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as MyApplication).getAppComponent().inject(this)
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
        val noteDate = beansType.date
        datePickerDialog = DatePickerDialog(
                context,
                this,
                noteDate.year,
                noteDate.monthValue,
                noteDate.dayOfMonth)
        dateButton.setOnClickListener {
            datePickerDialog.show()
        }
        updateUi()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onBeansTypeEditFinishedListener = context as OnBeansTypeEditFinishedListener
        } catch (e: ClassCastException) {
            throw RuntimeException(
                    String.format(
                            "%s must implement %s interface",
                            context?.javaClass?.simpleName ?: "[null]",
                            this.javaClass.simpleName))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.discard_or_save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_save -> {
                onSaveBeansType()
                onBeansTypeEditFinishedListener.onBeansTypeSaved()
                return true
            }
            R.id.menu_item_discard -> {
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        beansType.date = LocalDate.of(year, month, dayOfMonth)
        updateDateButtonUi()
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
        // TODO change the way of updating the entity. Instead of getting ui snapshot create a tmp
        // copy of original beanstype and whenever any of the ui elements changes update
        // corresponding fields in the tmp copy. Upon saving, replace the copy with the original.
        beansNameEditText.setText(beansType.name)
        beansCountryEditText.setText(beansType.country)
        roastLevelButton.text = beansType.roastLevel.toString()
        updateDateButtonUi()
    }

    private fun updateDateButtonUi() {
        dateButton.text = beansType.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
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
