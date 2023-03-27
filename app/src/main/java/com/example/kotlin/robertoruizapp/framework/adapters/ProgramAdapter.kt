package com.example.kotlin.robertoruizapp.framework.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.data.network.model.ProgramBase
import com.example.kotlin.robertoruizapp.databinding.ItemProgramaBinding
import com.example.kotlin.robertoruizapp.framework.adapters.viewholder.ProgramViewHolder

class ProgramAdapter : RecyclerView.Adapter<ProgramViewHolder>() {
    var data: ArrayList<ProgramBase> = ArrayList()
    lateinit var context: Context

    fun ProgramAdapter(basicData: ArrayList<ProgramBase>, context: Context){
        this.data = basicData
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