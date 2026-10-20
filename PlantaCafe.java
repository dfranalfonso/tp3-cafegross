package com.cafegross;

import java.util.ArrayList;
import java.util.List;

public class PlantaCafe {
    private String nombre;
    private List<TolvaCafe> tolvas;

    public PlantaCafe(String nombre) {
        this.nombre = nombre;
        this.tolvas = new ArrayList<>();
    }

    public void agregarTolva(TolvaCafe tolva) {
        this.tolvas.add(tolva);
    }

    public TolvaCafe buscarTolva(int numeroTolva) {
        for (TolvaCafe t : tolvas) {
            if (t.getNumeroTolva() == numeroTolva) {
                return t;
            }
        }
        return null;
    }

    public void cargarTolva(int numeroTolva, double kilos) throws TolvaExcepcion {
        TolvaCafe tolva = buscarTolva(numeroTolva);
        if (tolva == null) {
            throw new TolvaExcepcion("Tolva #" + numeroTolva + " no encontrada en los registros de planta.");
        }
        tolva.ingresarCarga(kilos);
    }

    public boolean descargarTolva(int numeroTolva, double kilos) throws TolvaExcepcion {
        TolvaCafe tolva = buscarTolva(numeroTolva);
        if (tolva == null) {
            throw new TolvaExcepcion("Tolva #" + numeroTolva + " no encontrada en los registros de planta.");
        }
        return tolva.descargarCarga(kilos);
    }

    // Polimorfismo al acumular el stock de todas las tolvas
    public double calcularStockTotal() {
        double stockTotal = 0.0;
        for (TolvaCafe t : tolvas) {
            stockTotal += t.getKilosActuales();
        }
        return stockTotal;
    }

    public List<TolvaCafe> getTolvas() {
        return tolvas;
    }

    public boolean hayTolvasDisponibles() {
        return !tolvas.isEmpty();
    }

    public void mostrarEstadoTolvas() {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-12s %-12s %-12s %-12s %-10s %-12s%n",
                "Tolva N°", "Origen", "Tipo", "Stock (kg)", "Cap. Máx", "Ocupación", "Estado");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (TolvaCafe t : tolvas) {
            String tipo = (t instanceof TolvaHermetica) ? "HERMETICA" : "ESTANDAR";
            System.out.printf("%-10d %-12s %-12s %-12.2f %-12.2f %-9.2f%% %-12s%n",
                    t.getNumeroTolva(),
                    t.getOrigen(),
                    tipo,
                    t.getKilosActuales(),
                    t.getPesoMaximo(),
                    t.getPorcentajeOcupacion(),
                    t.getEstado());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
