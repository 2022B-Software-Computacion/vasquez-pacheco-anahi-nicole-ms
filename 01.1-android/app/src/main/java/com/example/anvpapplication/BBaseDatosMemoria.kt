package com.example.anvpapplication

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador.add(BEntrenador(1,"Anahi", "a@a.com"))
            arregloBEntrenador.add(BEntrenador(2,"Nicole", "n@n.com"))
            arregloBEntrenador.add(BEntrenador(3, "Gabriela", "g@g.com"))

        }
    }
}