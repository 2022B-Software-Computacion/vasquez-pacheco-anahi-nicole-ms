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
    lateinit var empresas: List<Empresa>
    lateinit var empresaNames: ArrayList<String>
    lateinit var empresaListView: ListView

    private val actualizarEmpresa =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val empresaActualizada =
                    result.data?.getParcelableExtra<Empresa>("empresaActualizada")

                if (empresaActualizada != null) {
                    val index: Int =
                        empresas.indexOfFirst { it.idEmpresa == empresaActualizada.idEmpresa }

                    if (index >= 0) {
                        val departamentosActualizados = empresas.toMutableList()
                        departamentosActualizados[index] = empresaActualizada
                        empresas = departamentosActualizados.toList()

                        empresaNames[index] = empresaActualizada.nombreEmpresa
                        Log.e("TAG", empresaNames.toString())

                        (empresaListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    } else {
                        val departamentosActualizados = empresas.toMutableList()
                        departamentosActualizados.add(empresaActualizada)
                        empresas = departamentosActualizados.toList()
                        empresaNames.add(empresaActualizada.nombreEmpresa)
                        (empresaListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
                    }
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.empresaListView = findViewById<ListView>(R.id.lv_Empresa)
        getEmpresas()
        registerForContextMenu(empresaListView)
        val btnCrearEmpresa: Button = findViewById<Button>(R.id.btn_crear_empresa)
        btnCrearEmpresa.setOnClickListener {
            actualizarEmpresa(null)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.context_menu_empresa, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                actualizarEmpresa(empresas[idItemSeleccionado])
                return true
            }
            R.id.mi_eliminar -> {
                eliminarEmpresa()
                return true
            }
            R.id.mi_departamento -> {
                getDepartamentos()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun actualizarEmpresa(
        empresa: Empresa?
    ) {
        val intent = Intent(this, EmpresaAdapter::class.java)
        intent.putExtra("empresa", empresa)
        actualizarEmpresa.launch(intent)
    }

    fun eliminarEmpresa() {
        val empresaIndex = idItemSeleccionado
        val empresaId = empresas[empresaIndex].idEmpresa

        firestoreDB.eliminarEmpresa(empresaId!!,
            onSuccess = {
                Toast.makeText(this, "Empresa eliminada con exito", Toast.LENGTH_SHORT)
                    .show()
                empresas = empresas.filterIndexed { index, _ -> index != empresaIndex }
                empresaNames.removeAt(empresaIndex)
                (empresaListView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            },
            onFailure = { error -> print("Error eliminando empresa") }
        )

    }

    fun getDepartamentos() {
        val empresa = empresas[idItemSeleccionado]

        val intent = Intent(this, EmpresaDepartamentos::class.java)
        intent.putExtra("idEmpresa", empresa.idEmpresa)
        intent.putExtra("empresaName", empresa.nombreEmpresa)
        intent.putParcelableArrayListExtra(
            "departamentos",
            ArrayList(empresa.departamentos)
        )

        startActivity(intent)
    }

    fun getEmpresas() {
        firestoreDB.getEmpresas(
            onSuccess = { empresas ->
                this.empresas = empresas
                empresaNames = empresas.map { it.nombreEmpresa } as ArrayList<String>
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, empresaNames)
                empresaListView.adapter = adapter
                adapter.notifyDataSetChanged()
            },
            onFailure = { error ->
                print("Error retornando empresas")
            }
        )
    }


}