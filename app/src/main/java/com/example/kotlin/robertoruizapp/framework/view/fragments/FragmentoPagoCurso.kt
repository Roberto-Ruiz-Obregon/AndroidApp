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
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PaymentViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * FragmentoPagoCurso class that manages the fragment actions
 *
 */
class FragmentoPagoCurso : Fragment() {
    private var _binding: FragmentoFormaDePagoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PaymentViewModel
    private var cursoID : String? = null
    private lateinit var image_view: ImageView
    private var imagen_pago : Uri? = null

    /**
     * When the fragment is created sets up binding, viewmodel and progress bar
     *
     * @param inflater How the layout wil be created
     * @param container what viewmgroup the fragment belongs to
     * @param savedInstanceState the state of the activity / fragment
     *
     * @return [View] object containing the information about the fragment
     */
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
            Log.d("pago boton", "me clickeaste")
        }

        return root
    }


    /**
     * If the requestCode and result code are equal selected image is loaded
     *
     * @param requestCode request code of companion Object
     * @param resultCode result code of the Activity
     * @param data information of the Course
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            image_view.setImageURI(selectedImageUri)
            imagen_pago = selectedImageUri
        }
    }

    /**
     * Sets the binding to Null after view is Destroyed
     *
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Companion object for Select Image
     *
     * @constructor Create empty Companion wit private val for Request code select image
     */
    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE = 100
    }




}

