package com.wanjuuuuu.androiddictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.databinding.ListItemTermBinding

class TermAdapter(val onClick: (term: Term) -> Unit) :
    ListAdapter<Term, TermAdapter.TermViewHolder>(TermDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        return TermViewHolder(
            ListItemTermBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
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
            binding.itemClickListener = View.OnClickListener { onClick(binding.term!!) }
            binding.bookmarkClickListener =
                View.OnClickListener { it.apply { isSelected = !isSelected } }
        }

        fun bind(item: Term) {
            binding.term = item
            binding.bookmarkButton.isSelected = false // set to false
        }
    }
}

private class TermDiffCallback : DiffUtil.ItemCallback<Term>() {
    override fun areItemsTheSame(oldItem: Term, newItem: Term): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Term, newItem: Term): Boolean {
        return oldItem == newItem
    }
}