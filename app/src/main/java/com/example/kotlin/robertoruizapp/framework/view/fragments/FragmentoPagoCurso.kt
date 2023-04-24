package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.app.Activity
import android.content.Intent
import okhttp3.MultipartBody



import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PaymentViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class FragmentoPagoCurso : Fragment() {
    private var _binding: FragmentoFormaDePagoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PaymentViewModel
    private var cursoID : String? = null
    private lateinit var image_view: ImageView
    private var imagen_pago : Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        _binding = FragmentoFormaDePagoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val boton : Button = root.findViewById(R.id.choose_image_btn)
        val button_enviar: Button = root.findViewById(R.id.button_enviar)
        image_view = root.findViewById(R.id.image_view)


        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA);
       // val status: String = "Pendiente"

        val token: String = "Bearer " + LoginActivity.token





        fun startPayment() {
            val file = File(requireContext().cacheDir, "image.jpg")
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fieldname", "image")
                .addFormDataPart("originalname", file.name)
                .addFormDataPart("encoding", "binary")
                .addFormDataPart("mimetype", "image/jpeg")
                .addFormDataPart("size", file.length().toString())
                .addFormDataPart("destination", "/path/to/destination")
                .addFormDataPart("filename", file.name)
                .addFormDataPart("path", file.absolutePath)
                .addFormDataPart(
                    "buffer",
                    file.name,
                    file.asRequestBody("application/octet-stream".toMediaType())
                )
                .addFormDataPart("billImageUrl", "billImageUrlValue")
                .addFormDataPart("cursoID", cursoID ?: "")
                .build()

            val user = Pago(
                cursoID,
                //"billImageUrlValue",
                file.name,
                "utf-8",
                "image/jpeg",
                file.length(),
                file.parentFile.absolutePath,
                file.name,
                file.absolutePath
            )

            viewModel.startPayment(token, requestBody, user)

        }






        boton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, Companion.REQUEST_CODE_SELECT_IMAGE)
        }

        button_enviar.setOnClickListener {
            startPayment()
            Log.d("inscribirme boton", "me clickeaste")
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            image_view.setImageURI(selectedImageUri)
            imagen_pago = selectedImageUri
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE = 100
    }
}

private fun MultipartBody.part(index: String): MultipartBody.Part {
    TODO("Not yet implemented")
}
