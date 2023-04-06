package kg.news.newsapi.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.news.newsapi.data.model.categoriesmodel.CategoriesModel
import kg.news.newsapi.databinding.ItemCategoriesBinding

class HomeCategoriesAdapter(
    private val list: List<CategoriesModel>,
    private val onItemClick: (CategoriesModel) -> Unit
) : RecyclerView.Adapter<HomeCategoriesAdapter.HomeCategoriesVH>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesVH {
        return HomeCategoriesVH(
            ItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeCategoriesVH, position: Int) {
        holder.onBind(list[position])
        holder.changeSelectedItemBackground(isSelected = selectedPosition == position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class HomeCategoriesVH(private val vb: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(vb.root) {
        fun onBind(items: CategoriesModel) {
            vb.tvCategoryName.text = items.name

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(items)
                    selectedPosition = position
                    notifyDataSetChanged()
                }
            }
        }

        fun changeSelectedItemBackground(isSelected: Boolean) {
            vb.root.isSelected = isSelected
            if (isSelected) {
                vb.tvCategoryName.setTextColor(Color.WHITE)
            } else vb.tvCategoryName.setTextColor(Color.BLACK)
        }
    }
}