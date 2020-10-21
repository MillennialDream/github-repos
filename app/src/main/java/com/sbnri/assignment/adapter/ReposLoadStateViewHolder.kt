package com.sbnri.assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.sbnri.assignment.R
import kotlinx.android.synthetic.main.repos_load_state_footer_view_item.view.*

class ReposLoadStateViewHolder(
    view: View,
    retry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retry_btn.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            itemView.error_msg?.text = loadState.error.localizedMessage
        }
        itemView.progress_bar?.isVisible = loadState is LoadState.Loading
        itemView.retry_btn?.isVisible = loadState !is LoadState.Loading
        itemView.error_msg?.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repos_load_state_footer_view_item, parent, false)
            return ReposLoadStateViewHolder(view, retry)
        }
    }
}
