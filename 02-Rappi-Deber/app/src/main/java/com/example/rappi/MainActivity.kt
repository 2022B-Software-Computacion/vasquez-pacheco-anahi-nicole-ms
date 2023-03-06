package com.example.rappi


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rappi.adapter.RestauranteAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RestauranteProvider.RestauranteList
        initRecyclerViewRappi()

        val btnOpciones = findViewById<Button>(R.id.btn_comprar)
        btnOpciones.setOnClickListener {
            val launch = Intent(this, OpcionesPage::class.java)
            startActivity(launch)
            true
        }
    }

    private fun initRecyclerViewRappi(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_Rappi)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RestauranteAdapter(RestauranteProvider.RestauranteList)
    }
}