package it.giaquinto.stargazersviewer.ui.adapter

import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import it.giaquinto.stargazersviewer.ui.RecyclerViewViewHolder

abstract class RecyclerAdapter<DATA, BINDING: ViewDataBinding>(var list: MutableList<DATA>) : RecyclerView.Adapter<RecyclerViewViewHolder<DATA, BINDING>>() {
    protected val handler: Handler = Handler(Looper.getMainLooper())

    override fun onBindViewHolder(holder: RecyclerViewViewHolder<DATA, BINDING>, position: Int) =
        holder.bind(list[position])

    fun updateList(list: List<DATA>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() = with(list) {
        clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        list.size

}