package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.Manifest
import android.Manifest.permission
import android.Manifest.permission.*
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.content.pm.PackageManager
import android.graphics.Bitmap

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.Nullable
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PaymentViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber

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
    private var selectedImageFile: File? = null
    private val REQUEST_CODE_PERMISSIONS = 10

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
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA)?.trim()
        // Crear un RequestBody a partir de la cadena cursoID
        val courseIdRequestBody = cursoID?.toRequestBody("text/plain".toMediaTypeOrNull())
        val token: String = "Bearer " + LoginActivity.token

        checkPermissions()


        fun startPayment(imageFile: File) {
            val requestFile = fileToRequestBody(imageFile)
            viewModel.startPayment2(token,courseIdRequestBody, requestFile)
            goToNewFragment()
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


    /**
     * If the requestCode and result code are equal selected image is loaded
     *
     * @param requestCode request code of companion Object
     * @param resultCode result code of the Activity
     * @param data information of the Course
     */
    private fun goToNewFragment() {

        val contenedor = (context as FragmentActivity).findViewById<ViewGroup>(R.id.FragmentoPago)
        contenedor.removeAllViews()

        val fragmentoNuevo = Fragmento_ComprobanteEnviado()
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

        transaction.replace(R.id.FragmentoPago, fragmentoNuevo)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun Fragment.makeToast(text: String,duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
    }


    private fun fileToRequestBody(file: File): RequestBody {
        val MEDIA_TYPE_JPG = "image/jpeg".toMediaTypeOrNull()
        return file.asRequestBody(MEDIA_TYPE_JPG)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                image_view.setImageBitmap(imageBitmap)
                val imageUri = saveImageToGallery(requireContext(), imageBitmap)
                selectedImageFile = imageUri?.let { uriToFile(requireContext(), it) }
            } else if (requestCode == 2) {
                val selectedImageUri = data?.data
                image_view.setImageURI(selectedImageUri)
                selectedImageFile = selectedImageUri?.let { uriToFile(requireContext(), it) }
            }
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
    private fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "your_image_name.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            context.contentResolver.openOutputStream(uri).use { outputStream ->
                outputStream?.let {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, contentValues, null, null)
            }
        }

        return imageUri
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
        val requiredPermissions = arrayOf(
            CAMERA,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
        )

        val ungrantedPermissions = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) != PackageManager.PERMISSION_GRANTED
        }

        if (ungrantedPermissions.isNotEmpty()) {
            requestPermissions()
        }
    }



    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                CAMERA,
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_CODE_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {

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


}


