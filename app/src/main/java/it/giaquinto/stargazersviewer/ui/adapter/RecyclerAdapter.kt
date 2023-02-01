package it.giaquinto.stargazersviewer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import it.giaquinto.stargazersviewer.BR
import it.giaquinto.stargazersviewer.R
import it.giaquinto.stargazersviewer.databinding.GitHubItemBinding
import it.giaquinto.stargazersviewer.data.model.UserInfoModel

class RecyclerAdapter(var list: List<UserInfoModel>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
        RecyclerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.git_hub_item, parent, false
            )
        )

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) =
        holder.bind(list[position])

    inner class RecyclerViewHolder(private var binding: GitHubItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserInfoModel) = with(binding) {
            setVariable(BR.userDataset, user)
            executePendingBindings()
        }

    }

}