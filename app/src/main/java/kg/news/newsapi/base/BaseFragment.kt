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

abstract class BaseFragment<VB : ViewBinding>(
    private val viewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _vb: VB? = null
    val vb get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _vb = viewBinding.invoke(inflater, container, false)
        setupUI()
        livedata()
        checkNetworkConnection()

        return _vb!!.root
    }

    protected open fun setupUI(){}

    protected open fun checkNetworkConnection(){}

    protected open fun livedata(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
        toast("onDestroyView")
    }
}