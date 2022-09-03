package com.wanjuuuuu.androiddictionary.adapters

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
        return TermItemViewHolder(
            ListTermItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TermItemViewHolder, position: Int) {
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
            binding.term = item
            binding.bookmarkButton.bindOnToggledListener { toggled ->
                onClickBookmark(item.id, toggled)
            }
        }
    }
}

private class ListItemDiffCallback : DiffUtil.ItemCallback<TermListItem>() {

    override fun areItemsTheSame(oldItem: TermListItem, newItem: TermListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TermListItem, newItem: TermListItem): Boolean {
        return oldItem.id == newItem.id
    }
}