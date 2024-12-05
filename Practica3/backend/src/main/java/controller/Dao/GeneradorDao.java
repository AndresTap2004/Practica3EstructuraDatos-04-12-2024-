package controller.Dao;

import models.Generador;

import java.util.Arrays;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class GeneradorDao extends AdapterDao<Generador> {
    private Generador generador;
    private LinkedList<Generador> listAll;

    public GeneradorDao() {
        super(Generador.class);
        this.listAll = new LinkedList<>();
    }

    public Generador getGenerador() {
        if (generador == null) {
            generador = new Generador();
        }
        return this.generador;
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }

    public LinkedList<Generador> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        generador.setIdGenerador(id);
        this.persist(this.generador);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getGenerador(), getGenerador().getIdGenerador() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Generador> list = getlistAll();
        Generador generador = get(id);
        if (generador != null) {
            list.remove(generador);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Generador con id " + id + " no encontrado.");
            return false;
        }
    }

    private boolean compare(Generador a, Generador b, Integer type, String field) {
        int comparison;
        switch (field) {
            case "idGenerador":
                comparison = Integer.compare(a.getIdGenerador(), b.getIdGenerador());
                break;
            case "nombre":
                comparison = a.getNombre().compareTo(b.getNombre());
                break;
            case "codigo":
                comparison = a.getCodigo().compareTo(b.getCodigo());
                break;
            case "costo":
                comparison = Double.compare(a.getCosto(), b.getCosto());
                break;
            case "consumo":
                comparison = Double.compare(a.getConsumoPorHora(), b.getConsumoPorHora());
                break;
            case "potencia":
                comparison = Double.compare(a.getPotencia(), b.getPotencia());
                break;
            case "uso":
                comparison = a.getUso().compareTo(b.getUso());
                break;
            default:
                return false;
        }
        return type == 1 ? comparison < 0 : comparison > 0;
    }

    // Metodo de ordenacion Quicksort
    public LinkedList<Generador> quickSort(LinkedList<Generador> lista, Integer type, String field) throws Exception {
        Generador[] array = lista.toArray();
        quicksort(array, 0, array.length - 1, type, field);
        lista.toList(array);
        return lista;
    }

    private void quicksort(Generador[] array, int low, int high, Integer type, String field) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, type, field);
            quicksort(array, low, pivotIndex - 1, type, field);
            quicksort(array, pivotIndex + 1, high, type, field);
        }
    }

    private int partition(Generador[] array, int low, int high, Integer type, String field) {
        Generador pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(array[j], pivot, type, field)) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(Generador[] array, int i, int j) {
        Generador temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Metodo de ordenacion MergeSort
    public LinkedList<Generador> mergeSort(Integer type, String field) throws Exception {
        LinkedList<Generador> lista = listAll();
        Generador[] array = lista.toArray();
        mergeSort(array, 0, array.length - 1, type, field);
        lista.toList(array);
        return lista;
    }

    private void mergeSort(Generador[] array, int low, int high, Integer type, String field) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(array, low, mid, type, field);
            mergeSort(array, mid + 1, high, type, field);
            merge(array, low, mid, high, type, field);
        }
    }

    private void merge(Generador[] array, int low, int mid, int high, Integer type, String field) {
        Generador[] left = Arrays.copyOfRange(array, low, mid + 1);
        Generador[] right = Arrays.copyOfRange(array, mid + 1, high + 1);

        int i = 0, j = 0, k = low;
        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j], type, field)) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    // Metodo de ordenacion ShellSort
    public LinkedList<Generador> shellSort(Integer type, String field) throws Exception {
        LinkedList<Generador> lista = listAll();
        Generador[] array = lista.toArray();
        shellSort(array, type, field);
        lista.toList(array);
        return lista;
    }

    private void shellSort(Generador[] array, Integer type, String field) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Generador temp = array[i];
                int j;
                for (j = i; j >= gap && compare(array[j - gap], temp, type, field); j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    // Metodo de busqueda lineal binaria
    public LinkedList<Generador> linealBinaria(String field, String value) throws Exception {
        LinkedList<Generador> lista = listAll();
        if (lista == null) {
            throw new Exception("La lista de generadores es nula");
        }

        Generador[] array = lista.toArray();
        LinkedList<Generador> result = new LinkedList<>();

        for (Generador generador : array) {
            switch (field) {
                case "idGenerador":
                    if (Integer.toString(generador.getIdGenerador()).equals(value)) {
                        result.add(generador);
                    }
                    break;
                case "nombre":
                    if (generador.getNombre().equals(value)) {
                        result.add(generador);
                    }
                    break;
                case "costo":
                    if (Double.toString(generador.getCosto()).equals(value)) {
                        result.add(generador);
                    }
                    break;
                case "consumo":
                    if (Double.toString(generador.getConsumoPorHora()).equals(value)) {
                        result.add(generador);
                    }
                    break;
                case "potencia":
                    if (Double.toString(generador.getPotencia()).equals(value)) {
                        result.add(generador);
                    }
                    break;
                case "uso":
                    if (generador.getUso().equals(value)) {
                        result.add(generador);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo de búsqueda no válido: " + field);
            }
        }
        return result;
    }

    // Metodo de busqueda binaria
    public Generador binaria(String field, String value) throws Exception {
        LinkedList<Generador> lista = listAll();
        if (lista == null) {
            throw new Exception("La lista de generadores es nula");
        }

        Generador[] array = lista.toArray();
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            switch (field) {
                case "codigo":
                    if (array[mid].getCodigo().equals(value)) {
                        return array[mid];
                    } else if (array[mid].getCodigo().compareTo(value) < 0) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo de búsqueda no válido: " + field);
            }
        }
        return null;
    }

}