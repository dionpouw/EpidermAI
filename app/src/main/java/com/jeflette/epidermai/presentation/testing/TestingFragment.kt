package com.jeflette.epidermai.presentation.testing

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jeflette.epidermai.R
import com.jeflette.epidermai.databinding.FragmentTestingBinding
import com.jeflette.epidermai.ml.Train86Val82F1low73
import com.jeflette.epidermai.util.rotateFile
import com.jeflette.epidermai.util.safeNavigate
import com.jeflette.epidermai.util.uriToFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TestingFragment : Fragment() {

    private var _binding: FragmentTestingBinding? = null
    private val binding get() = _binding

    private var getFile: File? = null
    private val imageSize = 128

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding?.apply {
            btnCaptureImage.setOnClickListener {
                findNavController().safeNavigate(
                    TestingFragmentDirections.actionTestingFragmentToCameraFragment()
                )
            }
            btnLoadImage.setOnClickListener { startGallery() }
            tvOutput.setOnClickListener {
                if (tvOutput.text == "Select picture first") {
                    Toast.makeText(requireContext(), "Select picture first", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else {
                    findNavController().safeNavigate(
                        TestingFragmentDirections.actionTestingFragmentToDiseaseDetailFragment(
                            tvOutput.text.toString()
                        )
                    )
                }
            }
        }
        setFragmentListener()
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    context, getString(R.string.permission_error), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.select_image))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            getFile = result.data?.data?.let { uriToFile(it, requireContext()) }
            binding?.imageView?.let { Glide.with(this).load(myFile).into(it) }
            outputGenerator(BitmapFactory.decodeFile(myFile.path))
        }
    }

    private fun setFragmentListener() {
        setFragmentResultListener(CAMERA_X_RESULT) { _, bundle ->
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable("fileResult", File::class.java)
            } else {
                @Suppress("DEPRECATION") bundle.getSerializable("fileResult")
            } as? File

            val isBackCamera = bundle.getBoolean("isBackCamera", true)

            getFile = myFile
            getFile?.let { file ->
                rotateFile(file, isBackCamera)
                binding?.imageView?.let {
                    Glide.with(this).load(BitmapFactory.decodeFile(file.path)).into(it)
                }
            }
            outputGenerator(BitmapFactory.decodeFile(getFile?.path))
        }
    }

    private fun outputGenerator(bitmap: Bitmap) {
        val image = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
        classifyImage(image)
    }

    private fun classifyImage(image: Bitmap?) {
        val model = Train86Val82F1low73.newInstance(requireContext())

        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 128, 128, 3), DataType.FLOAT32)

        val byteBuffer = ByteBuffer.allocateDirect(4 * 128 * 128 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(128 * 128)
        image?.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0

        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val value = intValues[pixel++]
                byteBuffer.putFloat((value shr 16 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((value shr 8 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((value and 0xFF) * (1f / 255))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result
        val outputFeature0 = model.process(inputFeature0).outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        Log.d("TAG", "classifyImage: $confidences")
        var maxPos = 0
        var maxConfidence: Float = 0.0F;

        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i];
                maxPos = i;
            }
        }

        val classes = resources.getStringArray(R.array.classes)
        binding?.tvOutput?.text = classes[maxPos].toString()

        // Releases model resources if no longer used.
        model.close()
    }


    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object {
        const val CAMERA_X_RESULT = "200"
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}