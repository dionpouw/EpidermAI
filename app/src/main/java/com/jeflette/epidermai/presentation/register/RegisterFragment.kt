package com.jeflette.epidermai.presentation.register

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
import com.jeflette.epidermai.databinding.FragmentRegisterBinding
import com.jeflette.epidermai.util.safeNavigate

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        binding?.apply {
            registerButton.setOnClickListener {
                firebaseRegister(binding)
            }
        }
    }

    private fun firebaseRegister(binding: FragmentRegisterBinding?) {
        val name = binding?.nameRegisterEditText?.text.toString()
        val email = binding?.emailRegisterLayoutTextEdit?.text.toString()
        val password = binding?.passwordRegisterLayoutTextEdit?.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().safeNavigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            } else {
                Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}