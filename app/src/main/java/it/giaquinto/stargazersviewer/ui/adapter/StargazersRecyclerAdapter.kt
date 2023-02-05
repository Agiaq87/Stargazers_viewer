package it.giaquinto.stargazersviewer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import it.giaquinto.stargazersviewer.data.model.StargazersModel
import it.giaquinto.stargazersviewer.databinding.StargazersItemBinding
import it.giaquinto.stargazersviewer.ui.RecyclerViewViewHolder

class StargazersRecyclerAdapter(
    list: MutableList<StargazersModel>,
    val onClickListener: (StargazersModel) -> Unit
) :
    RecyclerAdapter<StargazersModel, StargazersItemBinding>(list) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewViewHolder<StargazersModel, StargazersItemBinding> = StargazersViewHolder(
        StargazersItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class StargazersViewHolder(private val _binding: StargazersItemBinding) :
        RecyclerViewViewHolder<StargazersModel, StargazersItemBinding>(_binding) {
        override fun bind(dataset: StargazersModel) {
            _binding.dataset = dataset
            handler.post {
                Picasso.get().load(dataset.avatarUrl).into(_binding.avatarView)
                _binding.avatarView.clipToOutline = true
            }
            _binding.imageView.setOnClickListener {
                onClickListener.invoke(dataset)
            }
        }
    }
}