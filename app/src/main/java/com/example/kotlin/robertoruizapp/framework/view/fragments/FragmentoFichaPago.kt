package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.databinding.FragmentoFichapagoBinding
import com.example.kotlin.robertoruizapp.framework.viewmodel.FichaPagoViewModel
import com.example.kotlin.robertoruizapp.utils.Constants

/**
 * FragmentoFichaPago class that manages the fragment actions
 */
class FragmentoFichaPago : Fragment() {
    private var _binding: FragmentoFichapagoBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null.")
    private lateinit var viewModel: FichaPagoViewModel
    private lateinit var recyclerView: RecyclerView
    private var cursoID: String? = null  // Declaración de la variable

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
        viewModel = ViewModelProvider(this)[FichaPagoViewModel::class.java]
        _binding = FragmentoFichapagoBinding.inflate(inflater, container, false)
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA);
        val root = binding.root

        val boton: Button = root.findViewById(R.id.boton_comprobante)
        boton.setOnClickListener {
            val newFragment = FragmentoPagoCurso()
            val currentFragment = FragmentoFichaPago()
            val container: ViewGroup = root.findViewById(R.id.fichaPago)
            val tag = "fragmento_anterior_tag"

            // Elimina todas las vistas contenidas en el contenedor
            container.removeAllViews()

            // Agrega el fragmento anterior al back stack si no está en la pila
            var fragmentAnterior = parentFragmentManager.findFragmentByTag(tag)
            if (fragmentAnterior == null) {
                fragmentAnterior = currentFragment
                parentFragmentManager.beginTransaction()
                    .add(R.id.fichaPago, fragmentAnterior, tag)
                    .commit()
            }

            // Reemplaza el fragmento actual con el nuevo fragmento
            parentFragmentManager.beginTransaction()
                .replace(R.id.fichaPago, newFragment)
                .addToBackStack(null)
                .commit()
        }
        return root
    }
}




