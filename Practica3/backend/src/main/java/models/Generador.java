package models;

public class Generador {
    private int idGenerador;
    private String nombre;
    private String codigo;
    private double costo;
    private double consumoPorHora;
    private int potencia;
    private String uso;

    public Generador() {
    }

    public Generador(int idGenerador, String nombre, String codigo, double costo, double consumoPorHora, int potencia,
            String uso) {
        this.idGenerador = idGenerador;
        this.nombre = nombre;
        this.costo = costo;
        this.codigo = codigo;
        this.consumoPorHora = consumoPorHora;
        this.potencia = potencia;
        this.uso = uso;
    }

    public int getIdGenerador() {
        return idGenerador;
    }

    public void setIdGenerador(int idGenerador) {
        this.idGenerador = idGenerador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}