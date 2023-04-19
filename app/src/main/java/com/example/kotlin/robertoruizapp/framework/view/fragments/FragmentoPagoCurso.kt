package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.kotlin.robertoruizapp.databinding.FragmentoFormaDePagoBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoHomeBinding

class FragmentoPagoCurso : Fragment() {
    private var _binding: FragmentoFormaDePagoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var image_view: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentoFormaDePagoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val boton : Button = root.findViewById(R.id.choose_image_btn)
        image_view = root.findViewById(R.id.image_view)


        boton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, Companion.REQUEST_CODE_SELECT_IMAGE)


        }



        //initializeComponents(root)
        //initializeObservers()
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            image_view.setImageURI(selectedImageUri)
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