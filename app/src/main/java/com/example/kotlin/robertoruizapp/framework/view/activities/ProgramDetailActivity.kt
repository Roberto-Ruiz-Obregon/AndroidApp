package com.example.kotlin.robertoruizapp.framework.view.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.databinding.ActivityProgramDetailBinding
import com.example.kotlin.robertoruizapp.domain.ProgramInfoRequirement
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ProgramDetailActivity class that manages the activity actions
 */
class ProgramDetailActivity : Activity() {
    private lateinit var binding: ActivityProgramDetailBinding
    private var programID: String? = null

    /**
     * When the activity is created sets up binding and viewmodel
     * alsi initializes the manageIntent, Binding and Listener methods
     *
     * @param savedInstanceState the state of the activity / fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        manageIntent()
        initializeBinding()
        initializeListeners()

    }

    /**
     * Initializes the Listeners to bind the icons with their corresponding action
     */
    private fun initializeListeners() {
        binding.ivDetailProgramWhatsApp.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://wa.me/524428205425")
            startActivity(openURL)
        }
        binding.tvDetailProgramWhatsapp.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://wa.me/524428205425")
            startActivity(openURL)
        }
        binding.tvDetailProgramTelefono.setOnClickListener {
            val phone = "tel:" + binding.tvDetailProgramTelefono.text.toString()
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(phone)))
        }

    }

    /**
     * Initializes the binding information of the view
     */
    private fun initializeBinding() {
        binding = ActivityProgramDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Extracts the binding information passed from [ProgramFragment]
     * and sets the view
     */
    private fun manageIntent() {
        if (intent != null) {
            programID = intent.getStringExtra(Constants.ID_PROGRAM)
        }

        var programIDString: String = programID.toString()


        CoroutineScope(Dispatchers.IO).launch {
            val programInfoRequirement = ProgramInfoRequirement()
            val result: Program? = programInfoRequirement(programIDString)

            if (result?.data?.document?.limitDate != null) {
                CoroutineScope(Dispatchers.Main).launch {

                    val urlImage = result.data.document.imageUrl
                    val activity = this@ProgramDetailActivity
                    val imageView: ImageView = activity.findViewById(R.id.ivDetailProgramImagen)

                    val aux = result.data.document
                    val inputFormat =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = inputFormat.parse(aux.limitDate)
                    val formattedDate = outputFormat.format(date!!)


                    binding.tvDetailProgramTitle.text = aux.programName
                    binding.tvDetailProgramDescripcion.text = aux.description
                    binding.tvDetailProgramCambioFecha.text = formattedDate


                    // Agrega el icono de calendario a la vista de DetailProgram
                    //Glide.with(activity).load(R.drawable.calendario_24)
                       //nto(binding.ivDetailProgramCalendario)
                    // Agrega el icono de whatsapp a la vista de DetailProgram
                    Glide.with(activity).load(R.drawable.whatsapp)
                        .into(binding.ivDetailProgramWhatsApp) //

                    Glide.with(activity).load(urlImage.toString())
                        .apply(
                            RequestOptions.placeholderOf(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                        )
                        .into(imageView)
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {

                    val urlImage = result?.data?.document?.imageUrl
                    val activity = this@ProgramDetailActivity
                    val imageView: ImageView = activity.findViewById(R.id.ivDetailProgramImagen)

                    val aux = result?.data?.document
                    binding.tvDetailProgramTitle.text = aux?.programName
                    binding.tvDetailProgramDescripcion.text = aux?.description
                    binding.tvDetailProgramCambioFecha.text = "15/04/2023"


                    // Agrega el icono de calendario a la vista de DetailProgram
                    //Glide.with(activity).load(R.drawable.calendario_24)
                       // .into(binding.ivDetailProgramCalendario)
                    // Agrega el icono de whatsapp a la vista de DetailProgram
                    Glide.with(activity).load(R.drawable.whatsapp)
                        .into(binding.ivDetailProgramWhatsApp) //

                    Glide.with(activity).load(urlImage.toString())
                        .apply(
                            RequestOptions.placeholderOf(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                        )
                        .into(imageView)
                }
            }
        }
    }
}


