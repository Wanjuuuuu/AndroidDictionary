package com.wanjuuuuu.androiddictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanjuuuuu.androiddictionary.data.TermListItem
import com.wanjuuuuu.androiddictionary.databinding.ListItemTermBinding
import com.wanjuuuuu.androiddictionary.fragments.TermListFragmentDirections

class TermAdapter(private val onClickBookmark: (id: Long, bookmarked: Boolean) -> Unit) :
    ListAdapter<TermListItem, TermAdapter.TermViewHolder>(TermDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        return TermViewHolder(
            ListItemTermBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        val term = getItem(position)
        holder.bind(term)
    }

    inner class TermViewHolder(private val binding: ListItemTermBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemClickListener =
                View.OnClickListener { view -> binding.term?.let { navigateToDetail(view, it) } }
        }

        private fun navigateToDetail(view: View, term: TermListItem) {
            val action =
                TermListFragmentDirections.actionTermListFragmentToTermDetailFragment(term.id)
            view.findNavController().navigate(action)
        }

        fun bind(item: TermListItem) {
            binding.term = item
            binding.bookmarkClickListener = View.OnClickListener {
                it.run { isSelected = !isSelected }
                onClickBookmark(item.id, it.isSelected)
            }
        }
    }
}

private class TermDiffCallback : DiffUtil.ItemCallback<TermListItem>() {
    override fun areItemsTheSame(oldItem: TermListItem, newItem: TermListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TermListItem, newItem: TermListItem): Boolean {
        return oldItem.bookmarked == newItem.bookmarked // changes to be applied
    }
}