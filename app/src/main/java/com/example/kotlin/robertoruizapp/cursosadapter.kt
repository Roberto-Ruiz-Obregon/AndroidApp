package com.example.kotlin.robertoruizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class cursosadapter: RecyclerView.Adapter<cursosadapter.ViewHolder>() {

    val titulos = arrayOf("Escritura", "Español" , "Matematicas", "Artes")
    val desc = arrayOf("Aprende a escribir", "Conoce mas acerca de nuestra lengua",
        "Aprende desde 0", "Muchas areas por explorar")
    val modalidad = arrayOf("Prencial", "Zoom" , "Zoom", "Presencial")
    val states = arrayOf("Gratis", "Paga" , "Paga", "Gratis")
    val fecha = arrayOf("15/1/2023","02/2/2023","25/11/2023","25/10/2023")

    val curso = intArrayOf(R.drawable.curso1)
    val calendario = intArrayOf(R.drawable.calendario)
    val pago = intArrayOf(R.drawable.pago)
    val ping = intArrayOf(R.drawable.ping)


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int):ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_element, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.courseName.text = titulos[i]
        viewHolder.description.text = desc[i]
        viewHolder.modality.text = modalidad[i]
        viewHolder.status.text = states[i]
        viewHolder.startDate.text = fecha[i]
        viewHolder.Imagen_curso.setImageResource(curso[i])
        viewHolder.Imagen_calendario.setImageResource(calendario[i])
        viewHolder.Imagen_pago.setImageResource(pago[i])
        viewHolder.Imagen_ping.setImageResource(ping[i])
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
        val Imagen_calendario: ImageView
        val Imagen_pago: ImageView
        val Imagen_ping: ImageView


        init {
            Imagen_curso =  itemView.findViewById(R.id.imagen_curso1)
            Imagen_calendario =  itemView.findViewById(R.id.calendario_curso1)
            Imagen_pago =  itemView.findViewById(R.id.pago_curso1)
            Imagen_ping =  itemView.findViewById(R.id.ping_curso1)
            courseName =  itemView.findViewById(R.id.titulo_curso_card_1)
            description =  itemView.findViewById(R.id.des_ccurso2)
            startDate =  itemView.findViewById(R.id.fecha_curso1)
           // endDate =  itemView.findViewById(R.id.course_Name)
            status =  itemView.findViewById(R.id.cobro_curso)
            modality =  itemView.findViewById(R.id.locacion_curso1)

        }
    }
}