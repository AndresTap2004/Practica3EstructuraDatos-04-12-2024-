package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.Familia;
import models.Generador;
import controller.Dao.FamiliaDao;

public class FamiliaServicies {
    private FamiliaDao obj;

    public FamiliaServicies() {
        obj = new FamiliaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }

    public LinkedList listAll() {
        return obj.getlistAll();
    }

    public Familia getFamilia() {
        return obj.getFamilia();
    }

    public void setFamilia(Familia Familia) {
        obj.setFamilia(Familia);
    }

    public Familia get(Integer id) throws Exception {
        return obj.get(id);
    }

    public LinkedList<Familia> quickSort(LinkedList<Familia> lista, Integer type_order, String atributo)
            throws Exception {
        return obj.quickSort(lista, type_order, atributo);
    }

    public LinkedList<Familia> mergeSort(Integer type_order, String atributo) throws Exception {
        return obj.mergeSort(type_order, atributo);
    }

    public LinkedList<Familia> shellSort(Integer type_order, String atributo) throws Exception {
        return obj.shellSort(type_order, atributo);
    }

    public LinkedList<Familia> linealBinaria(String atributo, String value) throws Exception {
        return obj.linealBinaria(atributo, value);
    }

    public Familia binaria(String atributo, String value) throws Exception {
        return obj.binaria(atributo, value);
    }
}