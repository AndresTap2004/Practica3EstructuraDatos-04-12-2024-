package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.Generador;
import controller.Dao.GeneradorDao;

public class GeneradorServicies {
    private GeneradorDao obj;

    public GeneradorServicies() {
        obj = new GeneradorDao();
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

    public LinkedList<Generador> listAll() {
        return obj.getlistAll();
    }

    public Generador getGenerador() {
        return obj.getGenerador();
    }

    public void setGenerador(Generador generador) {
        obj.setGenerador(generador);
    }

    public Generador get(Integer id) throws Exception {
        return obj.get(id);
    }

    public LinkedList<Generador> quickSort(LinkedList<Generador> lista, Integer type_order, String atributo)
            throws Exception {
        return obj.quickSort(lista, type_order, atributo);
    }

    public LinkedList<Generador> mergeSort(Integer type_order, String atributo) throws Exception {
        return obj.mergeSort(type_order, atributo);
    }

    public LinkedList<Generador> shellSort(Integer type_order, String atributo) throws Exception {
        return obj.shellSort(type_order, atributo);
    }

    public LinkedList<Generador> linealBinaria(String atributo, String value) throws Exception {
        return obj.linealBinaria(atributo, value);
    }

    public Generador binaria(String atributo, String value) throws Exception {
        return obj.binaria(atributo, value);
    }
}
