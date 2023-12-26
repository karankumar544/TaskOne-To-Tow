package com.radha.taskone.persentation.basic

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.radha.taskone.R
import com.radha.taskone.data.paging.MYPagingDataAdapter
import com.radha.taskone.data.paging.UserComparator
import com.radha.taskone.data.repository.PhotoRepositoryImpl
import com.radha.taskone.databinding.FragmentFirstBinding
import com.radha.taskone.domain.remote.RetrofitInstance
import com.radha.taskone.persentation.PhotoViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var text = "cat"
    private val binding get() = _binding!!
    private lateinit var viewModel: PhotoViewModel
    private val viewModel1: PhotoViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(requireActivity(), null) {
            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle
            ): T {
                return PhotoViewModel(PhotoRepositoryImpl(RetrofitInstance.photos), handle) as T
            }
        }
    }
    private lateinit var adapter: MYPagingDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initAdapter()
        initSwipeToRefresh()

    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MYPagingDataAdapter(UserComparator)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel1.newPhoto.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    viewModel1.showSearchPhoto(searchText)
                } else {
                }
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
