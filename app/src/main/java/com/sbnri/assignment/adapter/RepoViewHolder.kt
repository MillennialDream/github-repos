package com.sbnri.assignment.adapter

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.sbnri.assignment.R
import com.sbnri.assignment.model.Repo
import kotlinx.android.synthetic.main.repo_view_item.view.*

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(repo: Repo) {

        this.itemView.context.resources

        itemView.repo_name.text = repo.name

        itemView.repo_description.isVisible = repo.description?.isNotBlank() ?: true
        if (repo.description != null) {
            itemView.repo_description.text = repo.description
        }

        val license = repo.license
        if (license != null) {
            itemView.repo_license.visibility = View.VISIBLE
            itemView.repo_license.text = license.name
        } else {
            itemView.repo_license.visibility = View.GONE
        }

        itemView.repo_open_issue_count.text = repo.openIssuesCount.toString()

        val permissions = repo.permissions
        if (permissions != null) {

            itemView.permissions_group.visibility = View.VISIBLE

            itemView.repo_permission_admin.isVisible = permissions.admin
            itemView.repo_permission_push.isVisible = permissions.push
            itemView.repo_permission_pull.isVisible = permissions.pull

        } else {
            itemView.permissions_group.visibility = View.GONE
        }

        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
            itemView.context.startActivity(intent)
        }

    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}
