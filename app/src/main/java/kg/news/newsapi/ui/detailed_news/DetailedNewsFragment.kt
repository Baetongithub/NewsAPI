package kg.news.newsapi.ui.detailed_news

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kg.news.newsapi.base.BaseFragment
import kg.news.newsapi.databinding.FragmentDetailedNewsBinding
import kg.news.newsapi.utils.Constants

class DetailedNewsFragment : BaseFragment<FragmentDetailedNewsBinding>(FragmentDetailedNewsBinding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupUI() {

        val newsUrl = arguments?.getSerializable(Constants.SEND_NEWS_URL)

        vb.webViewPMain.loadUrl(newsUrl.toString())

        val webSetting: WebSettings = vb.webViewPMain.settings
        webSetting.javaScriptEnabled = true
        webSetting.loadWithOverviewMode = true

        vb.webViewPMain.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)

            }
        }
    }

    override fun livedata() {

    }

    override fun checkNetworkConnection() {

    }
}