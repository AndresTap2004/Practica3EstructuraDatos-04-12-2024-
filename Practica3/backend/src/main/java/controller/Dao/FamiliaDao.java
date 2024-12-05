package controller.Dao;

import models.Familia;

import java.util.Arrays;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class FamiliaDao extends AdapterDao<Familia> {
    private Familia familia;
    private LinkedList<Familia> listAll;

    public FamiliaDao() {
        super(Familia.class);
        this.listAll = new LinkedList<>();
    }

    public Familia getFamilia() {
        if (familia == null) {
            familia = new Familia(0, "");
        }
        return this.familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public LinkedList<Familia> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        familia.setIdFamilia(id);
        this.persist(this.familia);
        this.listAll = getlistAll();
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getFamilia(), getFamilia().getIdFamilia() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<Familia> list = getlistAll();
        Familia familia = get(id);
        if (familia != null) {
            list.remove(familia);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Familia con id " + id + " no encontrada.");
            return false;
        }
    }

    private boolean compare(Familia a, Familia b, Integer type, String field) {
        int comparison;
        switch (field) {
            case "idFamilia":
                comparison = Integer.compare(a.getIdFamilia(), b.getIdFamilia());
                break;
            case "nombre":
                comparison = a.getNombre().compareTo(b.getNombre());
                break;
            default:
                return false;
        }
        return type == 1 ? comparison < 0 : comparison > 0;
    }

    // Metodo de ordenacion Quicksort
    public LinkedList<Familia> quickSort(LinkedList<Familia> lista, Integer type, String field) throws Exception {
        Familia[] array = lista.toArray();
        quicksort(array, 0, array.length - 1, type, field);
        lista.toList(array);
        return lista;
    }

    private void quicksort(Familia[] array, int low, int high, Integer type, String field) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, type, field);
            quicksort(array, low, pivotIndex - 1, type, field);
            quicksort(array, pivotIndex + 1, high, type, field);
        }
    }

    private int partition(Familia[] array, int low, int high, Integer type, String field) {
        Familia pivot = array[high];
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

    private void swap(Familia[] array, int i, int j) {
        Familia temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Metodo de ordenacion MergeSort
    public LinkedList<Familia> mergeSort(Integer type, String field) throws Exception {
        LinkedList<Familia> lista = listAll();
        Familia[] array = lista.toArray();
        mergeSort(array, 0, array.length - 1, type, field);
        lista.toList(array);
        return lista;
    }

    private void mergeSort(Familia[] array, int low, int high, Integer type, String field) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(array, low, mid, type, field);
            mergeSort(array, mid + 1, high, type, field);
            merge(array, low, mid, high, type, field);
        }
    }

    private void merge(Familia[] array, int low, int mid, int high, Integer type, String field) {
        Familia[] left = Arrays.copyOfRange(array, low, mid + 1);
        Familia[] right = Arrays.copyOfRange(array, mid + 1, high + 1);

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
    public LinkedList<Familia> shellSort(Integer type, String field) throws Exception {
        LinkedList<Familia> lista = listAll();
        Familia[] array = lista.toArray();
        shellSort(array, type, field);
        lista.toList(array);
        return lista;
    }

    private void shellSort(Familia[] array, Integer type, String field) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Familia temp = array[i];
                int j;
                for (j = i; j >= gap && compare(array[j - gap], temp, type, field); j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    // Metodo de busqueda lineal binaria
    public LinkedList<Familia> linealBinaria(String field, String value) throws Exception {
        LinkedList<Familia> lista = listAll();
        if (lista == null) {
            throw new Exception("La lista de Familia es nula");
        }

        Familia[] array = lista.toArray();
        LinkedList<Familia> result = new LinkedList<>();

        for (Familia Familia : array) {
            switch (field) {
                case "idFamilia":
                    if (Integer.toString(Familia.getIdFamilia()).equals(value)) {
                        result.add(Familia);
                    }
                    break;
                case "nombre":
                    if (Familia.getNombre().equals(value)) {
                        result.add(Familia);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo de búsqueda no válido: " + field);
            }
        }
        return result;
    }

    // Metodo de busqueda binaria
    public Familia binaria(String field, String value) throws Exception {
        LinkedList<Familia> lista = listAll();
        if (lista == null) {
            throw new Exception("La lista de Familiaes es nula");
        }

        Familia[] array = lista.toArray();
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            switch (field) {
                case "idFamilia":
                    if (Integer.toString(array[mid].getIdFamilia()).equals(value)) {
                        return array[mid];
                    } else if (Integer.parseInt(value) > array[mid].getIdFamilia()) {
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
