package com.wanjuuuuu.androiddictionary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanjuuuuu.androiddictionary.data.ListGroup
import com.wanjuuuuu.androiddictionary.databinding.ListGroupItemBinding

class ListGroupAdapter(private val onClickBookmark: (id: Long, bookmarked: Boolean) -> Unit) :
    ListAdapter<ListGroup, ListGroupAdapter.TermGroupViewHolder>(ListGroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermGroupViewHolder {
        return TermGroupViewHolder(
            ListGroupItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TermGroupViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group)
    }

    inner class TermGroupViewHolder(private val binding: ListGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: ListGroup) {
            binding.category = group.category

            val adapter = ListItemAdapter(onClickBookmark)
            binding.termList.adapter = adapter

            adapter.submitList(group.terms)
        }
    }
}

private class ListGroupDiffCallback : DiffUtil.ItemCallback<ListGroup>() {
    override fun areItemsTheSame(oldItem: ListGroup, newItem: ListGroup): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListGroup, newItem: ListGroup): Boolean {
        return oldItem == newItem
    }
}