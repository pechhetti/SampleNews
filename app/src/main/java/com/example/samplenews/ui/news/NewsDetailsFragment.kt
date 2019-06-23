package com.example.samplenews.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.samplenews.R
import kotlinx.android.synthetic.main.fragment_news_details.view.*

class NewsDetailsFragment : Fragment() {

    companion object {
        fun newInstance(): NewsDetailsFragment {
            return NewsDetailsFragment()
        }
    }

    private lateinit var newsViewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)
        activity?.let { activity ->
            newsViewModel = ViewModelProviders.of(activity).get(NewsViewModel::class.java)
            with(newsViewModel) {
                view.webViewNewsDetails.loadUrl(selectedItem.value?.url)
            }
        }

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                fragmentManager?.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}