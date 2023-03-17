package com.example.examen2b

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Estudiante(
    @Exclude @JvmField var codigo: String?,
    val nombre: String = "",
    val apellido: String = "",
    val semestre: Int = 0
): Parcelable {

    constructor(
        nombre: String,
        apellido: String,
        semestre: Int
    ) : this(
        null,
        nombre,
        apellido,
        semestre
    )
    constructor() : this(null, "", "", 0)

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(codigo)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeInt(semestre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }

}
