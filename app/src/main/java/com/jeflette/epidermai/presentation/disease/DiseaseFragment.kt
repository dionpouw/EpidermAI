package com.jeflette.epidermai.presentation.disease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.epidermai.databinding.FragmentDiseaseBinding
import com.jeflette.epidermai.presentation.adapter.DiseaseItemAdapter
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DiseaseFragment : Fragment() {

    private val viewModel: DiseaseViewModel by viewModels()
    private var _binding: FragmentDiseaseBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiseaseBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diseaseAdapter = DiseaseItemAdapter()
        viewModel.getAllDiseases()

        binding?.apply {
            diseaseRecyclerView.apply {
                adapter = diseaseAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }

        populateDiseaseList(binding, diseaseAdapter)
    }

    private fun populateDiseaseList(
        binding: FragmentDiseaseBinding?, diseaseAdapter: DiseaseItemAdapter
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.diseaseResult.collectLatest { diseaseResult ->
                    when (diseaseResult) {
                        is Resource.Error -> {}
                        is Resource.Loading -> hideVisibility(binding)
                        is Resource.Success -> {
                            showVisibility(binding)
                            diseaseResult.data?.let { data ->
                                data.data?.let { diseaseAdapter.addDiseaseList(it) }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hideVisibility(binding: FragmentDiseaseBinding?) {
        binding?.apply {
            progressBar.visibility = View.VISIBLE
            searchView.visibility = View.GONE
            diseaseRecyclerView.visibility = View.GONE
        }
    }

    private fun showVisibility(binding: FragmentDiseaseBinding?) {
        binding?.apply {
            progressBar.visibility = View.GONE
            searchView.visibility = View.VISIBLE
            diseaseRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}