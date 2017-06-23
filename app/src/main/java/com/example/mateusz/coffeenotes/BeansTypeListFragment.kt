package com.example.mateusz.coffeenotes

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.UUID

import android.app.Activity.RESULT_OK
import android.support.v7.widget.DividerItemDecoration
import android.widget.ImageButton
import butterknife.bindView

class BeansTypeListFragment : ListenableFragment() {

    private lateinit var menu: Menu
    private var isInEditMode = false

    private val beansTypeDataManager: BeansTypeDataManager by lazy {
        BeansTypeDataManager.instance(context)
    }

    private val beansTypesRecyclerView: RecyclerView by bindView(R.id.beans_types_recycler_view)
    private lateinit var beansTypeAdapter: BeansTypeAdapter
    private var highlightedBeansTypeId: UUID? = null

    private lateinit var onBeansTypeSelectedListener: OnBeansTypeSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            if (args.containsKey(ARG_HIGHLIGHTED_BEANS_TYPE_ID)) {
                highlightedBeansTypeId = args.getSerializable(ARG_HIGHLIGHTED_BEANS_TYPE_ID) as UUID
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_beans_type_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        beansTypesRecyclerView.layoutManager = layoutManager
        beansTypeAdapter = BeansTypeAdapter(beansTypeDataManager.getBeansTypes())
        beansTypesRecyclerView.adapter = beansTypeAdapter
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        beansTypesRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            onBeansTypeSelectedListener = context as OnBeansTypeSelectedListener
        } catch (e: ClassCastException) {
            onNonListenerContextAttached(context)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_beans_type_list_menu, menu)
        this.menu = menu!!
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_list_new_item -> {
                val intent = BeansTypeActivity.newIntent(context)
                startActivityForResult(intent, EDIT_BEANS_TYPE_REQUEST)
                setEditMode(false)
                return true
            }
            R.id.menu_item_list_start_edit -> {
                setEditMode(true)
                return true
            }
            R.id.menu_item_list_finish_edit -> {
                setEditMode(false)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_BEANS_TYPE_REQUEST) {
            if (resultCode == RESULT_OK) {
                beansTypeAdapter.setBeansTypeList(beansTypeDataManager.getBeansTypes())
                beansTypeAdapter.notifyDataSetChanged()
            }
        }
    }

    interface OnBeansTypeSelectedListener {
        fun onBeansTypeSelected(beansType: BeansType)
    }

    private fun setEditMode(enabled: Boolean) {
        isInEditMode = enabled
        menu.findItem(R.id.menu_item_list_start_edit).isVisible = !enabled
        menu.findItem(R.id.menu_item_list_finish_edit).isVisible = enabled
        menu.findItem(R.id.menu_item_list_new_item).isVisible = enabled
        beansTypeAdapter.notifyDataSetChanged()
    }

    private inner class BeansTypeAdapter(private var beansTypesList: List<BeansType>)
        : RecyclerView.Adapter<BeansTypeAdapter.BeansTypeViewHolder>() {

        fun setBeansTypeList(beansTypeList: List<BeansType>) {
            beansTypesList = beansTypeList
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                BeansTypeAdapter.BeansTypeViewHolder {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.item_beans_type_row, parent, false)
            return BeansTypeViewHolder(view)
        }

        override fun onBindViewHolder(
                holder: BeansTypeAdapter.BeansTypeViewHolder, position: Int) {
            holder.bindBeansType(beansTypesList[position])
        }

        override fun onViewRecycled(holder: BeansTypeViewHolder?) {
            holder?.recycle()
        }

        override fun getItemCount(): Int {
            return beansTypesList.size
        }

        private inner class BeansTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val beansNameTextView: TextView =
                    itemView.findViewById(R.id.item_beans_type_row_name_text_view) as TextView
            private val beansCountryTextView: TextView =
                    itemView.findViewById(R.id.item_beans_type_row_country_text_view) as TextView
            private val removeButton: ImageButton =
                    itemView.findViewById(R.id.item_beans_type_row_remove_button) as ImageButton
            private lateinit var beansType: BeansType

            init {
                itemView.setOnClickListener {
                    onRowClicked()
                }
                removeButton.setOnClickListener {
                    // TODO: remove this tight coupling to the data manager. Create a recyclerView,
                    // which operates on Cursor instead.
                    beansTypeDataManager.removeBeansType(beansTypesList[adapterPosition])
                    setBeansTypeList(beansTypeDataManager.getBeansTypes())
                    notifyItemRemoved(adapterPosition)
                }
            }

            fun bindBeansType(beansType: BeansType) {
                this.beansType = beansType
                updateViewHolder()
            }

            private fun onRowClicked() {
                if (!isInEditMode) {
                    onBeansTypeSelectedListener.onBeansTypeSelected(beansType)
                } else {
                    val intent = BeansTypeActivity.newIntent(context, beansType.id)
                    startActivityForResult(intent, EDIT_BEANS_TYPE_REQUEST)
                }
            }

            private fun updateViewHolder() {
                beansNameTextView.text = beansType.name
                beansCountryTextView.text = beansType.country
                if (beansType.id == highlightedBeansTypeId) {
                    beansNameTextView.setTypeface(null, Typeface.BOLD)
                }
                removeButton.visibility = if (isInEditMode) View.VISIBLE else View.INVISIBLE
            }

            fun recycle() {
                if (beansType.id == highlightedBeansTypeId) {
                    beansNameTextView.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    companion object {
        private val ARG_HIGHLIGHTED_BEANS_TYPE_ID = "highlighted_beans_type_id"
        private val EDIT_BEANS_TYPE_REQUEST = 1

        fun newInstance(highlightedBeansTypeId: UUID): BeansTypeListFragment {
            val args = Bundle()
            args.putSerializable(ARG_HIGHLIGHTED_BEANS_TYPE_ID, highlightedBeansTypeId)
            val beansTypeListFragment = BeansTypeListFragment()
            beansTypeListFragment.arguments = args
            return beansTypeListFragment
        }

        fun newInstance(): BeansTypeListFragment {
            return BeansTypeListFragment()
        }
    }

}
