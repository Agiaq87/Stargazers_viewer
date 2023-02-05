package it.giaquinto.stargazersviewer.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewViewHolder<DATA, BINDING: ViewDataBinding>(binding: BINDING): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(dataset: DATA)

}