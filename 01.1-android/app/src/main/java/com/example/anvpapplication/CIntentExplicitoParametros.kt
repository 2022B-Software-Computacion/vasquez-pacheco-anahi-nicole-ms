package com.example.anvpapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenadorPrincipal")
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)

        //Intent como se comunica el android
        boton.setOnClickListener { devolverRespuesta() }
    }

    private fun devolverRespuesta() {

        val intentDevolverParamatros = Intent()
        intentDevolverParamatros.putExtra("nombreModificado", "Vicente")
        intentDevolverParamatros.putExtra("edadModificado", 33)
        setResult(
            RESULT_OK,
            intentDevolverParamatros
        )
        finish()
    }
}