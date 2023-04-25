package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap


import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PaymentViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FragmentoPagoCurso : Fragment() {
    private var _binding: FragmentoFormaDePagoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PaymentViewModel
    private var cursoID : String? = null
    private lateinit var image_view: ImageView
    private var imagen_pago : Uri? = null
    private var selectedImageFile: File? = null


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


        fun startPayment(imageFile: File) {

            val user = Pago(

                imageFile.name,
                "utf-8",
                "image/jpeg",
                imageFile.length(),
                imageFile.parentFile.absolutePath,
                imageFile.name,
                imageFile.absolutePath
            )
            viewModel.startPayment(token, user, cursoID)
            //viewModel.startPayment(token, imageFile, user.courseId)
        }




        boton.setOnClickListener {
            val options = arrayOf<CharSequence>("Tomar foto", "Elegir de la galería", "Cancelar")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Seleccionar una opción")
            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == "Tomar foto" -> {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, 1)
                    }
                    options[item] == "Elegir de la galería" -> {
                        val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, 2)
                    }
                    options[item] == "Cancelar" -> {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()
        }

        button_enviar.setOnClickListener {
            if(selectedImageFile != null) {
                startPayment(selectedImageFile!!)
            } else {
                Toast.makeText(context, "Por favor, seleccione una imagen primero", Toast.LENGTH_SHORT).show()
            }}


        return root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                image_view.setImageBitmap(imageBitmap)
                selectedImageFile = bitmapToFile(requireContext(), imageBitmap)
            } else if (requestCode == 2) {
                val selectedImageUri = data?.data
                image_view.setImageURI(selectedImageUri)
                selectedImageFile = uriToFile(requireContext(), selectedImageUri)
            }
        }
    }


    private fun bitmapToFile(context: Context, bitmap: Bitmap): File {
        val filesDir = context.filesDir
        val imageFile = File(filesDir, "image.jpg")

        val os = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        os.close()

        return imageFile
    }

    private fun uriToFile(context: Context, uri: Uri?): File? {
        uri?.let {
            val resolver = context.contentResolver
            val inputStream = resolver.openInputStream(it)
            val type = resolver.getType(it)
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
            val file = File(context.cacheDir, "image.$extension")
            val os = FileOutputStream(file)
            inputStream?.copyTo(os)
            os.flush()
            os.close()
            inputStream?.close()
            return file
        }
        return null
    }


    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.with(requireContext()).clear(image_view)

    }

    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE = 100
    }

}

