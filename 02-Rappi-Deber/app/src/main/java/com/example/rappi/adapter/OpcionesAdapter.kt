package com.example.rappi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rappi.Opciones
import com.example.rappi.R


class OpcionesAdapter (private val opcionesList: List<Opciones>) : RecyclerView.Adapter<OpcionesViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpcionesViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return OpcionesViewHolder(layoutInflater.inflate(R.layout.item_opciones, parent, false))
    }

    override fun onBindViewHolder(holder: OpcionesViewHolder, position: Int) {
        val item = opcionesList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return opcionesList.size
    }
}