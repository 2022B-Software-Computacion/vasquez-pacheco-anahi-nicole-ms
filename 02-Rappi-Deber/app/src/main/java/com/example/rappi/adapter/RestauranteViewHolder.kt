package com.example.rappi.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rappi.R
import com.example.rappi.Restaurante

class RestauranteViewHolder (view: View): RecyclerView.ViewHolder(view) {

    val nombre = view.findViewById<TextView>(R.id.tvRestauranteNombre)
    val tiempo = view.findViewById<TextView>(R.id.tvRestauranteTiempo)
    val costoEnvio = view.findViewById<TextView>(R.id.tvRestauranteCostoEnvio)
    val calificacion = view.findViewById<TextView>(R.id.tvRestauranteCalificacion)
    val photo = view.findViewById<ImageView>(R.id.ivRestaurante)


    fun render(restauranteModel: Restaurante){
        nombre.text = restauranteModel.nombre
        tiempo.text = restauranteModel.tiempo
        costoEnvio.text = restauranteModel.costoEnvio
        calificacion.text = restauranteModel.calificacion
        Glide.with(photo.context).load(restauranteModel.photo).into(photo)

    }

}