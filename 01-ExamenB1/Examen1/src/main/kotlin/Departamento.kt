import java.time.LocalDate

class Departamento {

    var idDepartamento: Int
    var nombreDepartamento:String
    var fechaInauguracion = LocalDate.parse("2001-01-19")
    var esPrincipal: Boolean
    var ganancias: Float

    //CONSTRUCTOR PRIMARIO
    init {
        this.idDepartamento = 0
        this.nombreDepartamento = null.toString()
        this.fechaInauguracion
        this.esPrincipal = false
        this.ganancias = 0.0F
    }

    //----------CRUD DEPARTAMENTO----------

    //CREATE
    fun crearDepartamento() {
        println("Id Departamento: ")
        this.idDepartamento = readln().toInt()
        println("Nombre Departamento: ")
        this.nombreDepartamento = readln()
        println("Fecha de Inauguración (AAAA-MM-DD): ")
        this.fechaInauguracion = LocalDate.parse(readln())
        println("¿Es un Departamento Principal? (Y/N): ")
        this.esPrincipal = if(readln().equals("Y")) true else false
        println("Ganancias del Departamento: ")
        this.ganancias = readln().toFloat()
    }

    //READ
    fun encontrarDepartamento
                (idDepartamento:Int, listaDepartamentos: ArrayList<Departamento>):Departamento{
        var auxDepartamento = Departamento()
        listaDepartamentos.forEach{ dep:Departamento ->
            if(dep.idDepartamento.equals(idDepartamento)){
                auxDepartamento =  dep
            }
        }
        return auxDepartamento
    }

    //UPDATE
    fun actualizarDepartamento
                (idDepartamento:Int, listaDepartamentos: ArrayList<Departamento>): ArrayList<Departamento> {

        //Encontrar Departamento
        var miDepartamento = Departamento()
        var depActualizado = miDepartamento.encontrarDepartamento(idDepartamento, listaDepartamentos)
        var posDepartamento = (miDepartamento.encontrarDepartamento(idDepartamento, listaDepartamentos).idDepartamento) - 1
        println(depActualizado.toString())

        //Actualizar Departamento
        do {
            println("1.Cambiar Nombre del Departamento")
            println("2.Cambiar Fecha de Inauguración")
            println("3.Cambiar ¿Es un Departamento Principal?")
            println("4.Cambiar Ganancias del Departamento")
            println("5.Salir")

            val respActualizar = readln().toInt()
            when(respActualizar) {
                1 -> {
                    println("Nuevo Nombre del Departamento: ")
                    depActualizado.nombreDepartamento = readln()
                    listaDepartamentos[posDepartamento] = depActualizado
                }
                2 -> {
                    println("Nueva Fecha de Inauguración del Departamento (AAAA-MM-DD): ")
                    depActualizado.fechaInauguracion = LocalDate.parse(readln())
                    listaDepartamentos[posDepartamento] = depActualizado
                }
                3 -> {
                    println("¿Es un Departamento Principal? (Y/N): ")
                    depActualizado.esPrincipal = if(readln().equals("Y")) true else false
                    listaDepartamentos[posDepartamento] = depActualizado
                }
                4 -> {
                    println("Ganancias del Departamento: ")
                    depActualizado.ganancias = readln().toFloat()
                    listaDepartamentos[posDepartamento] = depActualizado
                }
            }
        }while (respActualizar!=5);
        return listaDepartamentos
    }

    //DELETE
    fun borrarDepartamento
                (idDepartamento:Int, listaDepartamentos: ArrayList<Departamento>): ArrayList<Departamento> {
        var miDepartamento = Departamento()
        var posDepartamento = (miDepartamento.encontrarDepartamento(idDepartamento, listaDepartamentos).idDepartamento) - 1
        listaDepartamentos.removeAt(posDepartamento)
        return listaDepartamentos
    }

    //TOSTRING
    override fun toString(): String {
        return "----------------DEPARTAMENTO----------------\n" +
                "Id: $idDepartamento\n"+
                "Nombre: $nombreDepartamento\n"+
                "Fecha De Inauguración: $fechaInauguracion\n" +
                "Principal: $esPrincipal\n"+
                "Ganancias: $ganancias \n"+
                "-------------------------------------------\n"
    }

}