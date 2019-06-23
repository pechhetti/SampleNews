package com.example.samplenews.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplenews.R
import com.example.samplenews.data.Result
import com.example.samplenews.databinding.NewsListItemBinding

class NewsListAdapter(private val result: List<Result>, private val viewModel: NewsViewModel) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val listItemBinding = DataBindingUtil.inflate<NewsListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.news_list_item, parent, false
        )
        return RecyclerViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.listItemBinding.model = result[position]
        holder.listItemBinding.viewModel = viewModel
        holder.listItemBinding.executePendingBindings()
    }
}

class RecyclerViewHolder(val listItemBinding: NewsListItemBinding) : RecyclerView.ViewHolder(listItemBinding.root)

