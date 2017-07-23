package com.example.mateusz.coffeenotes.view

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageButton
import butterknife.bindView
import com.example.mateusz.coffeenotes.R

/**
 * Base class for Fragments which show a list of items.
 *
 * It uses RecyclerView for showing list.
 *
 * Every item:
 *  - is by default removable.
 *  - Contains a content view container which is supposed to be implemented and returned by child
 *      classes.
 */
abstract class EditableListFragment<T: Any> : Fragment() {
    protected lateinit var menu: Menu
    protected var isInEditMode = false
    protected val recyclerView: RecyclerView by bindView(R.id.recycler_view)
    protected lateinit var adapter: EditableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_editable_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EditableListAdapter(getDataList())
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.list_menu, menu)
        this.menu = menu!!
    }

    open protected fun setEditMode(enabled: Boolean) {
        isInEditMode = enabled
        menu.findItem(R.id.menu_item_list_start_edit).isVisible = !enabled
        menu.findItem(R.id.menu_item_list_finish_edit).isVisible = enabled
        menu.findItem(R.id.menu_item_list_new_item).isVisible = enabled
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_list_start_edit -> {
                setEditMode(true)
                return true
            }
            R.id.menu_item_list_finish_edit -> {
                setEditMode(false)
                return true
            }
            R.id.menu_item_list_new_item -> {
                setEditMode(false)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun getDataList(): List<T>

    protected abstract fun createContentViewHolder(): ContentViewHolder<T>

    protected abstract fun onRemoveContentViewHolder(contentViewHolder: ContentViewHolder<T>)

    protected inner class EditableListAdapter(var data: List<T>)
        : RecyclerView.Adapter<EditableListAdapter.RemovableViewHolder>() {

        fun setDataList(data: List<T>) {
            this.data = data
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: RemovableViewHolder, position: Int) {
            holder.bindData(data[position])
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RemovableViewHolder {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.item_removable_row, parent, false) as ViewGroup
            return RemovableViewHolder(view)
        }

        override fun onViewRecycled(holder: RemovableViewHolder) {
            holder.recycle()
        }

        inner class RemovableViewHolder(itemView: ViewGroup)
            : RecyclerView.ViewHolder(itemView) {
            private val removeButton: ImageButton =
                    itemView.findViewById(R.id.item_row_remove_button) as ImageButton
            private val contentViewHolder: ContentViewHolder<T> = createContentViewHolder()

            init {
                val contentViewContainer =
                        itemView.findViewById(R.id.content_view_container) as ViewGroup
                contentViewContainer.addView(contentViewHolder.view)

                itemView.setOnClickListener {
                    contentViewHolder.onClicked()
                }

                removeButton.setOnClickListener {
                    onRemoveContentViewHolder(contentViewHolder)
                    notifyItemRemoved(adapterPosition)
                }
            }

            fun bindData(data: T) {
                contentViewHolder.bindData(data)
                updateEditMode(isInEditMode)
            }

            fun updateEditMode(isInEditMode: Boolean) {
                removeButton.visibility = if (isInEditMode) View.VISIBLE else View.INVISIBLE
            }

            fun recycle() {
                contentViewHolder.onRecycle()
            }
        }
    }

}