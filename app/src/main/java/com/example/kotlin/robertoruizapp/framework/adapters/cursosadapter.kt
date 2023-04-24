package com.example.kotlin.robertoruizapp.framework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.framework.view.activities.CursoClickListener
import com.example.kotlin.robertoruizapp.data.network.model.Cursos.Document
import java.text.SimpleDateFormat
import java.util.*


class cursosadapter(val clickListener: CursoClickListener): RecyclerView.Adapter<cursosadapter.ViewHolder>() {

    lateinit var data : List<Document>
    var results : Int = 0

    fun cursosAdapter (data: List<Document>) {
            this.data = data

    }

    fun cursosResults (results: Int) {
        this.results = results
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_element_cursos, viewGroup, false)
        return ViewHolder(v, clickListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var temp: Document = data[i]

        if (temp.cost.toString() == "0") {
            viewHolder.cobro_curso.text = "Gratuito"
        }
        else{
            viewHolder.cobro_curso.text = "$" + temp.cost.toString()
        }


        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (temp.startDate != null)
        {
            val date = inputFormat.parse(temp.startDate)
            val formattedDate = outputFormat.format(date)
            viewHolder.startDate.text = formattedDate
        }
        else {
            viewHolder.status.text = temp.status
           //viewHolder.status.text = "00/00/00"
        }
        viewHolder.courseName.text = temp.courseName
        viewHolder.description.text = temp.description
        viewHolder.modality.text = temp.modality




        Glide.with(viewHolder.itemView.context)
            .load(temp.imageUrl)
            .into(viewHolder.Imagen_curso)


        viewHolder.botoncurso.setOnClickListener(){
            clickListener.onClick(temp)
        }



    }

    override fun getItemCount(): Int {
        var courses: Int = results
        return courses
    }


    inner class ViewHolder(itemView: View, private val clickListener : CursoClickListener): RecyclerView.ViewHolder(itemView){
        val courseName: TextView
        val description: TextView
        val startDate: TextView
        //val endDate: Date
        val modality: TextView
        val status: TextView
        val botoncurso: Button
        val Imagen_curso: ImageView
        val cobro_curso: TextView



        init {
            botoncurso =  itemView.findViewById(R.id.button_info1)
            Imagen_curso =  itemView.findViewById(R.id.imagen_curso1)
            courseName =  itemView.findViewById(R.id.titulo_curso_card_1)
            description =  itemView.findViewById(R.id.des_ccurso2)
            startDate =  itemView.findViewById(R.id.fecha_curso1)
            cobro_curso = itemView.findViewById(R.id.cobro_curso)
            // endDate =  itemView.findViewById(R.id.course_Name)
            status =  itemView.findViewById(R.id.cobro_curso)
            modality =  itemView.findViewById(R.id.locacion_curso1)

        }
    }
}

private fun Button.setOnClickListener(_id: String) {

}

private fun ImageView.setImageDrawable(imageUrl: String) {


}
