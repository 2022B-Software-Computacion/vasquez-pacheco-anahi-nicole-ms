package com.example.rappi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rappi.R
import com.example.rappi.Restaurante

class RestauranteAdapter(private val restauranteList: List<Restaurante>) : RecyclerView.Adapter<RestauranteViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return RestauranteViewHolder(layoutInflater.inflate(R.layout.item_restaurante, parent, false))
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val item = restauranteList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return restauranteList.size
    }

}