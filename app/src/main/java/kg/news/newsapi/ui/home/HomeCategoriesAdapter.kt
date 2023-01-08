package kg.news.newsapi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.news.newsapi.data.model.categoriesmodel.CategoriesModel
import kg.news.newsapi.databinding.ItemCategoriesBinding

class HomeCategoriesAdapter(private val list: List<CategoriesModel>, private val onItemClick: (CategoriesModel) -> Unit) :
    RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesVH {
        return HomeCategoriesVH(ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeCategoriesVH, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener { onItemClick(list[position]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HomeCategoriesVH(private val itemsVB: ItemCategoriesBinding) : RecyclerView.ViewHolder(itemsVB.root) {
        fun onBind(items: CategoriesModel) {
            itemsVB.tvCategoryName.text = items.name
        }
    }
}