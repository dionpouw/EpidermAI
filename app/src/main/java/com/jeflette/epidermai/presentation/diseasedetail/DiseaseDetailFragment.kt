package com.jeflette.epidermai.presentation.diseasedetail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jeflette.epidermai.data.local.model.CommonItem
import com.jeflette.epidermai.databinding.FragmentDiseaseDetailBinding
import com.jeflette.epidermai.presentation.adapter.CardItemAdapter
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiseaseDetailFragment : Fragment() {

    private val viewModel: DiseaseDetailViewModel by viewModels()
    private var _binding: FragmentDiseaseDetailBinding? = null
    private val args: DiseaseDetailFragmentArgs by navArgs()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiseaseDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medicineAdapter = CardItemAdapter()
        viewModel.getDiseaseDetail(args.diseaseName)

        binding?.apply {
            diseaseMedicineRecyclerView.apply {
                adapter = medicineAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

        binding?.overviewTextDescription?.movementMethod = ScrollingMovementMethod()
        populateData(binding, medicineAdapter)
    }

    private fun hideVisibility(binding: FragmentDiseaseDetailBinding?) {
        binding?.apply {
            progressBar.visibility = View.VISIBLE
            diseaseImage.visibility = View.GONE
            overviewText.visibility = View.GONE
            overviewTextDescription.visibility = View.GONE
            diseaseMedicine.visibility = View.GONE
            diseaseMedicineRecyclerView.visibility = View.GONE
        }
    }

    private fun showVisibility(binding: FragmentDiseaseDetailBinding?) {
        binding?.apply {
            progressBar.visibility = View.GONE
            diseaseImage.visibility = View.VISIBLE
            overviewText.visibility = View.VISIBLE
            overviewTextDescription.visibility = View.VISIBLE
            diseaseMedicine.visibility = View.VISIBLE
            diseaseMedicineRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun populateData(
        binding: FragmentDiseaseDetailBinding?, medicineAdapter: CardItemAdapter
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.diseaseResult.collectLatest { diseaseResult ->
                    when (diseaseResult) {
                        is Resource.Error -> {}
                        is Resource.Loading -> hideVisibility(binding)
                        is Resource.Success -> {
                            showVisibility(binding)
                            binding?.apply {
                                Glide.with(requireContext()).load(diseaseResult.data?.data?.dssImg)
                                    .into(diseaseImage)
                                overviewTextDescription.text = diseaseResult.data?.data?.dssDesc
                                overviewText.text = diseaseResult.data?.data?.dssName


                                val medicineData = mutableListOf<CommonItem>()
                                if (diseaseResult.data?.data?.medicineDetails?.isNotEmpty() == true) {
                                    for (medicines in diseaseResult.data.data.medicineDetails) {
                                        val realData = CommonItem(
                                            id = diseaseResult.data.data.dssId!!,
                                            name = medicines?.mdcName!!,
                                            image = medicines.mdcImg!!,
                                            desc = medicines.mdcDesc!!
                                        )
                                        medicineData.add(realData)
                                    }
                                    medicineAdapter.addCardItemList(medicineData)
                                } else {
                                    binding.diseaseMedicine.visibility = View.GONE
                                    binding.diseaseMedicineRecyclerView.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}