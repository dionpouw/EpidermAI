package com.jeflette.epidermai.presentation.diseasedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jeflette.epidermai.databinding.FragmentDiseaseGridBinding
import com.jeflette.epidermai.presentation.adapter.CardItemAdapter
import com.jeflette.epidermai.util.provideDummyMedicine

class DiseaseGridFragment : Fragment() {

    private var _binding: FragmentDiseaseGridBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiseaseGridBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val diseaseAdapter = CardItemAdapter()

        binding?.apply {
            diseaseGridRecyclerView.apply {
                adapter = diseaseAdapter
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
            }
        }
        populateDiseaseList(diseaseAdapter)
    }

    private fun populateDiseaseList(diseaseAdapter: CardItemAdapter) {
        val data = provideDummyMedicine()
        diseaseAdapter.addCardItemList(data)
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

}