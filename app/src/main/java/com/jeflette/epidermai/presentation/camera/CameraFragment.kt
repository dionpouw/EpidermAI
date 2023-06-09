package com.jeflette.epidermai.presentation.camera

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.jeflette.epidermai.R
import com.jeflette.epidermai.databinding.FragmentCameraBinding
import com.jeflette.epidermai.presentation.testing.TestingFragment
import com.jeflette.epidermai.util.createFile

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCamera()

        binding?.captureImage?.setOnClickListener { takePhoto() }
        binding?.switchCamera?.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application = this.requireActivity().application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this.requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        requireActivity().getString(R.string.error_camera_x),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        requireContext(),
                        requireActivity().getString(R.string.image_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                    setFragmentResult(
                        TestingFragment.CAMERA_X_RESULT, bundleOf(
                            "fileResult" to photoFile,
                            "isBackCamera" to (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                        )
                    )
                    findNavController().navigateUp()
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())

        cameraProviderFuture.addListener(
            {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().setTargetResolution(Size(128, 128)).build()
                    .also { it.setSurfaceProvider(binding?.viewFinder?.surfaceProvider) }
                imageCapture = ImageCapture.Builder().setTargetResolution(Size(128, 128))
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                } catch (e: Exception) {
                    Toast.makeText(
                        this.requireContext(),
                        getString(R.string.error_camera_x),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, ContextCompat.getMainExecutor(this.requireContext())
        )
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}