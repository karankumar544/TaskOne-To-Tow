package com.radha.taskone.persentation.basic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.radha.taskone.data.repository.PhotoRepositoryImpl
import com.radha.taskone.databinding.FragmentFirstBinding
import com.radha.taskone.domain.remote.RetrofitInstance
import com.radha.taskone.persentation.PhotoViewModel
import com.radha.taskone.persentation.adapter.RecyclerViewAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoViewModel by viewModels<PhotoViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotoViewModel(PhotoRepositoryImpl(RetrofitInstance.photos)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.photos.observe(requireActivity()) { response ->
            if (response == null) {
                Log.e("ram", "API response is null")
                return@observe
            }

            // Check the HTTP status code
            val statusCode = response.code()
            if (response.isSuccessful) {
                val body = response.body()
                val adapter = RecyclerViewAdapter()
                binding.recyclerView.adapter = adapter
                body?.let { photos ->
                    binding.progress.visibility = View.GONE
                    adapter.setPhoto(photos.photos.photo)
                    adapter.setOnClickListener(object : RecyclerViewAdapter.OnClickListener {
                        override fun onClick(position: Int) {
                            val action =
                                FirstFragmentDirections.actionFirstFragmentToSecondFragment(photos.photos.photo[position])
                            findNavController().navigate(action)
                        }
                    })
                }


            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("ram", "API request failed. Error message: $errorMessage")
                Log.e("ram", "HTTP status code: $statusCode")
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}