package com.example.samplenews.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplenews.App
import com.example.samplenews.R
import com.example.samplenews.databinding.FragmentNewsListBinding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_news_list.*


class NewsListFragment : Fragment() {

    companion object {
        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }
    }

    private val composeDisposable = CompositeDisposable()
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentNewsListBinding>(
            inflater, R.layout.fragment_news_list,
            container, false
        ).apply {
            lifecycleOwner = this@NewsListFragment
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        activity?.let { activity ->
            newsViewModel = ViewModelProviders.of(activity).get(NewsViewModel::class.java)
            with(newsViewModel) {
                (activity.application as App).component.inject(this)
                dataBinding.viewModel = this
                lifecycle.addObserver(this)
                newsUiNavigationFlowable.subscribe { event ->
                    when (event) {
                        NewsNavEvents.ShowProgressBar -> {
                            progress_circular.visibility = View.VISIBLE
                        }
                        NewsNavEvents.HideProgressBar -> {
                            progress_circular.visibility = View.GONE
                        }
                        NewsNavEvents.Error -> {
                            progress_circular.visibility = View.GONE
                            Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                        }
                        NewsNavEvents.NavigateToNewsDetails -> {
                            selectedItem.let {
                                if (fragmentManager?.findFragmentByTag("NewsDetailsFragment") == null) {
                                    fragmentManager?.beginTransaction()?.let {
                                        it.replace(R.id.content_frame, NewsDetailsFragment.newInstance())
                                        it.addToBackStack("NewsDetailsFragment")
                                        it.commit()
                                    }
                                }
                            }
                        }
                    }
                }

                newsFeeds.observe(this@NewsListFragment, Observer { model ->
                    model.results?.let {
                        dataBinding.recyclerView.adapter = NewsListAdapter(it, this)
                    }
                })
            }
        }

        return dataBinding.root
    }

    override fun onStop() {
        super.onStop()
        composeDisposable.dispose()
    }
}