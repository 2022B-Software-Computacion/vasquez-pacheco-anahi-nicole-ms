package com.example.anvpapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.anvpapplication.databinding.ActivityAcicloVidaBinding

class ACicloVida : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAcicloVidaBinding

    var textoGlobal =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAcicloVidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_aciclo_vida)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        mostrarSnackbar("OnCreate")
    }//onCreate fin de bloque de codigo

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("OnCreate")
    }
    override fun onResume() {
        super.onResume()
        mostrarSnackbar("OnCreate")
    }
    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("OnCreate")
    }
    override fun onPause() {
        super.onPause()
        mostrarSnackbar("OnCreate")
    }
    override fun onStop() {
        super.onStop()
        mostrarSnackbar("OnCreate")
    }
    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("OnCreate")
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_aciclo_vida)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun mostrarSnackbar(texto: String){
        textoGlobal += texto
        Snackbar.make(findViewById(R.id.cl_ciclo_vida),
        textoGlobal, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

}