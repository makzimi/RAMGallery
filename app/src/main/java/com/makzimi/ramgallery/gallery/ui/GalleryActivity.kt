package com.makzimi.ramgallery.gallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.makzimi.ramgallery.R
import com.makzimi.ramgallery.di.Injector
import com.makzimi.ramgallery.model.CharacterEntity
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {

    private lateinit var viewModel: GalleryViewModel
    private val adapter = GalleryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        viewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory(this))
            .get(GalleryViewModel::class.java)
        uiRecyclerView.adapter = adapter
        uiRecyclerView.layoutManager = LinearLayoutManager(this)

        subscribeUI()

        uiSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun subscribeUI() {
        viewModel.characters.observe(this, Observer<PagedList<CharacterEntity>> {
            if(it?.size != 0) {
                showUIContent()
                adapter.submitList(it)
            }
        })

        viewModel.errors.observe(this, Observer<String> {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        })

        viewModel.isLoadingState.observe(this, Observer<Boolean> {
            if(adapter.itemCount == 0) {
                if(it) {
                    showUILoading()
                } else {
                    uiMessage.text = getString(R.string.message_empty_string)
                    showUIMessage()
                }
            }
        })
    }

    private fun showUILoading() {
        uiRecyclerView.visibility = View.GONE
        uiProgressBar.visibility = View.VISIBLE
        uiMessage.visibility = View.GONE
        uiSwipeRefreshLayout.isRefreshing = false
    }

    private fun showUIContent() {
        uiRecyclerView.visibility = View.VISIBLE
        uiProgressBar.visibility = View.GONE
        uiMessage.visibility = View.GONE
        uiSwipeRefreshLayout.isRefreshing = false
    }

    private fun showUIMessage() {
        uiRecyclerView.visibility = View.GONE
        uiProgressBar.visibility = View.GONE
        uiMessage.visibility = View.VISIBLE
        uiSwipeRefreshLayout.isRefreshing = false
    }
}
