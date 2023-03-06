package com.example.rappi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rappi.adapter.OpcionesAdapter
import com.example.rappi.databinding.ActivityOpcionesBinding


class OpcionesPage : AppCompatActivity(){

    private lateinit var binding: ActivityOpcionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerViewOpciones()
    }

    private fun initRecyclerViewOpciones(){
        binding.recyclerViewOpciones.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewOpciones.adapter = OpcionesAdapter(OpcionesProvider.OpcionesList)
    }

}