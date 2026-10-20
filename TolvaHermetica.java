package com.cafegross;

public class TolvaHermetica extends TolvaCafe {
    private double presionAtmosferica; // en bar
    private double nivelHumedadMax;    // en porcentaje

    public TolvaHermetica(int numeroTolva, String origen, double pesoMaximo, double presionAtmosferica, double nivelHumedadMax) {
        super(numeroTolva, origen, pesoMaximo);
        this.presionAtmosferica = presionAtmosferica;
        this.nivelHumedadMax = nivelHumedadMax;
    }

    @Override
    public void ingresarCarga(double kilos) throws TolvaExcepcion {
        if (!this.estadoOperativo) {
            throw new TolvaExcepcion("Operación cancelada: La tolva hermética #" + numeroTolva + " está fuera de servicio.");
        }
        if (presionAtmosferica < 0.8 || presionAtmosferica > 1.2) {
            throw new TolvaExcepcion("Alerta ambiental: Presión de atmósfera modificada no apta (" + presionAtmosferica + " bar). Verifique sellado hermético.");
        }
        if (kilos < 10.0 || kilos > 70.0) {
            throw new TolvaExcepcion("Carga rechazada: El saco de café especial (" + kilos + " kg) está fuera del rango permitido (10 a 70 kg).");
        }
        if ((this.kilosActuales + kilos) > this.pesoMaximo) {
            double disponible = this.pesoMaximo - this.kilosActuales;
            throw new TolvaExcepcion("Alerta de sobrecarga en tolva hermética #" + numeroTolva +
                    ": Excede la capacidad máxima disponible de " + disponible + " kg.");
        }
        this.kilosActuales += kilos;
    }

    public double getPresionAtmosferica() {
        return presionAtmosferica;
    }

    public double getNivelHumedadMax() {
        return nivelHumedadMax;
    }
}
