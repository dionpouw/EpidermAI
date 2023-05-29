package com.jeflette.epidermai.presentation.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jeflette.epidermai.databinding.FragmentMedicineBinding

class MedicineFragment : Fragment() {

    private var _binding: FragmentMedicineBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}