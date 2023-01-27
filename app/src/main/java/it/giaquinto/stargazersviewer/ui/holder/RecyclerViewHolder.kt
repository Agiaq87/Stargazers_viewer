package it.giaquinto.stargazersviewer.ui.holder

import androidx.recyclerview.widget.RecyclerView
import it.giaquinto.stargazersviewer.databinding.GitHubItemBinding
import it.giaquinto.stargazersviewer.data.model.UserInfo

class RecyclerViewHolder(private val binding: GitHubItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserInfo) = with(binding) {
        userDataset = user
    }
}