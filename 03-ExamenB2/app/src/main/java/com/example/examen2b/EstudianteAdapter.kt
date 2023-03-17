package com.example.examen2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EstudianteAdapter : AppCompatActivity() {

    val firestoreDB = FireStoreDB()
    lateinit var idMateria: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiante)

        val nombreInput = findViewById<EditText>(R.id.id_nombre_input)
        val apellidoInput = findViewById<EditText>(R.id.id_apellido_input)
        val semestreInput = findViewById<EditText>(R.id.id_semestre_input)
        val crearButton = findViewById<Button>(R.id.btn_crear_estudiante)

        idMateria = intent.getStringExtra("idMateria")!!
        val estudiante = intent.getParcelableExtra<Estudiante>("estudiante")

        if (estudiante != null){
            nombreInput.setText(estudiante.nombre)
            apellidoInput.setText(estudiante.apellido)
            semestreInput.setText(estudiante.semestre.toString())
            crearButton.setOnClickListener {
                val nombre = nombreInput.text.toString()
                val apellido = apellidoInput.text.toString()
                val semestre = semestreInput.text.toString().toInt()
                val newEstudiante = Estudiante(estudiante.codigo ,nombre, apellido, semestre)
                firestoreDB.actualizarEstudiante(idMateria,newEstudiante,{
                    setResult(Activity.RESULT_OK, Intent().putExtra("estudianteActualizado", newEstudiante))
                    finish()
                }, { exception -> print("Error actualizando estudiante")
                })
            }
        }else{
            crearButton.setOnClickListener {
                val nombre = nombreInput.text.toString()
                val apellido = apellidoInput.text.toString()
                val semestre = semestreInput.text.toString().toInt()
                val estudiante = Estudiante(nombre, apellido, semestre)
                firestoreDB.crearEstudiante(idMateria ,estudiante,
                    {
                        setResult(Activity.RESULT_OK, Intent().putExtra("estudianteActualizado", estudiante))
                        finish()
                    }
                    , { exception -> print("Error creando estudiante") }
                )
            }
        }
    }
}