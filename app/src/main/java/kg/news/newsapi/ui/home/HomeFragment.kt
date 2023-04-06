package kg.news.newsapi.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
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
import kg.news.newsapi.utils.KeyboardHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    /**Categories*/
    private val listCategories = arrayListOf<CategoriesModel>()
    private val adapterCategories: HomeCategoriesAdapter by lazy {
        HomeCategoriesAdapter(
            listCategories,
            this::onCLickCategories
        )
    }

    /**Main News*/
    private val listMainNews = arrayListOf<Article>()
    private val adapterMainNews: MainNewsAdapter by lazy {
        MainNewsAdapter(
            listMainNews,
            this::onClickNews
        )
    }

    private val viewModel: MainViewModel by viewModel()

    override fun setupUI() {
        setupCategoriesRecyclerView()
        initSearchNews()
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
                    vb.cardProgressBar.visibility = GONE
                    listMainNews.clear()
                    if (resource.data != null)
                        listMainNews.addAll(resource.data.articles)
                    setUpNewsRecyclerView()
                }
                Status.ERROR -> {
                    vb.cardProgressBar.visibility = GONE
                    toast("Error " + resource.message)
                }
                Status.LOADING -> vb.cardProgressBar.visibility = VISIBLE
            }
        }
    }

    private fun loadNewsOnRequest(req: String) {
        viewModel.loadRequestedNews(req).observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    listMainNews.clear()
                    resource.data?.articles?.let { listMainNews.addAll(it) }
                    setUpNewsRecyclerView()
                    vb.cardProgressBar.visibility = GONE
                }
                Status.ERROR -> {
                    vb.cardProgressBar.visibility = GONE
                    toast("Error " + resource.message)
                }
                Status.LOADING -> vb.cardProgressBar.visibility = VISIBLE
            }
        }
    }

    private fun setUpNewsRecyclerView() = with(vb) {
        recyclerViewMainNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMainNews
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        KeyboardHelper.hideKeyboard(activity)
                    }
                }
            })
        }
    }

    private fun setupCategoriesRecyclerView() = with(vb) {
        recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            adapter = adapterCategories
        }
    }

    private fun initSearchNews() = with(vb) {
        imageSearch.setOnClickListener {
            customSearchView.visibility = VISIBLE
            toolbar.visibility = GONE
            KeyboardHelper.showKeyboard(context, focusOn = vb.etSearch)
        }
        imageBackSearch.setOnClickListener {
            KeyboardHelper.hideKeyboard(activity)
            customSearchView.visibility = GONE
            toolbar.visibility = VISIBLE
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    loadNewsOnRequest(p0.toString())
                    vb.tvToolbar.text = p0
                }
            }
        })
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
        vb.tvToolbar.text = String.format("${context?.getString(R.string.news)} • ${e.name}")
        vb.customSearchView.visibility = GONE
        vb.toolbar.visibility = VISIBLE
    }
}