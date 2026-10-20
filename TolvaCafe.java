package com.cafegross;

public abstract class TolvaCafe {
    protected int numeroTolva;
    protected String origen;
    protected double pesoMaximo;
    protected double kilosActuales;
    protected boolean estadoOperativo;

    public TolvaCafe(int numeroTolva, String origen, double pesoMaximo) {
        this.numeroTolva = numeroTolva;
        this.origen = origen;
        this.pesoMaximo = pesoMaximo;
        this.kilosActuales = 0.0;
        this.estadoOperativo = true;
    }

    // Método abstracto polimórfico
    public abstract void ingresarCarga(double kilos) throws TolvaExcepcion;

    public boolean descargarCarga(double kilos) {
        if (kilos > 0 && kilos <= this.kilosActuales) {
            this.kilosActuales -= kilos;
            return true;
        }
        return false;
    }

    public int getNumeroTolva() {
        return numeroTolva;
    }

    public String getOrigen() {
        return origen;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }

    public double getKilosActuales() {
        return kilosActuales;
    }

    public boolean isEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(boolean estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public String getEstado() {
        return estadoOperativo ? "Operativa" : "Fuera de Servicio";
    }

    public double getPorcentajeOcupacion() {
        if (pesoMaximo == 0) return 0.0;
        return (kilosActuales / pesoMaximo) * 100.0;
    }
}
