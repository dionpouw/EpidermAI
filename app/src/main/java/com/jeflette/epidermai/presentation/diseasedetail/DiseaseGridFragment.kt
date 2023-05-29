package com.jeflette.epidermai.presentation.diseasedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jeflette.epidermai.databinding.FragmentDiseaseGridBinding

class DiseaseGridFragment : Fragment() {

    private var _binding: FragmentDiseaseGridBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiseaseGridBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

}