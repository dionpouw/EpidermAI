package com.jeflette.epidermai.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jeflette.epidermai.databinding.FragmentLoginBinding
import com.jeflette.epidermai.util.safeNavigate

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding?.apply {
            loginButton.setOnClickListener {
                firebaseLogin(binding)
            }
            goToRegister.setOnClickListener {
                findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            googleButton.setOnClickListener {
                findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }

        binding?.loginButton?.setOnClickListener {
            firebaseLogin(binding)
        }
    }

    private fun firebaseLogin(binding: FragmentLoginBinding?) {
        val email = binding?.emailLoginEditText?.text.toString()
        val password = binding?.passwordLoginEditText?.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            } else {
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}