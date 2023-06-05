package com.jeflette.epidermai.presentation.disease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.epidermai.databinding.FragmentDiseaseBinding
import com.jeflette.epidermai.presentation.adapter.DiseaseItemAdapter
import com.jeflette.epidermai.util.provideDummyDisease

class DiseaseFragment : Fragment() {

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

        binding?.apply {
            diseaseRecyclerView.apply {
                adapter = diseaseAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(false)
            }
        }

        populateDiseaseList(diseaseAdapter)
    }

    private fun populateDiseaseList(diseaseAdapter: DiseaseItemAdapter) =
        diseaseAdapter.addDiseaseList(provideDummyDisease())

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}