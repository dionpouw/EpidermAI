package com.jeflette.epidermai.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jeflette.epidermai.databinding.FragmentHomeBinding
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

        binding?.apply {
            logoutButton.setOnClickListener {
                logout()
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