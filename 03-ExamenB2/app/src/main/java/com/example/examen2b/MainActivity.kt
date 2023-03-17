package com.example.examen2b

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    val firestoreDB = FireStoreDB()
    var idItemSeleccionado = 0
    lateinit var materias: List<Materia>
    lateinit var materiaNames: ArrayList<String>
    lateinit var materiaListView: ListView

    private val actualizarMateria =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val materiaActualizada =
                    result.data?.getParcelableExtra<Materia>("materiaActualizada")

                if (materiaActualizada != null) {
                    val index: Int =
                        materias.indexOfFirst { it.codigo == materiaActualizada.codigo }

                    if (index >= 0) {
                        val estudiantesActualizados = materias.toMutableList()
                        estudiantesActualizados[index] = materiaActualizada
                        materias = estudiantesActualizados.toList()

                        materiaNames[index] = materiaActualizada.nombre
                        Log.e("TAG", materiaNames.toString())

                        (materiaListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    } else {
                        val estudiantesActualizados = materias.toMutableList()
                        estudiantesActualizados.add(materiaActualizada)
                        materias = estudiantesActualizados.toList()
                        materiaNames.add(materiaActualizada.nombre)
                        (materiaListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    }
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.materiaListView = findViewById<ListView>(R.id.id_listview_Materia)
        getMaterias()
        registerForContextMenu(materiaListView)
        val btnCrearMateria: Button = findViewById<Button>(R.id.btn_crear_materia)
        btnCrearMateria.setOnClickListener {
            actualizarMateria(null)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_materia, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                actualizarMateria(materias[idItemSeleccionado])
                return true
            }
            R.id.mi_eliminar -> {
                eliminarMateria()
                return true
            }
            R.id.mi_estudiantes -> {
                getEstudiantes()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun actualizarMateria(
        materia: Materia?
    ) {
        val intent = Intent(this, MateriaAdapter::class.java)
        intent.putExtra("materia", materia)
        actualizarMateria.launch(intent)
    }

    fun eliminarMateria() {
        val materiaIndex = idItemSeleccionado
        val materiaId = materias[materiaIndex].codigo

        firestoreDB.eliminarMateria(materiaId!!,
            onSuccess = {
                Toast.makeText(this, "Materia eliminada con exito", Toast.LENGTH_SHORT)
                    .show()
                materias = materias.filterIndexed { index, _ -> index != materiaIndex }
                materiaNames.removeAt(materiaIndex)
                (materiaListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            },
            onFailure = { error -> print("Error eliminando materia") }
        )

    }

    fun getEstudiantes() {
        val materia = materias[idItemSeleccionado]

        val intent = Intent(this, MateriaEstudiantes::class.java)
        intent.putExtra("idMateria", materia.codigo)
        intent.putExtra("materiaName", materia.nombre)
        intent.putParcelableArrayListExtra(
            "estudiantes",
            ArrayList(materia.estudiantes)
        )

        startActivity(intent)
    }

    fun getMaterias() {
        firestoreDB.getMaterias(
            onSuccess = { materias ->
                this.materias = materias
                materiaNames = materias.map { it.nombre } as ArrayList<String>
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, materiaNames)
                materiaListView.adapter = adapter
                adapter.notifyDataSetChanged()
            },
            onFailure = { error ->
                print("Error retornando materias")
            }
        )
    }


}