package com.example.anvpapplication

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this, //Contexto
            android.R.layout.simple_list_item_1, // Como se va ver dentro del XML
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView.setOnClickListener { anadirEntrenador(adaptador) }

        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu) //Llenamos el XML dentro de la opción
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id

    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar", DialogInterface.OnClickListener { dialog, which ->
                // Al aceptar eliminar el registro
            }
        )
        builder.setNegativeButton(
            "Cancelar", null
        )

        val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
        val seleccionPrevia = booleanArrayOf(
            true, // Lunes SI seleccionado
            false, // Martes NO seleccionado
            false // Miercoles NO seleccionado
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            { dialog, which, isChecked -> "Dio click en el item $which" }
        )
        val dialogo = builder.create()
        dialogo.show()

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // abrir el diagolo
        return when (item.itemId) {
            R.id.mi_editar -> {
                "${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar -> {
                abrirDialogo()
                "${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    fun anadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ) {
        arreglo.add(
            BEntrenador(1, "Anahi", "Descripcion")
        )
        adaptador.notifyDataSetChanged()
    }
}