package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.Manifest.permission.READ_EXTERNAL_STORAGE

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PaymentViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody



class FragmentoPagoCurso : Fragment() {
    private var _binding: FragmentoFormaDePagoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PaymentViewModel
    private var cursoID : String? = null
    private lateinit var image_view: ImageView
    private var imagen_pago : Uri? = null
    private var selectedImageFile: File? = null
    private val REQUEST_CODE_PERMISSIONS = 10


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
        val token: String = "Bearer " + LoginActivity.token

        checkPermissions()

        fun startPayment(imageFile: File) {

            // Crear un RequestBody a partir del archivo de imagen
            val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

            // Llamar al método startPayment en el ViewModel
            viewModel.startPayment(token, requestFile, cursoID)


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
                val imageUri = getImageUri(requireContext(), imageBitmap)
                imagen_pago = imageUri
                selectedImageFile = File(imageUri.path!!)
            } else if (requestCode == 2) {
                val selectedImageUri = data?.data
                image_view.setImageURI(selectedImageUri)
                imagen_pago = selectedImageUri
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
            val filePath = getPath(context, it)
            filePath?.let { path ->
                return File(path)
            }
        }
        return null
    }

    fun getPath(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            val path = it.getString(columnIndex)
            it.close()
            return path
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

    // SOLICITAR PERMISOS

    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val notGrantedPermissions = mutableListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                notGrantedPermissions.add(permission)
            }
        }

        if (notGrantedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                notGrantedPermissions.toTypedArray(),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() &&
                    grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                ) {
                    // Todos los permisos fueron concedidos
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Los permisos son necesarios para utilizar esta función",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Glide.with(requireContext()).clear(image_view)

    }

    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE = 100
        private const val REQUEST_CODE_PERMISSIONS = 101
    }


}


