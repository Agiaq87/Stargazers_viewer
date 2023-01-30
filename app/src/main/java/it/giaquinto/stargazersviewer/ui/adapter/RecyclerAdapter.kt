package it.giaquinto.stargazersviewer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.giaquinto.stargazersviewer.databinding.GitHubItemBinding
import it.giaquinto.stargazersviewer.data.model.UserInfoModel
import it.giaquinto.stargazersviewer.ui.holder.RecyclerViewHolder

class RecyclerAdapter(private val list: List<UserInfoModel>): RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
        RecyclerViewHolder(
            GitHubItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
        holder.bind(list[position])
}