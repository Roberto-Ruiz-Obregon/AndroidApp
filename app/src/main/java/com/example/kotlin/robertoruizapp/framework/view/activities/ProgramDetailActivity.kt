package com.example.kotlin.robertoruizapp.framework.view.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.databinding.ActivityProgramDetailBinding
import com.example.kotlin.robertoruizapp.domain.ProgramInfoRequirement
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramDetailActivity : Activity() {
    private lateinit var binding: ActivityProgramDetailBinding
    private var programID: String? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        manageIntent()
        initializeBinding()

    }


    private fun initializeBinding(){
        binding = ActivityProgramDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun manageIntent(){
        if(intent != null){
            programID = intent.getStringExtra(Constants.ID_PROGRAM)
            Log.d("Aqui", programID.toString())
        }
        //TODO preguntarle a Alex como acomodarlo para mantener un Clean Architecture

        var programIDString : String = programID.toString()
        Log.d("WHAT", programIDString) // Si llega el string

        CoroutineScope(Dispatchers.IO).launch {
            val programInfoRequirement = ProgramInfoRequirement()
            val result: Program? = programInfoRequirement(programIDString)
           Log.d("WHAT2",result!!.toString()) //

           // Log.d("WHAT", result!!.toString()) // Descarga algo, pero no completo

            CoroutineScope(Dispatchers.Main).launch {

                val urlImage = result?.data?.document?.imageUrl
                val activity = this@ProgramDetailActivity
                val imageView : ImageView = activity.findViewById(R.id.ivDetailProgramImagen)

                val aux = result?.data?.document
             //   Log.d("nombre", result.data.document.size.toString())
                binding.tvDetailProgramTitle.text = aux?.programName
                binding.tvDetailProgramDescripcion.text = aux?.description
                binding.tvDetailProgramCambioFecha.text = aux?.createdAt


                Glide.with(activity).load(R.drawable.calendario_24)
                    .into(binding.ivDetailProgramCalendario)
                Glide.with(activity).load(R.drawable.whatsapp)
                    .into(binding.ivDetailProgramWhatsApp) //

                Glide.with(activity).load(urlImage.toString())
                    .into(imageView)
            }


        }


    }


    fun Adapter(basicData: List<Document>){

        var data = basicData as ArrayList<Document>

    }

}