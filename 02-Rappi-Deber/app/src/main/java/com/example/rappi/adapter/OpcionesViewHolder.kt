package com.example.rappi.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rappi.R
import com.example.rappi.Opciones
import com.example.rappi.Restaurante

class OpcionesViewHolder (view: View): RecyclerView.ViewHolder(view){

    val nombreOpcion = view.findViewById<TextView>(R.id.tvOpcionesNombre)
    val descripcion = view.findViewById<TextView>(R.id.tvOpcionesDescripcion)
    val precioOpcion = view.findViewById<TextView>(R.id.tvOpcionesPrecio)
    val fotoOpcion = view.findViewById<ImageView>(R.id.ivOpciones)

    fun render(opcionesModel: Opciones){
        nombreOpcion.text = opcionesModel.nombreOpcion
        descripcion.text = opcionesModel.descripcion
        precioOpcion.text = opcionesModel.precioOpcion
        Glide.with(fotoOpcion.context).load(opcionesModel.fotoOpcion).into(fotoOpcion)

    }

}