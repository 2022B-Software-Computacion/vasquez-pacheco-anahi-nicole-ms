package com.example.examen2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EmpresaAdapter : AppCompatActivity() {

    val firestoreDB = FireStoreDB()
    lateinit var idEmpresa: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa)

        val nombreEmpresaInput = findViewById<EditText>(R.id.input_nombre_empresa)
        val cantidadDepInput = findViewById<EditText>(R.id.input_cant_departamentos)
        val nombreGerenteInput = findViewById<EditText>(R.id.input_nombre_gerente)
        val crearButton = findViewById<Button>(R.id.btn_crear_empresa)

        val empresa = intent.getParcelableExtra<Empresa>("empresa")

        if (empresa != null) {
            nombreEmpresaInput.setText(empresa.nombreEmpresa)
            cantidadDepInput.setText(empresa.cantDepartamentos.toString())
            nombreGerenteInput.setText(empresa.nombreGerente)
            crearButton.setOnClickListener {
                val nombreEmpresa = nombreEmpresaInput.text.toString()
                val cantidadDepartamento = cantidadDepInput.text.toString().toInt()
                val nombreGerente = nombreGerenteInput.text.toString()
                val newEmpresa = Empresa(empresa.idEmpresa, nombreEmpresa, cantidadDepartamento, nombreGerente)

                firestoreDB.actualizarEmpresa(idEmpresa, newEmpresa, {
                    setResult(
                        Activity.RESULT_OK,
                        Intent().putExtra("Empresa Actualizada Existosamente", newEmpresa)
                    )
                    finish()
                }
                ) { exception -> print("Error en la actualización de la empresa!") }
            }
        } else {
            crearButton.setOnClickListener {
                val nombreEmpresa = nombreEmpresaInput.text.toString()
                val cantidadDepartamento = cantidadDepInput.text.toString().toInt()
                val nombreGerente = nombreGerenteInput.text.toString()
                val empresa = Empresa(nombreEmpresa, cantidadDepartamento, nombreGerente, emptyList())

                firestoreDB.crearEmpresa(empresa,
                    {
                        empresa.idEmpresa = it
                        setResult(
                            Activity.RESULT_OK,
                            Intent().putExtra("Empresa Actualizada Existosamente", empresa)
                        )
                        finish()
                    }, { exception -> print("Error en la actualización de la empresa!") }
                )
            }
        }
    }
}