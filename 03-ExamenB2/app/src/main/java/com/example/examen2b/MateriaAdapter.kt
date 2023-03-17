package com.example.examen2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MateriaAdapter : AppCompatActivity() {

    val firestoreDB = FireStoreDB()
    lateinit var idMateria: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia)

        val nombreInput = findViewById<EditText>(R.id.id_nombre_input_materia)
        val numestInput = findViewById<EditText>(R.id.id_numest_input_materia)
        val profesorInput = findViewById<EditText>(R.id.id_profesor_input_materia)
        val crearButton = findViewById<Button>(R.id.btn_crear_materia)

        val materia = intent.getParcelableExtra<Materia>("materia")

        if (materia != null) {
            idMateria = materia.codigo!!
            nombreInput.setText(materia.nombre)
            numestInput.setText(materia.num_estudiantes.toString())
            profesorInput.setText(materia.profesor)
            crearButton.setOnClickListener {
                val nombre = nombreInput.text.toString()
                val num_estudiantes = numestInput.text.toString().toInt()
                val profesor = profesorInput.text.toString()
                val newMateria = Materia(materia.codigo, nombre, num_estudiantes, profesor)
                firestoreDB.actualizarMateria(idMateria, newMateria, {
                    setResult(
                        Activity.RESULT_OK,
                        Intent().putExtra("materiaActualizada", newMateria)
                    )
                    finish()
                }, { exception -> print("Error actualizando materia") }
                )
            }
        } else {
            crearButton.setOnClickListener {
                val nombre = nombreInput.text.toString()
                val num_estudiantes = numestInput.text.toString().toInt()
                val profesor = profesorInput.text.toString()
                val materia = Materia(nombre, num_estudiantes, profesor, emptyList())

                firestoreDB.crearMateria(materia,
                    {
                        materia.codigo = it
                        setResult(
                            Activity.RESULT_OK,
                            Intent().putExtra("materiaActualizada", materia)
                        )
                        finish()
                    }, { exception -> print("Error creando la materia") }
                )
            }
        }
    }
}