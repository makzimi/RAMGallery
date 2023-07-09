package com.makzimi.ramgallery.gallery.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.makzimi.ramgallery.R
import com.makzimi.ramgallery.databinding.FragmentCharactersBinding
import com.makzimi.ramgallery.di.Injector
import androidx.fragment.app.viewModels

class CharactersFragment: Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels(
        factoryProducer = { Injector.provideViewModelFactory(requireContext()) }
    )

    private val adapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uiRecyclerView.adapter = adapter
        binding.uiRecyclerView.layoutManager = LinearLayoutManager(context)

        subscribeUI()

        binding.uiSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUI() {
        viewModel.characters.observe(viewLifecycleOwner) {
            if (it?.size != 0) {
                showUIContent()
                adapter.submitList(it)
            }
        }

        viewModel.errors.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
        }

        viewModel.isLoadingState.observe(viewLifecycleOwner) {
            if (adapter.itemCount == 0) {
                if (it) {
                    showUILoading()
                } else {
                    binding.uiMessage.text = getString(R.string.message_empty_string)
                    showUIMessage()
                }
            }
        }
    }

    private fun showUILoading() {
        binding.uiRecyclerView.visibility = View.GONE
        binding.uiProgressBar.visibility = View.VISIBLE
        binding.uiMessage.visibility = View.GONE
        binding.uiSwipeRefreshLayout.isRefreshing = false
    }

    private fun showUIContent() {
        binding.uiRecyclerView.visibility = View.VISIBLE
        binding.uiProgressBar.visibility = View.GONE
        binding.uiMessage.visibility = View.GONE
        binding.uiSwipeRefreshLayout.isRefreshing = false
    }

    private fun showUIMessage() {
        binding.uiRecyclerView.visibility = View.GONE
        binding.uiProgressBar.visibility = View.GONE
        binding.uiMessage.visibility = View.VISIBLE
        binding.uiSwipeRefreshLayout.isRefreshing = false
    }
}