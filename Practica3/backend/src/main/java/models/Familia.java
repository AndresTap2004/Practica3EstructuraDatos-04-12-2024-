
package models;

public class Familia {
    private int idFamilia;
    private String nombre;

    public Familia() {}

    public Familia(int idFamilia, String nombre) {
        this.idFamilia = idFamilia;
        this.nombre = nombre;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
