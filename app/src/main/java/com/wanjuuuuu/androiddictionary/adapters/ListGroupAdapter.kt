package com.wanjuuuuu.androiddictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanjuuuuu.androiddictionary.data.ListGroup
import com.wanjuuuuu.androiddictionary.databinding.ListGroupItemBinding

class ListGroupAdapter(
    private val onClickCollapse: (listGroup: ListGroup) -> Unit,
    private val onClickBookmark: (id: Long, bookmarked: Boolean) -> Unit
) : ListAdapter<ListGroup, ListGroupAdapter.TermGroupViewHolder>(ListGroupDiffCallback()) {

    private val listItemAdapters = ListItemAdapters()

    override fun submitList(list: List<ListGroup>?) {
        super.submitList(list)
        submitListItems(requireNotNull(list))
    }

    private fun submitListItems(list: List<ListGroup>) {
        list.forEach {
            val adapter = listItemAdapters.getOrNew(it.category, onClickBookmark)
            adapter.submitList(it.terms)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermGroupViewHolder {
        return TermGroupViewHolder(
            ListGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TermGroupViewHolder, position: Int) {
        val group = getItem(position)
        val adapter = getListItemAdapter(group.category)
        holder.bind(group, adapter)
    }

    private fun getListItemAdapter(category: String): ListItemAdapter {
        return listItemAdapters.getOrNew(category, onClickBookmark)
    }

    inner class TermGroupViewHolder(private val binding: ListGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: ListGroup, adapter: ListItemAdapter) {
            binding.category = group.category
            binding.collapsed = group.collapsed
            binding.collapseClickListener = View.OnClickListener { onClickCollapse(group) }
            binding.termList.adapter = adapter
        }
    }
}

private class ListGroupDiffCallback : DiffUtil.ItemCallback<ListGroup>() {

    override fun areItemsTheSame(oldItem: ListGroup, newItem: ListGroup): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(oldItem: ListGroup, newItem: ListGroup): Boolean {
        return oldItem.collapsed == newItem.collapsed
    }
}