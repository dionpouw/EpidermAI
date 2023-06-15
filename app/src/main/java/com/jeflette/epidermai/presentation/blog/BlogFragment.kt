package com.jeflette.epidermai.presentation.blog

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jeflette.epidermai.databinding.FragmentBlogBinding

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding
    private val args: BlogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            Glide.with(requireContext()).load(args.blog.image).into(blogImage)
            blogText.text = args.blog.name
            blogTextDescription.text = args.blog.desc
        }
        binding?.blogTextDescription?.movementMethod = ScrollingMovementMethod()
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}