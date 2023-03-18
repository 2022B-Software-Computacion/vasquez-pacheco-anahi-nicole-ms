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


class EmpresaDepartamentos : AppCompatActivity() {
    lateinit var listViewDepartamentos: ListView
    lateinit var nombreEmpresaTextView: TextView

    val firestoreDB = FireStoreDB()

    var idItemSeleccionado = 0
    var departamentos: List<Departamento>? = null
    lateinit var nombreDepartamentos: ArrayList<String>
    lateinit var nombreEmpresa: String
    lateinit var idEmpresa: String
    private val actualizarDepartamento =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { departamentoActualizado ->

            if (departamentoActualizado.resultCode == Activity.RESULT_OK) {
                val updatedDepartamento =
                    departamentoActualizado.data?.getParcelableExtra<Departamento>("departamentoActualizado")

                if (updatedDepartamento != null) {
                    val index: Int =
                        departamentos!!.indexOfFirst { it.idDepartamento == updatedDepartamento.idDepartamento }

                    if (index >= 0) {
                        val updatedDepartamentos = departamentos!!.toMutableList()
                        updatedDepartamentos[index] = updatedDepartamento
                        departamentos = updatedDepartamentos.toList()
                        nombreDepartamentos[index] =
                            updatedDepartamento.nombreDepartamento + " " + updatedDepartamento.fechaInauguracion
                        (listViewDepartamentos.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    } else {
                        val updatedDepartamentos = departamentos!!.toMutableList()
                        updatedDepartamentos.add(updatedDepartamento)
                        departamentos = updatedDepartamentos.toList()
                        nombreDepartamentos.add(updatedDepartamento.nombreDepartamento + " " + updatedDepartamento.fechaInauguracion)
                        (listViewDepartamentos.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_departamentos)
        val btnCreateDepartamento = findViewById<Button>(R.id.btn_createDepartamento)
        btnCreateDepartamento.setOnClickListener {
            editarCreateDepartamento(null)
        }
        nombreEmpresaTextView = findViewById<TextView>(R.id.textview_nombreEmpresa)
        listViewDepartamentos = findViewById<ListView>(R.id.lv_Departamentos)

        idEmpresa = intent.getStringExtra("idEmpresa")!!
        nombreEmpresa = intent.getStringExtra("empresaName")!!
        departamentos = intent.getParcelableArrayListExtra<Departamento>("departamentos")

        nombreDepartamentos = departamentos?.map { it.nombreDepartamento + " " + it.fechaInauguracion } as ArrayList<String>

        val adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                nombreDepartamentos as List<String>
            )

        listViewDepartamentos.adapter = adapter

        nombreEmpresaTextView.text = nombreEmpresa
        adapter.notifyDataSetChanged()

        registerForContextMenu(listViewDepartamentos)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_departamentos, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.departamento_editar -> {
                editarCreateDepartamento(departamentos!![idItemSeleccionado])
                return true
            }
            R.id.departamento_eliminar-> {
                eliminarDepartamento()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun editarCreateDepartamento(
        departamento: Departamento?
    ) {
        val intent = Intent(this, DepartamentoAdapter::class.java)
        intent.putExtra("idEmpresa", idEmpresa)
        intent.putExtra("departamento", departamento)

        actualizarDepartamento.launch(intent)
    }


    fun eliminarDepartamento() {
        val departamentoIndex = idItemSeleccionado
        val departamentoID = departamentos!![idItemSeleccionado].idDepartamento!!

        firestoreDB.eliminarDepartamento(
            idEmpresa, departamentoID,
            onSuccess = {
                Toast.makeText(this, "Departamento eliminado con exito", Toast.LENGTH_SHORT)
                    .show()
                departamentos = departamentos?.filterIndexed { index, _ -> index != departamentoIndex }
                nombreDepartamentos.removeAt(departamentoIndex)
                (listViewDepartamentos.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            }
        ) { error ->
            Toast.makeText(
                this,
                "Error al eliminar el departamento: $error",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("Error al eliminar", error.toString())
        }
    }

}

