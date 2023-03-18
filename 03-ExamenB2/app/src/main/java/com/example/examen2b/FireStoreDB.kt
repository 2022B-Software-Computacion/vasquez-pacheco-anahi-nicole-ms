package com.example.examen2b

import com.google.firebase.firestore.FirebaseFirestore

class FireStoreDB {

    private val db = FirebaseFirestore.getInstance()

    private val empresasCollection = db.collection("empresas")

    // ---------- EMPRESAS ----------

    fun crearEmpresa(
        empresa: Empresa,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        empresasCollection.add(empresa)
            .addOnSuccessListener { documentReference ->
                val departamentoRef = documentReference.collection("departamentos")
                for (dep in empresa.departamentos) {
                    departamentoRef.add(dep)
                        .addOnSuccessListener {
                            dep.idDepartamento = it.id
                        }
                        .addOnFailureListener { onFailure(it) }
                }
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun actualizarEmpresa(
        idEmpresa: String,
        empresa: Empresa,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        empresasCollection.document(idEmpresa.toString()).set(empresa)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun eliminarEmpresa(
        idEmpresa: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        empresasCollection.document(idEmpresa.toString()).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // ---------- DEPARTAMENTOS ----------

    fun crearDepartamento(
        idEmpresa: String,
        departamento: Departamento,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val departamentoRef = empresasCollection.document(idEmpresa.toString()).collection("departamentos")
        departamentoRef.add(departamento)
            .addOnSuccessListener {
                departamento.idDepartamento = it.id
                onSuccess()
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun actualizarDepartamento(
        idEmpresa: String,
        departamento: Departamento,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val departamentosRef = empresasCollection.document(idEmpresa.toString()).collection("departamentos")
        departamentosRef.document(departamento.idDepartamento!!).set(departamento)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun eliminarDepartamento(
        idEmpresa: String,
        idDepartamento: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val departamentoCollection =
            empresasCollection.document(idEmpresa.toString()).collection("departamentos").document(idDepartamento)
        departamentoCollection.get()
            .addOnSuccessListener { departamentoDoc ->
                if (departamentoDoc.exists()) {
                    departamentoCollection.delete()
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onFailure(it) }
                }
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getEmpresas(onSuccess: (List<Empresa>) -> Unit, onFailure: (Exception) -> Unit) {
        empresasCollection.get()
            .addOnSuccessListener { result ->
                val empresas = mutableListOf<Empresa>()
                for (document in result) {
                    val empresa = document.toObject(Empresa::class.java)
                    empresa.idEmpresa = document.id
                    empresas.add(empresa)
                    getDepartamentos(empresa.idEmpresa!!, onSuccess = {
                        empresa.departamentos = it
                    }, onFailure = {})
                }
                onSuccess(empresas)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun getDepartamentos(
        idEmpresa: String,
        onSuccess: (List<Departamento>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val departamentosCollection = empresasCollection.document(idEmpresa.toString()).collection("departamentos")

        departamentosCollection.get()
            .addOnSuccessListener { result ->
                val departamentos = mutableListOf<Departamento>()
                for (document in result) {
                    val departamento = document.toObject(Departamento::class.java)
                    departamento.idDepartamento = document.id
                    departamentos.add(departamento)
                }
                onSuccess(departamentos)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }
}