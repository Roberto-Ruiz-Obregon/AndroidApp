package com.example.kotlin.robertoruizapp.framework.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.data.network.model.program.Document
import com.example.kotlin.robertoruizapp.databinding.ItemProgramaBinding
import com.example.kotlin.robertoruizapp.framework.adapters.viewholder.ProgramViewHolder

class ProgramAdapter : RecyclerView.Adapter<ProgramViewHolder>() {
    lateinit var data: ArrayList<Document>
    lateinit var context: Context

    // Investigar porque se ocupa Cast
    // "La variable del adaptabdor es un arraylist"
    fun ProgramAdapter(basicData: List<Document>, context: Context){
        this.data = basicData as ArrayList<Document>
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val binding = ItemProgramaBinding.inflate(LayoutInflater.from(parent.context), parent,
            false)
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int){
        val item = data[position]
        holder.bind(item,context)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}