package com.example.kotlin.robertoruizapp.framework.adapters.viewholder

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.databinding.ItemProgramaBinding
import com.example.kotlin.robertoruizapp.domain.ProgramListRequirement
import com.example.kotlin.robertoruizapp.framework.view.activities.ProgramDetailActivity
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProgramViewHolder(private val binding: ItemProgramaBinding) : RecyclerView.ViewHolder
    (binding.root) {

    fun bind(item: Document, context: Context) {

        if(item.createdAt.isNullOrEmpty()) {
            binding.tvCardCalendarioPrograma.text =  "15/04/2023"
            binding.tvCardTituloPrograma.text = item.programName // Se le cambia el valor a la card
            binding.tvCardDescripcionPrograma.text = item.description
            CoroutineScope(Dispatchers.Main).launch {
                var urlImage = item.imageUrl

                val requestOptions = RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)

                Glide.with(context).load(urlImage?.toString())
                    .apply(requestOptions)
                    .into(binding.ivPrograma)
            }

            binding.btnCardDetallePrograma.setOnClickListener {
                passViewGoToProgramDetail(item._id,context)

            }

        }
        else {

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = inputFormat.parse(item.createdAt)
            val formattedDate = outputFormat.format(date)

            binding.tvCardTituloPrograma.text = item.programName // Se le cambia el valor a la card
            binding.tvCardCalendarioPrograma.text =  formattedDate
            binding.tvCardDescripcionPrograma.text = item.description
            binding.tvCardCategoryPrograma.text = item.category

            CoroutineScope(Dispatchers.Main).launch {
                var urlImage = item.imageUrl

                val requestOptions = RequestOptions()
                    .priority(Priority.HIGH)

                Glide.with(context).load(urlImage?.toString())
                    .apply(requestOptions)
                    .into(binding.ivPrograma)
            }

            binding.btnCardDetallePrograma.setOnClickListener {
                passViewGoToProgramDetail(item._id,context)

            }
        }

    }

    private fun getProgramInfo(url: String, imageView: ImageView, context: Context) {


        CoroutineScope(Dispatchers.IO).launch {
            val programListRequirement = ProgramListRequirement()
            val result: Program? = programListRequirement()
            CoroutineScope(Dispatchers.Main).launch {

                val urlImage = result?.data?.documents?.iterator()?.next()?.imageUrl

                val requestOptions = RequestOptions()
                    .fitCenter()
                    .priority(Priority.HIGH)

                Glide.with(context).load(urlImage?.toString())
                    .apply(requestOptions)
                    .into(imageView)
            }
        }
    }

    private fun passViewGoToProgramDetail(url: String, context: Context) {
        var intent: Intent = Intent(context, ProgramDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(Constants.ID_PROGRAM, url)
        context.startActivity(intent)
        Log.d("ID_PROGRAM", url)





    }
}