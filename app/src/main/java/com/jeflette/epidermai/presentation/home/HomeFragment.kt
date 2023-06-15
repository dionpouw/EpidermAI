package com.jeflette.epidermai.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jeflette.epidermai.data.local.model.CommonItem
import com.jeflette.epidermai.databinding.FragmentHomeBinding
import com.jeflette.epidermai.presentation.adapter.BlogCardAdapter
import com.jeflette.epidermai.presentation.adapter.CardItemAdapter
import com.jeflette.epidermai.util.Resource
import com.jeflette.epidermai.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
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
        val blogAdapter = BlogCardAdapter()

        viewModel.getBlog()
        viewModel.getAllDiseases()

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

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun populateBlogList(blogAdapter: BlogCardAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.blogResult.collectLatest { blogResult ->
                    when (blogResult) {
                        is Resource.Loading -> {
                            binding?.blogProgressBar?.visibility = View.VISIBLE
                        }

                        is Resource.Error -> {
                            binding?.blogProgressBar?.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            binding?.blogProgressBar?.visibility = View.GONE
                            binding?.blogRecyclerView?.visibility = View.VISIBLE

                            blogResult.data?.let { data -> blogAdapter.addCardItemList(data) }
                        }
                    }
                }
            }
        }
    }

    private fun populateDiseaseList(diseaseAdapter: CardItemAdapter) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.diseaseResult.collectLatest { diseaseResult ->
                    when (diseaseResult) {
                        is Resource.Loading -> {
                            binding?.diseaseProgressBar?.visibility = View.VISIBLE
                        }

                        is Resource.Error -> {
                            binding?.diseaseProgressBar?.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            binding?.diseaseProgressBar?.visibility = View.GONE
                            binding?.diseaseRecyclerView?.visibility = View.VISIBLE
                            diseaseResult.data?.let { data ->
                                val diseaseData = mutableListOf<CommonItem>()
                                for (diseases in data.data!!) {
                                    val realData = CommonItem(
                                        id = diseases.dssId,
                                        name = diseases.dssName,
                                        image = diseases.dssImg,
                                        desc = diseases.dssDesc
                                    )
                                    diseaseData.add(realData)
                                }
                                diseaseAdapter.addCardItemList(diseaseData)
                            }
                        }
                    }
                }
            }
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