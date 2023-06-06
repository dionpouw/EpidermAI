package com.jeflette.epidermai.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jeflette.epidermai.databinding.FragmentHomeBinding
import com.jeflette.epidermai.model.CommonItem
import com.jeflette.epidermai.presentation.adapter.CardItemAdapter
import com.jeflette.epidermai.util.provideDummyDisease
import com.jeflette.epidermai.util.provideDummyMedicine
import com.jeflette.epidermai.util.safeNavigate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val diseaseAdapter = CardItemAdapter()
        val blogAdapter = CardItemAdapter()

        binding?.apply {
            logoutButton.setOnClickListener {
                logout()
            }
            diseaseRecyclerView.apply {
                adapter = diseaseAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            blogRecyclerView.apply {
                adapter = blogAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            testingButton.setOnClickListener {
                findNavController().safeNavigate(HomeFragmentDirections.actionHomeFragmentToTestingFragment())
            }
        }

        populateDiseaseList(diseaseAdapter)
        populateBlogList(blogAdapter)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun populateBlogList(blogAdapter: CardItemAdapter) =
        blogAdapter.addCardItemList(provideDummyMedicine())


    private fun populateDiseaseList(diseaseAdapter: CardItemAdapter) {
        val data = provideDummyDisease()
        val diseaseData = mutableListOf<CommonItem>()
        for (diseases in data) {
            diseaseData.add(diseases.diseaseItem)
        }
        val diseasesFinal: List<CommonItem> = diseaseData
        if (diseasesFinal.isNotEmpty()) {
            diseaseAdapter.addCardItemList(diseasesFinal)
        }
    }

    private fun logout() {
        auth.signOut()
        findNavController().safeNavigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}