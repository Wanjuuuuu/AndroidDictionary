package com.wanjuuuuu.androiddictionary.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanjuuuuu.androiddictionary.data.TermListItem
import com.wanjuuuuu.androiddictionary.databinding.ListTermItemBinding
import com.wanjuuuuu.androiddictionary.fragments.TermListFragmentDirections
import com.wanjuuuuu.androiddictionary.utils.TAG

typealias ListItemAdapters = LinkedHashMap<String, ListItemAdapter>

fun ListItemAdapters.getOrNew(
    category: String,
    onClickBookmark: (id: Long, bookmarked: Boolean) -> Unit
): ListItemAdapter {
    val adapter = get(category) ?: ListItemAdapter(onClickBookmark)
    put(category, adapter)
    return adapter
}

class ListItemAdapter(private val onClickBookmark: (id: Long, bookmarked: Boolean) -> Unit) :
    ListAdapter<TermListItem, ListItemAdapter.TermItemViewHolder>(ListItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermItemViewHolder {
        Log.e(TAG, "onCreateViewHolder")
        return TermItemViewHolder(
            ListTermItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TermItemViewHolder, position: Int) {
        Log.e(TAG, "onBindViewHolder : ${position}")
        val term = getItem(position)
        holder.bind(term)
    }

    inner class TermItemViewHolder(private val binding: ListTermItemBinding) :
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
            Log.e(TAG, "TermItemViewHolder.bind : ${item.name}")
            binding.term = item
            binding.bookmarkClickListener = View.OnClickListener {
                it.run { isSelected = !isSelected }
                onClickBookmark(item.id, it.isSelected)
            }
        }
    }
}

private class ListItemDiffCallback : DiffUtil.ItemCallback<TermListItem>() {

    override fun areItemsTheSame(oldItem: TermListItem, newItem: TermListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TermListItem, newItem: TermListItem): Boolean {
        if (oldItem.bookmarked != newItem.bookmarked) {
            Log.e(TAG, "ListItemDiffCallback.contentDifferent : ${oldItem.name} vs ${newItem.name}")
        }
        return oldItem.bookmarked == newItem.bookmarked // changes to be applied
    }
}