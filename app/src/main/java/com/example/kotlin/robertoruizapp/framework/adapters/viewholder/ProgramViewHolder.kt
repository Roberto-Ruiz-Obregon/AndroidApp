package com.example.kotlin.robertoruizapp.framework.adapters.viewholder

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.data.network.model.ProgramBase
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.data.network.model.program.Program
import com.example.kotlin.robertoruizapp.databinding.ItemProgramaBinding
import com.example.kotlin.robertoruizapp.domain.ProgramInfoRequirement
import com.example.kotlin.robertoruizapp.framework.view.activities.ProgramDetailActivity
import com.example.kotlin.robertoruizapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgramViewHolder(private val binding: ItemProgramaBinding) : RecyclerView.ViewHolder
    (binding.root){
    // todo llProgram
        fun bind(item: Document, context: Context) {
            binding.tvCardTituloPrograma.text = item.programName // Se le cambia el valor a la card
            //getProgramInfo(item.url, binding.ivPrograma, context)
            binding.llProgram.setOnClickListener {
              //  passViewGoToProgramDetail(item.url,context)
                // Todo passview
            }
        }

    private fun getProgramInfo(url: String, context: Context){
        var programStringNumber: String = url.replace("https://us-central1-robertoruiz-eca78" +
                ".cloudfunctions.net/api/v1/program","")
        programStringNumber = programStringNumber.replace("/","")
        val programNumber : String = programStringNumber

        //TOdo Passes the information of the program
        CoroutineScope(Dispatchers.IO).launch {
            val programInfoRequirement = ProgramInfoRequirement()
            val result: Program? = programInfoRequirement(programNumber)

        }
    }

    private fun passViewGoToProgramDetail(url: String, context: Context) {
        var intent: Intent = Intent(context, ProgramDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        context.startActivity(intent)
        intent.putExtra(Constants.URL_PROGRAM, url)
    }
}