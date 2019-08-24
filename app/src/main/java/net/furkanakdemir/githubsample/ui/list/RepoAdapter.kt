package net.furkanakdemir.githubsample.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.furkanakdemir.githubsample.R
import net.furkanakdemir.githubsample.ui.data.Repo

class RepoAdapter(private val onRepoCallback: OnRepoCallback) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private val repos = mutableListOf<Repo>()

    override fun getItemCount(): Int = repos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        val repo = repos[position]

        holder.repoNameTextView.text = repo.name
        holder.languageTextView.text = repo.language
        holder.favoriteImageView.isVisible = repo.isFavorite

        holder.itemView.setOnClickListener {
            onRepoCallback.onRepoClicked(repo)
        }
    }

    fun update(newRepos: List<Repo>) {
        val diffResult = DiffUtil.calculateDiff(RepoDiffUtilCallback(repos, newRepos))
        this.repos.clear()
        this.repos.addAll(newRepos)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoNameTextView: TextView = itemView.findViewById(R.id.repoNameTextView)
        val languageTextView: TextView = itemView.findViewById(R.id.languageTextView)
        val favoriteImageView: View = itemView.findViewById(R.id.favoriteImageView)
    }

    inner class RepoDiffUtilCallback constructor(
        private val oldList: List<Repo>,
        private val newList: List<Repo>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    interface OnRepoCallback {
        fun onRepoClicked(repo: Repo)
    }
}
