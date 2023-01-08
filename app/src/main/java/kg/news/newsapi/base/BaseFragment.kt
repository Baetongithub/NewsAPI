package kg.news.newsapi.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kg.news.newsapi.extensions.toast

abstract class BaseFragment<VB : ViewBinding>(private val viewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var binding: VB? = null
    val vb get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = viewBinding.invoke(inflater, container, false)
        setupUI()
        livedata()
        checkNetworkConnection()

        return binding!!.root
    }

    abstract fun setupUI()

    abstract fun checkNetworkConnection()

    abstract fun livedata()

    fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity?.currentFocus
        if (view == null) view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        toast("onDestroyView")
    }
}