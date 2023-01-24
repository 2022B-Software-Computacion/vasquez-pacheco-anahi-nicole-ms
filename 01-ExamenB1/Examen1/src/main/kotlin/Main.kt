fun main(args: Array<String>) {
    var documento = AdministradorInformacion()

    do {
        println("------------EXAMEN B1 MOVILES (Empresa-Departamentos)------------")
        println("-------------------EMPRESA-------------------")
        println("1.Crear Empresa")
        println("2.Encontrar Empresa")
        println("3.Actualizar Empresa")
        println("4.Borrar Empresa")
        println("5.Departamento")
        println("6.Salir")

        val opcion = readln().toInt()
        var listaEmpresas = documento.leerInfo()

        when (opcion) {
            1 -> {//CREAR
                val miEmpresa = Empresa()
                miEmpresa.crearEmpresa()
                listaEmpresas.add(miEmpresa)
                documento.escribirInfo(listaEmpresas)
                println("¡Creación de Empresa Exitosa!\n")
            }
            2->{//READ
                println("ID Empresa: ")
                val idEmpresa = readln().toInt()
                val miEmpresa = Empresa().encontrarEmpresa(idEmpresa, listaEmpresas)
                println(miEmpresa.toString())
                println("¡Empresa Encontrada!\n")
            }
            3->{//UPDATE
                println("ID Empresa: ")
                val idEmpresa = readln().toInt()
                val misEmpresas = Empresa().actualizarEmpresa(idEmpresa, listaEmpresas)
                documento.escribirInfo(listaEmpresas)
                println("¡Empresa Actualizada!\n")
            }

            4->{//DELETE
                println("ID Empresa: ")
                val idEmpresa = readln().toInt()
                val misEmpresas = Empresa().borrarEmpresa(idEmpresa, listaEmpresas)
                documento.escribirInfo(listaEmpresas)
                println("¡Empresa Borrada!\n")
            }
            5->{//Modulo SERVICIOS
                println("ID Empresa: ")
                val idEmpresa = readln().toInt()
                val miEmpresa = Empresa().encontrarEmpresa(idEmpresa, listaEmpresas)
                val posEmpresa = (miEmpresa.idEmpresa)-1

                do{
                    println("-------------------DEPARTAMENTOS-------------------")
                    println("1.Crear Departamento")
                    println("2.Encontrar Departamento")
                    println("3.Actualizar Departamento")
                    println("4.Borrar Departamento")
                    println("5.Salir")

                    val opDepartamento = readln().toInt()

                    listaEmpresas = documento.leerInfo()

                    when(opDepartamento){
                        1->{ //CREAR
                            val miDepartamento = Departamento()
                            miDepartamento.crearDepartamento()
                            listaEmpresas[posEmpresa].listaDepartamentos.add(miDepartamento)
                            documento.escribirInfo(listaEmpresas)
                            println("¡Creación de Departamento Exitosa!\n")
                        }
                        2->{ //LEER
                            println("ID Departamento: ")
                            val idDepartamento = readln().toInt()
                            val miDepartamento = Departamento().encontrarDepartamento(idDepartamento, listaEmpresas[posEmpresa].listaDepartamentos)
                            println(miDepartamento.toString())
                            println("¡Departamento Encontrado!\n")
                        }
                        3->{//UPDATE
                            println("ID Departamento: ")
                            val idDepartamento = readln().toInt()
                            val miDepartamento = Departamento().actualizarDepartamento(idDepartamento, listaEmpresas[posEmpresa].listaDepartamentos)
                            documento.escribirInfo(listaEmpresas)
                            println("¡Departamento Actualizado!\n")
                        }
                        4->{//DELETE
                            println("ID Departamento: ")
                            val idDepartamento = readln().toInt()
                            val miDepartamento = Departamento().borrarDepartamento(idDepartamento, listaEmpresas[posEmpresa].listaDepartamentos)
                            documento.escribirInfo(listaEmpresas)
                            println("¡Departamento Borrado!\n")
                        }
                    }
                }while (opDepartamento!=5)
            }
        }
    } while (opcion != 6)
}