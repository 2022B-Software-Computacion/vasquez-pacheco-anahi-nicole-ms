package com.example.examen2b

import com.google.firebase.firestore.FirebaseFirestore

class FireStoreDB {

    private val db = FirebaseFirestore.getInstance()

    private val materiasCollection = db.collection("materias")

    // ---------- MATERIAS ----------

    fun crearMateria(
        materia: Materia,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        materiasCollection.add(materia)
            .addOnSuccessListener { documentReference ->
                val estudiantesRef = documentReference.collection("estudiantes")
                for (estudiante in materia.estudiantes) {
                    estudiantesRef.add(estudiante)
                        .addOnSuccessListener {
                            estudiante.codigo = it.id
                        }
                        .addOnFailureListener { onFailure(it) }
                }
                onSuccess(documentReference.id)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun actualizarMateria(
        idMateria: String,
        materia: Materia,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        materiasCollection.document(idMateria).set(materia)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun eliminarMateria(
        idMateria: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        materiasCollection.document(idMateria).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // ---------- ESTUDIANTES ----------

    fun crearEstudiante(
        idMateria: String,
        estudiante: Estudiante,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val estudiantesRef = materiasCollection.document(idMateria).collection("estudiantes")
        estudiantesRef.add(estudiante)
            .addOnSuccessListener {
                estudiante.codigo = it.id
                onSuccess()
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun actualizarEstudiante(
        idMateria: String,
        estudiante: Estudiante,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val estudiantesRef = materiasCollection.document(idMateria).collection("estudiantes")
        estudiantesRef.document(estudiante.codigo!!).set(estudiante)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun eliminarEstudiante(
        idMateria: String,
        idEstudiante: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val estudianteCollection =
            materiasCollection.document(idMateria).collection("estudiantes").document(idEstudiante)
        estudianteCollection.get()
            .addOnSuccessListener { estudianteDoc ->
                if (estudianteDoc.exists()) {
                    estudianteCollection.delete()
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onFailure(it) }
                }
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getMaterias(onSuccess: (List<Materia>) -> Unit, onFailure: (Exception) -> Unit) {
        materiasCollection.get()
            .addOnSuccessListener { result ->
                val materias = mutableListOf<Materia>()
                for (document in result) {
                    val materia = document.toObject(Materia::class.java)
                    materia.codigo = document.id
                    materias.add(materia)
                    getEstudiantes(materia.codigo!!, onSuccess = {
                        materia.estudiantes = it
                    }, onFailure = {})
                }
                onSuccess(materias)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun getEstudiantes(
        idMateria: String,
        onSuccess: (List<Estudiante>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val estudiantesCollection = materiasCollection.document(idMateria).collection("estudiantes")

        estudiantesCollection.get()
            .addOnSuccessListener { result ->
                val estudiantes = mutableListOf<Estudiante>()
                for (document in result) {
                    val estudiante = document.toObject(Estudiante::class.java)
                    estudiante.codigo = document.id
                    estudiantes.add(estudiante)
                }
                onSuccess(estudiantes)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }
}