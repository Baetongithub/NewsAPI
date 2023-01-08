package kg.news.newsapi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.news.newsapi.data.model.Article
import kg.news.newsapi.databinding.ItemMainNewsBinding
import kg.news.newsapi.extensions.glide

class MainNewsAdapter(private val list: List<Article>, private val onClick: (Article) -> Unit) :
    RecyclerView.Adapter<MainNewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemMainNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener { onClick(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NewsViewHolder(private val itemBinding: ItemMainNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(newsModel: Article) {
            itemBinding.imageMainNews.glide(newsModel.urlToImage)
            itemBinding.tvNewsHeadline.text = newsModel.title
        }
    }
}