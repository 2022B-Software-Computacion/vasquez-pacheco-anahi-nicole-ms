package com.example.examen2b

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Empresa(
    @Exclude @JvmField
    var idEmpresa: String?,
    val nombreEmpresa: String,
    val cantDepartamentos: Int,
    val nombreGerente: String,
    @Exclude @JvmField var departamentos: List<Departamento> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.createTypedArrayList(Departamento)!!
    )

    constructor(
        nombreEmpresa: String,
        cantDepartamentos: Int,
        nombreGerente: String,
        departamentos: List<Departamento>

    ) : this(
        null,
        nombreEmpresa,
        cantDepartamentos,
        nombreGerente,
        departamentos
    )

    constructor() : this(null, "", 0, "", emptyList())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idEmpresa)
        parcel.writeString(nombreEmpresa)
        parcel.writeInt(cantDepartamentos)
        parcel.writeString(nombreGerente)
        parcel.writeTypedList(departamentos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Empresa> {
        override fun createFromParcel(parcel: Parcel): Empresa {
            return Empresa(parcel)
        }

        override fun newArray(size: Int): Array<Empresa?> {
            return arrayOfNulls(size)
        }
    }
}