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
import butterknife.bindView

class BeansTypeListFragment : ListenableFragment() {

    private val beansTypesRecyclerView: RecyclerView by bindView(R.id.beans_types_recycler_view)
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

        beansTypesRecyclerView.layoutManager = LinearLayoutManager(context)
        beansTypesRecyclerView.adapter = BeansTypeAdapter(BeansTypeDataManager.instance.beansTypeList)
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_new_beans_type -> {
                val intent = BeansTypeActivity.newIntent(context)
                startActivityForResult(intent, EDIT_BEANS_TYPE_REQUEST)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EDIT_BEANS_TYPE_REQUEST) {
            if (resultCode == RESULT_OK) {
                beansTypesRecyclerView.adapter.notifyDataSetChanged()
            }
        }
    }

    interface OnBeansTypeSelectedListener {
        fun onBeansTypeSelected(beansType: BeansType)
    }

    private inner class BeansTypeAdapter(private val beansTypesList: List<BeansType>) : RecyclerView.Adapter<BeansTypeAdapter.BeansTypeViewHolder>() {

        inner class BeansTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val beansNameTextView: TextView = itemView as TextView
            private lateinit var beansType: BeansType

            init {
                beansNameTextView.setOnClickListener { onBeansTypeSelectedListener.onBeansTypeSelected(beansType) }
            }

            fun bindBeansType(beansType: BeansType) {
                this.beansType = beansType
                updateViewHolder()
            }

            private fun updateViewHolder() {
                beansNameTextView.text = beansType.name
                if (beansType.id == highlightedBeansTypeId) {
                    beansNameTextView.setTypeface(null, Typeface.BOLD)
                }
            }

            fun recycle() {
                if (beansType.id == highlightedBeansTypeId) {
                    beansNameTextView.setTypeface(null, Typeface.NORMAL)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeansTypeAdapter.BeansTypeViewHolder {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
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

}// Required empty public constructor
