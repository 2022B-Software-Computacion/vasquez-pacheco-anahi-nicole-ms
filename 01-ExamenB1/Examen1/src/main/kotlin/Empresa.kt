import java.time.LocalDate

class Empresa{

    var idEmpresa: Int
    var nombreEmpresa:String
    var valorEmpresa: Double
    var fechaCreacion: LocalDate? = LocalDate.parse("2001-01-19")
    var esNacional: Boolean
    var listaDepartamentos: ArrayList<Departamento> = arrayListOf()

    //CONSTRUCTOR PRIMARIO
    init {
        this.idEmpresa = 0
        this.nombreEmpresa = null.toString()
        this.valorEmpresa = 0.0
        this.fechaCreacion
        this.esNacional = false
    }

    //----------CRUD EMPRESA----------

    //Create
    fun crearEmpresa() {
        println("Id Empresa: ")
        this.idEmpresa = readln().toInt()
        println("Nombre Empresa: ")
        this.nombreEmpresa = readln()
        println("Valor de la Empresa: ")
        this.valorEmpresa = readln().toDouble()
        println("Fecha de Creación (AAAA-MM-DD): ")
        this.fechaCreacion = LocalDate.parse(readln())
        println("¿Es Nacional? (Y/N): ")
        this.esNacional = if(readln().equals("Y")) true else false
    }

    //READ
    fun encontrarEmpresa
                (idEmpresa:Int, listaEmpresas: ArrayList<Empresa>):Empresa{
        var auxEmpresa = Empresa()
        listaEmpresas.forEach{emp:Empresa ->
            if(emp.idEmpresa.equals(idEmpresa)){
                auxEmpresa =  emp
            }
        }
        return auxEmpresa
    }

    //UPDATE
    fun actualizarEmpresa
                (idEmpresa:Int, listaEmpresas: ArrayList<Empresa>): ArrayList<Empresa> {

        //Encontrar Empresa
        var miEmpresa = Empresa()
        var empresaActualizada = miEmpresa.encontrarEmpresa(idEmpresa, listaEmpresas)
        var posEmpresa = (miEmpresa.encontrarEmpresa(idEmpresa, listaEmpresas).idEmpresa) - 1
        println(empresaActualizada.toString())

        //Actualizar Empresa
        do {
            println("1.Cambiar Nombre de la Empresa")
            println("2.Cambiar Valor de la Empresa")
            println("3.Cambiar Fecha de Creacion")
            println("4.Cambiar ¿La Empresa es Nacional?")
            println("5.Salir")

            val respActualizar = readln().toInt()
            when(respActualizar) {
                1 -> {
                    println("Nuevo Nombre de la Empresa: ")
                    empresaActualizada.nombreEmpresa = readln()
                    listaEmpresas[posEmpresa] = empresaActualizada
                }
                2 -> {
                    println("Nuevo Valor de la Empresa: ")
                    empresaActualizada.valorEmpresa = readln().toDouble()
                    listaEmpresas[posEmpresa] = empresaActualizada
                }
                3 -> {
                    println("Nueva Fecha de Creación de la Empresa (AAAA-MM-DD): ")
                    empresaActualizada.fechaCreacion = LocalDate.parse(readln())
                    listaEmpresas[posEmpresa] = empresaActualizada
                }
                4 -> {
                    println("¿Es Nacional? (Y/N): ")
                    empresaActualizada.esNacional = if(readln().equals("Y")) true else false
                    listaEmpresas[posEmpresa] = empresaActualizada
                }
            }
        }while (respActualizar!=5);
        return listaEmpresas
    }

    //DELETE
    fun borrarEmpresa
                (idEmpresa:Int, listaEmpresa: ArrayList<Empresa>): ArrayList<Empresa> {
        var miEmpresa = Empresa()
        var posEmpresa = (miEmpresa.encontrarEmpresa(idEmpresa, listaEmpresa).idEmpresa) - 1
        listaEmpresa.removeAt(posEmpresa)
        return listaEmpresa
    }

    //TO STRING
    override fun toString(): String {
        return "---------------------EMPRESA---------------------\n" +
                "Id: $idEmpresa\n" +
                "Nombre: $nombreEmpresa\n" +
                "Valorada en: $valorEmpresa\n" +
                "Fecha de Creación: $fechaCreacion\n" +
                "Nacional: $esNacional\n" +
                "$listaDepartamentos"+
                "------------------------------------------------\n"
    }
}