package com.cafegross;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionPlantaCafe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlantaCafe planta = new PlantaCafe("Café Gross - Acopio Villa Mercedes");

        // Carga inicial de datos de prueba predeterminados (igual a los del TP2)
        planta.agregarTolva(new TolvaEstandar(101, "Colombia", 1500.0, 90));
        planta.agregarTolva(new TolvaHermetica(102, "Etiopia", 800.0, 1.0, 11.5));
        planta.agregarTolva(new TolvaEstandar(103, "Bolivia", 1200.0, 60));

        // Inicializamos con algunas cargas válidas
        try {
            planta.cargarTolva(101, 60.0);
            planta.cargarTolva(101, 55.5);
            planta.cargarTolva(102, 35.0);
        } catch (TolvaExcepcion ignored) {}

        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\n=======================================================");
            System.out.println("   SISTEMA DE GESTIÓN DE ACOPIO - CAFÉ GROSS");
            System.out.println("=======================================================");
            System.out.println("1. Registrar nueva Tolva");
            System.out.println("2. Cargar café en tolva (Ingreso de saco)");
            System.out.println("3. Descargar lote de café");
            System.out.println("4. Listar tolvas y estado en planta");
            System.out.println("5. Calcular stock total acumulado");
            System.out.println("6. Salir");
            System.out.println("=======================================================");
            System.out.print("Elija una opción (1-6): ");

            try {
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.print("\nIngrese número identificador de la tolva: ");
                        int numeroTolva = scanner.nextInt();
                        scanner.nextLine(); // Limpiar salto de línea

                        System.out.print("Ingrese origen del grano (Colombia / Etiopia / Bolivia): ");
                        String origen = scanner.nextLine();

                        System.out.print("Ingrese capacidad máxima de almacenamiento (kg): ");
                        double pesoMax = scanner.nextDouble();

                        System.out.print("Seleccione tipo de tolva (1: Estándar | 2: Hermética): ");
                        int tipo = scanner.nextInt();

                        if (tipo == 1) {
                            System.out.print("Ingrese tiempo máximo de almacenamiento (días): ");
                            int dias = scanner.nextInt();
                            planta.agregarTolva(new TolvaEstandar(numeroTolva, origen, pesoMax, dias));
                            System.out.println(">> Éxito: Tolva Estándar #" + numeroTolva + " registrada en planta.");
                        } else if (tipo == 2) {
                            System.out.print("Ingrese presión atmosférica inicial (bar, ej: 1.0): ");
                            double presion = scanner.nextDouble();
                            System.out.print("Ingrese nivel máximo de humedad (%): ");
                            double humedad = scanner.nextDouble();
                            planta.agregarTolva(new TolvaHermetica(numeroTolva, origen, pesoMax, presion, humedad));
                            System.out.println(">> Éxito: Tolva Hermética #" + numeroTolva + " registrada en planta.");
                        } else {
                            System.out.println(">> Error: Tipo de tolva no reconocido.");
                        }
                        break;

                    case 2:
                        System.out.print("\nIngrese número de tolva destino: ");
                        int tolvaCarga = scanner.nextInt();
                        System.out.print("Ingrese peso del saco a cargar (kg) [Admisible: 10 a 70 kg]: ");
                        double kilosCarga = scanner.nextDouble();

                        planta.cargarTolva(tolvaCarga, kilosCarga);
                        System.out.println(">> Éxito: Saco de " + kilosCarga + " kg ingresado correctamente en tolva #" + tolvaCarga + ".");
                        break;

                    case 3:
                        System.out.print("\nIngrese número de tolva a descargar: ");
                        int tolvaDesc = scanner.nextInt();
                        System.out.print("Ingrese kilos a retirar para tostado: ");
                        double kilosDesc = scanner.nextDouble();

                        if (planta.descargarTolva(tolvaDesc, kilosDesc)) {
                            System.out.println(">> Éxito: Se retiraron " + kilosDesc + " kg de la tolva #" + tolvaDesc + ".");
                        } else {
                            System.out.println(">> Error: Fondos insuficientes en tolva o cantidad inválida.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ESTADO ACTUAL DE LAS TOLVAS EN DEPÓSITO ---");
                        if (planta.hayTolvasDisponibles()) {
                            planta.mostrarEstadoTolvas();
                        } else {
                            System.out.println("No hay tolvas registradas.");
                        }
                        break;

                    case 5:
                        double total = planta.calcularStockTotal();
                        System.out.println("\n>> Stock total acumulado en planta: " + total + " kg de granos de café.");
                        break;

                    case 6:
                        System.out.println("\n>> Cerrando sesión de acopio. ¡Hasta pronto!");
                        ejecutando = false;
                        break;

                    default:
                        System.out.println(">> Opción inválida. Ingrese un valor del 1 al 6.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("\n>> ERROR DE ENTRADA: Ha ingresado un caracter no numérico. Por favor intente nuevamente.");
                scanner.nextLine(); // Limpiar el buffer para no quedar en bucle infinito
            } catch (TolvaExcepcion e) {
                System.out.println("\n>> EXCEPCIÓN DETECTADA: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
