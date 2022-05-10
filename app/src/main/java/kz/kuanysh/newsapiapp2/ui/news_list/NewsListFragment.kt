package kz.kuanysh.newsapiapp2.ui.news_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kz.kuanysh.newsapiapp2.databinding.FragmentNewsListBinding
import org.koin.android.ext.android.inject

class NewsListFragment : Fragment() {

    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter(
            onArticleClicked = viewModel::onArticleClicked,
            onListEndReached = viewModel::loadNextPage
        )
    }

    private val viewModel: NewsListViewModel by inject()

    private var binding: FragmentNewsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initUI(): Unit? = binding?.run {
        rvArticles.adapter = adapter
        rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.d("taaag", "dx $dx dy $dy")
            }
        })
        srlArticles.setOnRefreshListener {
            viewModel.onRefresh()
        }
        search.doOnTextChanged { text, _, _, _ ->
            viewModel.searchQuery = text.toString()
        }
        Unit
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect(adapter::submitList)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    binding?.srlArticles?.isRefreshing = it
                }
            }
        }
    }

}