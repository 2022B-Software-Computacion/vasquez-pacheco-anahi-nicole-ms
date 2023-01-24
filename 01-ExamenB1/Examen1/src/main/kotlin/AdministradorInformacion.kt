import java.io.File
import java.io.InputStream
import java.time.LocalDate

class AdministradorInformacion {

    //LEER ARCHIVO
    fun leerInfo():ArrayList<Empresa>{
        val inputStream: InputStream = File("C:\\Users\\escritorio.virtual27\\Documents\\anahivasquezp\\vasquez-pacheco-anahi-nicole-ms\\01-ExamenB1\\Examen1\\src\\main\\resources\\Informacion.txt").inputStream()
        val registros = mutableListOf<String>()
        inputStream.bufferedReader().useLines { lines ->
            lines.forEach {
                registros.add(it)
            }
        }

        var listaEmpresas = ArrayList<Empresa>()
        var posEmpresa = 0

        registros.forEach{ linea: String->
            val tokens = listOf(*linea.split("\\s*_\\s*".toRegex()).toTypedArray())

            if(tokens[0].equals("Empresa")){
                val miEmpresa = Empresa()
                miEmpresa.idEmpresa = tokens[2].toInt()
                miEmpresa.nombreEmpresa = tokens[4]
                miEmpresa.valorEmpresa = tokens[6].toDouble()
                miEmpresa.fechaCreacion = LocalDate.parse(tokens[8])
                miEmpresa.esNacional = tokens[10].toBoolean()
                listaEmpresas.add(miEmpresa)
                posEmpresa = listaEmpresas.indexOf(miEmpresa)
            }else{
                if(tokens[0].equals("Departamentos") ){
                    val miDepartamento = Departamento()
                    miDepartamento.idDepartamento = tokens[2].toInt()
                    miDepartamento.nombreDepartamento = tokens[4]
                    miDepartamento.fechaInauguracion = LocalDate.parse(tokens[6])
                    miDepartamento.esPrincipal = tokens[8].toBoolean()
                    miDepartamento.ganancias = tokens[10].toFloat()
                    listaEmpresas[posEmpresa].listaDepartamentos.add(miDepartamento)
                }
            }
        }
        return listaEmpresas
    }

    //ESCRIBIR ARCHIVO
    fun escribirInfo(texto:ArrayList<Empresa>){

        val documento = File("C:\\Users\\escritorio.virtual27\\Documents\\anahivasquezp\\vasquez-pacheco-anahi-nicole-ms\\01-ExamenB1\\Examen1\\src\\main\\resources\\Informacion.txt")
        documento.writeText("")
        texto.forEach { empresa: Empresa ->
            documento.appendText("Empresa_ ")
            documento.appendText("Id Empresa_" + empresa.idEmpresa
                    + " _Nombre Empresa_" + empresa.nombreEmpresa
                    +" _Valor_"+empresa.valorEmpresa
                    +" _Fecha Creacion_"+empresa.fechaCreacion
                    +" _¿Esta Nacional?_"+empresa.esNacional
                    +"\n")

            empresa.listaDepartamentos.forEach { miDepartamento: Departamento ->
                documento.appendText("Departamentos_ ")
                documento.appendText("Id Departamento_" + miDepartamento.idDepartamento
                        +" _Nombre Departamento_"+miDepartamento.nombreDepartamento
                        +" _Fecha Inauguracion_"+miDepartamento.fechaInauguracion
                        +" _¿Es Principal?_"+miDepartamento.esPrincipal
                        +" _Ganancias_"+miDepartamento.ganancias
                        +"\n")
            }
        }
    }

}