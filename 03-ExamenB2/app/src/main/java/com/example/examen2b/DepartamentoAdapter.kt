package com.example.examen2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class DepartamentoAdapter : AppCompatActivity() {

    val firestoreDB = FireStoreDB()
    lateinit var idDepartamento: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento)

        val nombreDepartamentoInput = findViewById<EditText>(R.id.input_nombre_departamento)
        val fechaInauguracionInput = findViewById<EditText>(R.id.input_fecha_inauguracion)
        val gananciasInput = findViewById<EditText>(R.id.input_ganancias)
        val crearButton = findViewById<Button>(R.id.btn_crear_departamento)

        idDepartamento = intent.getStringExtra("idEmpresa")!!
        val departamento = intent.getParcelableExtra<Departamento>("departamento")

        if (departamento != null){
            nombreDepartamentoInput.setText(departamento.nombreDepartamento)
            fechaInauguracionInput.setText(departamento.fechaInauguracion)
            gananciasInput.setText(departamento.ganancias.toString())
            crearButton.setOnClickListener {
                val nombreDepartamento = nombreDepartamentoInput.text.toString()
                val fechaInauguracion = fechaInauguracionInput.text.toString()
                val ganancias = gananciasInput.text.toString().toInt()
                val newDepartamento = Departamento(idDepartamento ,nombreDepartamento, fechaInauguracion, ganancias)

                firestoreDB.actualizarDepartamento(idDepartamento,newDepartamento,{
                    setResult(Activity.RESULT_OK, Intent().putExtra("Departamento Actualizado Existosamente", newDepartamento))
                    finish()
                }) { exception ->
                    print("Error en la actualización del departamento!")
                }
            }
        }else{
            crearButton.setOnClickListener {
                val nombreDepartamento = nombreDepartamentoInput.text.toString()
                val fechaInauguracion = fechaInauguracionInput.text.toString()
                val ganancias = gananciasInput.text.toString().toInt()
                val departamento = Departamento(idDepartamento, nombreDepartamento, fechaInauguracion, ganancias)

                firestoreDB.crearDepartamento(idDepartamento ,departamento,
                    {
                        setResult(Activity.RESULT_OK, Intent().putExtra("Departamento Actualizado Existosamente", departamento))
                        finish()
                    }
                ) { exception -> print("Error en la actualización del departamento!") }
            }
        }
    }
}