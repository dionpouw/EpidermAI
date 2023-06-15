package com.jeflette.epidermai.presentation.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.jeflette.epidermai.data.local.model.CommonItem
import com.jeflette.epidermai.databinding.FragmentMedicineBinding
import com.jeflette.epidermai.presentation.adapter.CardItemAdapter
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicineFragment : Fragment() {

    private val viewModel: MedicineViewModel by viewModels()
    private var _binding: FragmentMedicineBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diseaseAdapter = CardItemAdapter()
        viewModel.getAllDiseases()

        binding?.apply {
            medicineRecyclerView.apply {
                adapter = diseaseAdapter
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(false)
            }
        }
        populateDiseaseList(binding, diseaseAdapter)
    }

    private fun populateDiseaseList(
        binding: FragmentMedicineBinding?, diseaseAdapter: CardItemAdapter
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.diseaseResult.collectLatest { medicineResult ->
                    when (medicineResult) {
                        is Resource.Error -> {}
                        is Resource.Loading -> hideVisibility(binding)
                        is Resource.Success -> {
                            showVisibility(binding)
                            medicineResult.data?.let { data ->
                                val medicineData = mutableListOf<CommonItem>()
                                for (medicines in data.data!!) {
                                    val realData = CommonItem(
                                        id = medicines.mdcId,
                                        name = medicines.mdcName,
                                        image = medicines.mdcImg,
                                        desc = medicines.mdcDesc
                                    )
                                    medicineData.add(realData)
                                }
                                diseaseAdapter.addCardItemList(medicineData)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hideVisibility(binding: FragmentMedicineBinding?) {
        binding?.apply {
            progressBar.visibility = View.VISIBLE
            searchView.visibility = View.GONE
            medicineRecyclerView.visibility = View.GONE
        }
    }

    private fun showVisibility(binding: FragmentMedicineBinding?) {
        binding?.apply {
            progressBar.visibility = View.GONE
            searchView.visibility = View.VISIBLE
            medicineRecyclerView.visibility = View.VISIBLE
        }
    }


    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}