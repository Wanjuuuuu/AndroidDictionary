package com.wanjuuuuu.androiddictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.databinding.ListItemTermBinding

class TermAdapter : ListAdapter<Term, TermAdapter.TermViewHolder>(TermDiffCallback()) {

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

    class TermViewHolder(private val binding: ListItemTermBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clickListener = View.OnClickListener { TODO("change fragment??") }
        }

        fun bind(item: Term) {
            binding.term = item
        }
    }
}

private class TermDiffCallback : DiffUtil.ItemCallback<Term>() {
    override fun areItemsTheSame(oldItem: Term, newItem: Term): Boolean {
        TODO("yet no primary key for Term")
    }

    override fun areContentsTheSame(oldItem: Term, newItem: Term): Boolean {
        return oldItem == newItem
    }
}