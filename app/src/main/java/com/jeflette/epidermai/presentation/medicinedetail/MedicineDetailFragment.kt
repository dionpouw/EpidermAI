package com.jeflette.epidermai.presentation.medicinedetail

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
import com.bumptech.glide.Glide
import com.jeflette.epidermai.databinding.FragmentMedicineDetailBinding
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MedicineDetailFragment : Fragment() {

    private val viewModel: MedicineDetailViewModel by viewModels()
    private val args: MedicineDetailFragmentArgs by navArgs()
    private var _binding: FragmentMedicineDetailBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMedicineDetail(args.medicineName)

        populateData(binding)
        binding?.overviewTextDescription?.movementMethod = ScrollingMovementMethod()
    }

    private fun populateData(binding: FragmentMedicineDetailBinding?) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.medicineResult.collectLatest { medicineResult ->
                    when (medicineResult) {
                        is Resource.Error -> {}
                        is Resource.Loading -> hideVisibility(binding)
                        is Resource.Success -> {
                            showVisibility(binding)
                            binding?.apply {
                                Glide.with(requireContext()).load(medicineResult.data?.data?.mdcImg)
                                    .into(diseaseImage)
                                overviewText.text = medicineResult.data?.data?.mdcName
                                overviewTextDescription.text = medicineResult.data?.data?.mdcDesc
                            }
                        }
                    }
                }
            }
        }
    }


    private fun hideVisibility(binding: FragmentMedicineDetailBinding?) {
        binding?.apply {
            progressBar.visibility = View.VISIBLE
            diseaseImage.visibility = View.GONE
            overviewText.visibility = View.GONE
            overviewTextDescription.visibility = View.GONE
        }
    }

    private fun showVisibility(binding: FragmentMedicineDetailBinding?) {
        binding?.apply {
            progressBar.visibility = View.GONE
            diseaseImage.visibility = View.VISIBLE
            overviewText.visibility = View.VISIBLE
            overviewTextDescription.visibility = View.VISIBLE
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}

