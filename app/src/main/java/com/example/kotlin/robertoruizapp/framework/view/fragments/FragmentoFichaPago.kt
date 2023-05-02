package com.example.kotlin.robertoruizapp.framework.view.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.mypokedexapp.viewmodel.MainViewModel
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.Repository
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.CursosObjeto
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import com.example.kotlin.robertoruizapp.databinding.FragmentoFichapagoBinding
import com.example.kotlin.robertoruizapp.databinding.FragmentoInfoCursosBinding
import com.example.kotlin.robertoruizapp.framework.viewmodel.FichaPagoViewModel
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class FragmentoFichaPago : Fragment() {
    private lateinit var _binding: FragmentoFichapagoBinding
    private val binding get() = _binding ?: throw IllegalStateException("Binding is null.")
    private lateinit var viewModel: FichaPagoViewModel
    private lateinit var recyclerView: RecyclerView
    private var cursoID: String? = null  // Declaración de la variable


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[FichaPagoViewModel::class.java]
        _binding = FragmentoFichapagoBinding.inflate(inflater, container, false)


        val root = binding.root
        val intent = requireActivity().intent
        cursoID = requireActivity().intent.getStringExtra(Constants.CURSO_ID_EXTRA);


        // Carga los datos
      lateinit var data: List<Document>
       getCourseList()


        val boton : Button = root.findViewById(R.id.boton_comprobante)
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

    private fun getCourseList(){
        CoroutineScope(Dispatchers.IO).launch {
            val repository = Repository()
            val result: CursosObjeto? = repository.getCursos()

            CoroutineScope(Dispatchers.Main).launch{
                val curso = cursoFromID(cursoID,result)
                if (curso != null)
                {
                    Log.d("FragmentoFichaPago", "Resultado de getCursos(): $result")

                    binding.montoCurso.text = "$" + curso.cost.toString()
                    binding.Banco.text = curso.bank
                    if (curso.bankAccount != null){
                        binding.numeroCuenta.text = "Cuenta:  " + curso.bankAccount
                    }else{
                        binding.numeroCuenta.text = "Cuenta:  Por Definir"
                    }

                    binding.referenciaCurso.text = "Referencia:  " + curso.courseName
                }
            }
        }
    }
    private fun cursoFromID(cursoID: String?, result: CursosObjeto?): Document? {
        for (curso in result!!.data.documents){
            var cursoid = curso._id
            if(cursoid.toString() == cursoID)
                return curso
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

}




