package com.example.examen2b

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Materia(
    @Exclude @JvmField var codigo: String?,
    val nombre: String,
    val num_estudiantes: Int,
    val profesor: String,
    @Exclude @JvmField var estudiantes: List<Estudiante> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.createTypedArrayList(Estudiante)!!
    )

    constructor(
        nombre: String,
        num_estudiantes: Int,
        profesor: String,
        estudiantes: List<Estudiante>
    ) : this(
        null,
        nombre,
        num_estudiantes,
        profesor,
        estudiantes
    )

    constructor() : this(null, "", 0, "", emptyList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(codigo)
        parcel.writeString(nombre)
        parcel.writeInt(num_estudiantes)
        parcel.writeString(profesor)
        parcel.writeTypedList(estudiantes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Materia> {
        override fun createFromParcel(parcel: Parcel): Materia {
            return Materia(parcel)
        }

        override fun newArray(size: Int): Array<Materia?> {
            return arrayOfNulls(size)
        }
    }
}