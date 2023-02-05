package it.giaquinto.stargazersviewer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import it.giaquinto.stargazersviewer.data.model.RepoUserModel
import it.giaquinto.stargazersviewer.databinding.RepoUserLayoutBinding
import it.giaquinto.stargazersviewer.ui.RecyclerViewViewHolder

class RepoUserRecyclerAdapter(
    list: MutableList<RepoUserModel>
) :
    RecyclerAdapter<RepoUserModel, RepoUserLayoutBinding>(list) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewViewHolder<RepoUserModel, RepoUserLayoutBinding> = RepoUserViewHolder(
        RepoUserLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class RepoUserViewHolder(private val _binding: RepoUserLayoutBinding) :
        RecyclerViewViewHolder<RepoUserModel, RepoUserLayoutBinding>(_binding) {
        override fun bind(dataset: RepoUserModel) {
            _binding.dataset = dataset
        }
    }
}