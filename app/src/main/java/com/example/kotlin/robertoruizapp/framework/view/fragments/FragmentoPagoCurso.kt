package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.app.Activity
import android.content.Intent
import android.media.Image
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
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.Inscripcion.Pago
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoHomeBinding
import com.example.kotlin.robertoruizapp.framework.view.activities.LoginActivity
import com.example.kotlin.robertoruizapp.framework.viewmodel.PaymentViewModel
import com.example.kotlin.robertoruizapp.utils.Constants

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
        val status: String = "Pendiente"

        val token: String = "Bearer" + LoginActivity.token

        fun startPayment() {

            //Este es un ejemplo de un curso
            //TODO extraer info de curso en vista de Inscripción
            //  val cursoId: String = "64386615c8ec2f0bc8b9dee3"

            val user = Pago(
               cursoID, status, imagen_pago
            )
           viewModel.startPayment(token, user)

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