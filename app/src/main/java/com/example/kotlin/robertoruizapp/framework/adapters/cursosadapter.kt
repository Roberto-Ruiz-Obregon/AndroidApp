package com.example.kotlin.robertoruizapp.framework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.robertoruizapp.R


class cursosadapter: RecyclerView.Adapter<cursosadapter.ViewHolder>() {

    val titulos = arrayOf("Escritura", "Español" , "Matematicas", "Artes")
    val desc = arrayOf("Aprende a escribir", "Conoce mas acerca de nuestra lengua",
        "Aprende desde 0", "Muchas areas por explorar")
    val modalidad = arrayOf("Prencial", "Zoom" , "Zoom", "Presencial")
    val states = arrayOf("Gratis", "Paga" , "Paga", "Gratis")
    val fecha = arrayOf("15/1/2023","02/2/2023","25/11/2023","25/10/2023")

    val curso = intArrayOf(R.drawable.curso1, R.drawable.curso2, R.drawable.escritura, R.drawable.curso4)



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_element_cursos, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.courseName.text = titulos[i]
        viewHolder.description.text = desc[i]
        viewHolder.modality.text = modalidad[i]
        viewHolder.status.text = states[i]
        viewHolder.startDate.text = fecha[i]
        viewHolder.Imagen_curso.setImageResource(curso[i])

    }

    override fun getItemCount(): Int {
        return titulos.size
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val courseName: TextView
        val description: TextView
        val startDate: TextView
        //val endDate: Date
        val modality: TextView
        val status: TextView

        val Imagen_curso: ImageView
        // val Imagen_calendario: ImageView
        // val Imagen_pago: ImageView
        //val Imagen_ping: ImageView


        init {
            Imagen_curso =  itemView.findViewById(R.id.imagen_curso1)
            courseName =  itemView.findViewById(R.id.titulo_curso_card_1)
            description =  itemView.findViewById(R.id.des_ccurso2)
            startDate =  itemView.findViewById(R.id.fecha_curso1)
            // endDate =  itemView.findViewById(R.id.course_Name)
            status =  itemView.findViewById(R.id.cobro_curso)
            modality =  itemView.findViewById(R.id.locacion_curso1)

        }
    }
}