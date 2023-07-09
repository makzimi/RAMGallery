package com.makzimi.ramgallery.gallery.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.makzimi.ramgallery.R
import com.makzimi.ramgallery.databinding.ActivityGalleryBinding
import com.makzimi.ramgallery.di.Injector
import com.makzimi.ramgallery.gallery.data.CharacterEntity

class GalleryActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityGalleryBinding

    private lateinit var viewModel: GalleryViewModel
    private val adapter = GalleryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityGalleryBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this, Injector.provideViewModelFactory(this))
            .get(GalleryViewModel::class.java)
        _binding.uiRecyclerView.adapter = adapter
        _binding.uiRecyclerView.layoutManager = LinearLayoutManager(this)

        subscribeUI()

        _binding.uiSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun subscribeUI() {
        viewModel.characters.observe(this, Observer<PagedList<CharacterEntity>> {
            if (it?.size != 0) {
                showUIContent()
                adapter.submitList(it)
            }
        })

        viewModel.errors.observe(this, Observer<String> {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
        })

        viewModel.isLoadingState.observe(this, Observer<Boolean> {
            if (adapter.itemCount == 0) {
                if (it) {
                    showUILoading()
                } else {
                    _binding.uiMessage.text = getString(R.string.message_empty_string)
                    showUIMessage()
                }
            }
        })
    }

    private fun showUILoading() {
        _binding.uiRecyclerView.visibility = View.GONE
        _binding.uiProgressBar.visibility = View.VISIBLE
        _binding.uiMessage.visibility = View.GONE
        _binding.uiSwipeRefreshLayout.isRefreshing = false
    }

    private fun showUIContent() {
        _binding.uiRecyclerView.visibility = View.VISIBLE
        _binding.uiProgressBar.visibility = View.GONE
        _binding.uiMessage.visibility = View.GONE
        _binding.uiSwipeRefreshLayout.isRefreshing = false
    }

    private fun showUIMessage() {
        _binding.uiRecyclerView.visibility = View.GONE
        _binding.uiProgressBar.visibility = View.GONE
        _binding.uiMessage.visibility = View.VISIBLE
        _binding.uiSwipeRefreshLayout.isRefreshing = false
    }
}
