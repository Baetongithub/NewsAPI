package kg.news.newsapi.ui.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import kg.news.newsapi.R
import kg.news.newsapi.base.BaseFragment
import kg.news.newsapi.data.model.Article
import kg.news.newsapi.data.model.categoriesmodel.CategoriesModel
import kg.news.newsapi.data.remote.network.Status
import kg.news.newsapi.databinding.FragmentHomeBinding
import kg.news.newsapi.extensions.toast
import kg.news.newsapi.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    /**Categories*/
    private val listCategories = arrayListOf<CategoriesModel>()
    private val adapterCategories: HomeCategoriesAdapter by lazy {
        HomeCategoriesAdapter(listCategories,
            this::onCLickCategories)
    }

    /**Main News*/
    private val listMainNews = arrayListOf<Article>()
    private val adapterMainNews: MainNewsAdapter by lazy { MainNewsAdapter(listMainNews, this::onClickNews) }

    private val viewModel: MainViewModel by viewModel()

    override fun checkNetworkConnection() {

    }

    override fun setupUI() {
        vb.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            adapter = adapterCategories
        }

        vb.fabSearch.setOnClickListener {
            if (vb.etSearch.text.isNotEmpty()) {
                loadNewsOnRequest(vb.etSearch.text.toString().trim())
                vb.tvToolbar.text = vb.etSearch.text.toString()
            } else toast("Empty request")
        }

        vb.recyclerViewMainNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    hideKeyBoard()
                }
            }
        })
    }

    override fun livedata() {

        listCategories.clear()
        listCategories.add(CategoriesModel("All"))
        listCategories.add(CategoriesModel("Music"))
        listCategories.add(CategoriesModel("Sports"))
        listCategories.add(CategoriesModel("Technology"))
        listCategories.add(CategoriesModel("Business"))
        listCategories.add(CategoriesModel("Science"))
        listCategories.add(CategoriesModel("Health"))
        listCategories.add(CategoriesModel("Entertainment"))

        loadTopRatedNews("ru", null)

    }

    private fun loadTopRatedNews(country: String, category: String?) {
        viewModel.loadTopRatedNews(country, category).observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                    listMainNews.clear()
                    if (resource.data != null)
                        listMainNews.addAll(resource.data.articles)

                    setUpRecyclerView()

                    toast(resource.data?.articles?.get(0)!!.title)
                }
                Status.ERROR -> toast("Error " + resource.message)

                Status.LOADING -> {
                    toast("Loading")
                }
            }
        }
    }

    private fun loadNewsOnRequest(req: String) {
        viewModel.loadRequestedNews(req).observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    listMainNews.clear()
                    resource.data?.articles?.let { listMainNews.addAll(it) }

                    setUpRecyclerView()

                    toast(resource.data?.articles?.get(0)!!.title)
                }
                Status.ERROR -> toast("Error " + resource.message)
                Status.LOADING -> {
                    toast("Loading")
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        vb.recyclerViewMainNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMainNews
        }
    }

    private fun onClickNews(items: Article) {
        val sendUrl = Bundle()
        sendUrl.putSerializable(Constants.SEND_NEWS_URL, items.url)
        findNavController().navigate(R.id.action_homeFragment_to_detailedNewsFragment, sendUrl)
    }

    private fun onCLickCategories(e: CategoriesModel) {
        if (e.name == "All") {
            loadTopRatedNews("ru", "general")
        } else {
            loadTopRatedNews("ru", e.name.replaceFirstChar { it.lowercase() })
        }
    }
}