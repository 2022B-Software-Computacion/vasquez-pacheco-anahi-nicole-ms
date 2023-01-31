package com.example.anvpapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class FRecyclerViewAdaptadorNombreCedula (
    private val contexto: GRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerVIew: RecyclerView
    ): RecyclerView.Adapter<FRecyclerViewAdaptadorNombreCedula.MyViewHolder>(){
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

            val nombreTextView: TextView = TODO()
            val cedulaTextView: TextView
            val likesTextView: TextView
            val accionButton: Button
            var numerolikes = 0

            init{
                nombreTextView = view.findViewById(R.id.tv_nombres)
                cedulaTextView = view.findViewById(R.id.tv_cedula)
                likesTextView = view.findViewById(R.id.tv_likes)
                accionButton = view.findViewById<Button>(R.id.btn_dar_like)
                accionButton.setOnClickListener{ anadirLike()}
            }

            fun anadirLike(){
                numerolikes = numerolikes + 1
                likesTextView.text = numerolikes.toString()
                contexto.aumentarTotalLikes()
            }

        }

    //Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }


    // Setear los datos para la iteración
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.cedulaTextView.text = entrenadorActual.descripcion
        holder.accionButton.text = "Like ${entrenadorActual.nombre}"
        holder.likesTextView.text = "0"
    }
    // Tamaño del arreglo
    override fun getItemCount(): Int {
        return this.lista.size
    }


}