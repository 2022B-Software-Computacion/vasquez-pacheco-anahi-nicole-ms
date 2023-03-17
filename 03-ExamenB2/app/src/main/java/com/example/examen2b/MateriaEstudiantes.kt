package com.example.examen2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts


class MateriaEstudiantes : AppCompatActivity() {
    lateinit var listViewEstudiantes: ListView
    lateinit var nombreMateriaTextView: TextView

    val firestoreDB = FireStoreDB()

    var idItemSeleccionado = 0
    var estudiantes: List<Estudiante>? = null
    lateinit var nombreEstudiantes: ArrayList<String>
    lateinit var nombreMateria: String
    lateinit var idMateria: String
    private val actualizarEstudiante =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { estudianteActualizado ->

            if (estudianteActualizado.resultCode == Activity.RESULT_OK) {
                val updatedEstudiante =
                    estudianteActualizado.data?.getParcelableExtra<Estudiante>("estudianteActualizado")

                if (updatedEstudiante != null) {
                    val index: Int =
                        estudiantes!!.indexOfFirst { it.codigo == updatedEstudiante.codigo }

                    if (index >= 0) {
                        val updatedEstudiantes = estudiantes!!.toMutableList()
                        updatedEstudiantes[index] = updatedEstudiante
                        estudiantes = updatedEstudiantes.toList()
                        nombreEstudiantes[index] =
                            updatedEstudiante.nombre + " " + updatedEstudiante.apellido
                        (listViewEstudiantes.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    } else {
                        val updatedEstudiantes = estudiantes!!.toMutableList()
                        updatedEstudiantes.add(updatedEstudiante)
                        estudiantes = updatedEstudiantes.toList()
                        nombreEstudiantes.add(updatedEstudiante.nombre + " " + updatedEstudiante.apellido)
                        (listViewEstudiantes.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia_estudiantes)
        val btnCreateEstudiante = findViewById<Button>(R.id.id_btn_createEstudiante)
        btnCreateEstudiante.setOnClickListener {
            editarCreateEstudiante(null)
        }
        nombreMateriaTextView = findViewById<TextView>(R.id.id_textview_nombreMateria)
        listViewEstudiantes = findViewById<ListView>(R.id.id_listview_estudiantes)

        idMateria = intent.getStringExtra("idMateria")!!
        nombreMateria = intent.getStringExtra("materiaName")!!
        estudiantes = intent.getParcelableArrayListExtra<Estudiante>("estudiantes")

        nombreEstudiantes = estudiantes?.map { it.nombre + " " + it.apellido } as ArrayList<String>

        val adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                nombreEstudiantes as List<String>
            )

        listViewEstudiantes.adapter = adapter

        nombreMateriaTextView.text = nombreMateria
        adapter.notifyDataSetChanged()

        registerForContextMenu(listViewEstudiantes)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_estudiantes, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.estudiante_editar -> {
                editarCreateEstudiante(estudiantes!![idItemSeleccionado])
                return true
            }
            R.id.estudiante_eliminar -> {
                eliminarEstudiante()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun editarCreateEstudiante(
        estudiante: Estudiante?
    ) {
        val intent = Intent(this, EstudianteAdapter::class.java)
        intent.putExtra("idMateria", idMateria)
        intent.putExtra("estudiante", estudiante)

        actualizarEstudiante.launch(intent)
    }

    fun eliminarEstudiante() {

        val estudianteIndex = idItemSeleccionado
        val estudianteID = estudiantes!![idItemSeleccionado].codigo!!
        firestoreDB.eliminarEstudiante(
            idMateria, estudianteID,
            onSuccess = {
                Toast.makeText(this, "Estudiante eliminado con exito", Toast.LENGTH_SHORT)
                    .show()
                estudiantes = estudiantes?.filterIndexed { index, _ -> index != estudianteIndex }
                nombreEstudiantes.removeAt(estudianteIndex)
                (listViewEstudiantes.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            },
            onFailure = { error ->
                Toast.makeText(
                    this,
                    "Error al eliminar el estudiante: $error",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Error al eliminar", error.toString())
            }
        )
    }


}
