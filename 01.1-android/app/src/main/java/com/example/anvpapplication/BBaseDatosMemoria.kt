package com.example.anvpapplication

class BBaseDatosMemoria {

    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador("Anahi", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Alexis", "a1@a1.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Leonardo", "l@l.com")
                )
        }
    }

}