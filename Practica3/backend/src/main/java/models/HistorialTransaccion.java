package models;

import java.util.Date;

public class HistorialTransaccion {
    private int historialTransaccion; 
    private int idFamilia;
    private int idGenerador;

    public HistorialTransaccion() {}

    public HistorialTransaccion(int historialTransaccion, int idFamilia, int idGenerador) {
        this.historialTransaccion = historialTransaccion;
        this.idFamilia = idFamilia;
        this.idGenerador = idGenerador;
    }

    public int getHistorialTransaccion() {
        return historialTransaccion;
    }   

    public void setHistorialTransaccion(int historialTransaccion) {
        this.historialTransaccion = historialTransaccion;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public int getIdGenerador() {
        return idGenerador;
    }

    public void setIdGenerador(int idGenerador) {
        this.idGenerador = idGenerador;
    }

}

