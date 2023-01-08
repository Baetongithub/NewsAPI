package kg.news.newsapi.data.model.top_rated_categories

data class TopRatedCategoriesModel(
    val sources: List<Source>,
    val status: String
)