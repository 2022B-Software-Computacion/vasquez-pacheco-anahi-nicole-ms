package com.example.examen2b

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Departamento(
    @Exclude @JvmField
    var idDepartamento: String?,
    val nombreDepartamento: String = "",
    val fechaInauguracion: String = "",
    val ganancias: Int = 0,
): Parcelable {

    constructor(
        nombreDepartamento: String,
        fechaInauguracion: String,
        ganancias: Int,
    ) : this(
        null,
        nombreDepartamento,
        fechaInauguracion,
        ganancias,
    )
    constructor() : this(null, "", "", 0)

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idDepartamento)
        parcel.writeString(nombreDepartamento)
        parcel.writeString(fechaInauguracion)
        parcel.writeInt(ganancias)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Departamento> {
        override fun createFromParcel(parcel: Parcel): Departamento {
            return Departamento(parcel)
        }

        override fun newArray(size: Int): Array<Departamento?> {
            return arrayOfNulls(size)
        }
    }

}
