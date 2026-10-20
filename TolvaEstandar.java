package com.cafegross;

public class TolvaEstandar extends TolvaCafe {
    private int tiempoAlmacenamientoMax; // en días

    public TolvaEstandar(int numeroTolva, String origen, double pesoMaximo, int tiempoAlmacenamientoMax) {
        super(numeroTolva, origen, pesoMaximo);
        this.tiempoAlmacenamientoMax = tiempoAlmacenamientoMax;
    }

    @Override
    public void ingresarCarga(double kilos) throws TolvaExcepcion {
        if (!this.estadoOperativo) {
            throw new TolvaExcepcion("Operación cancelada: La tolva #" + numeroTolva + " está fuera de servicio.");
        }
        if (kilos < 10.0 || kilos > 70.0) {
            throw new TolvaExcepcion("Carga rechazada: El peso individual del saco (" + kilos + " kg) debe estar entre 10 kg y 70 kg.");
        }
        if ((this.kilosActuales + kilos) > this.pesoMaximo) {
            double disponible = this.pesoMaximo - this.kilosActuales;
            throw new TolvaExcepcion("Alerta de sobrecarga estructural: La tolva #" + numeroTolva +
                    " solo admite " + disponible + " kg adicionales (Capacidad máxima: " + this.pesoMaximo + " kg).");
        }
        this.kilosActuales += kilos;
    }

    public int getTiempoAlmacenamientoMax() {
        return tiempoAlmacenamientoMax;
    }
}
