package controller.Sort_Search;

import java.util.Arrays;
import java.util.Random;

public class Search {
    public static int busquedaBinaria(int[] arr, int x) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (arr[m] == x)
                return m;
            if (arr[m] < x)
                l = m + 1;
            else
                r = m - 1;
        }
        return -1;
    }

    // Busqueda linealBinaria
    public static int busquedaLinealBinaria(int[] arr, int x) {
        int n = arr.length;
        int i = 0;
        while (i < n && arr[i] <= x) {
            if (arr[i] == x)
                return i;
            i = i + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] tamanios = { 10000, 20000, 25000 };
        System.out.printf("%-15s%-20s%-25s%n", "Tamaño", "Búsqueda Binaria (ms)", "Búsqueda Lineal Binaria (ms)");
        System.out.println("-----------------------------------------------------------");

        for (int tam : tamanios) {
            int[] array = arregloAleatorio(tam);

            Arrays.sort(array);

            int valorABuscar = array[new Random().nextInt(tam)];

            double tiempoBusquedaBinaria = tiempoEjecucion(() -> Search.busquedaBinaria(array, valorABuscar));
            double tiempoBusquedaLinealBinaria = tiempoEjecucion(
                    () -> Search.busquedaLinealBinaria(array, valorABuscar));

            System.out.printf("%-15d%-20.5f%-25.5f%n", tam, tiempoBusquedaBinaria, tiempoBusquedaLinealBinaria);
        }
    }

    private static int[] arregloAleatorio(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100000);
        }
        return array;
    }

    private static double tiempoEjecucion(Runnable algoritmoBusqueda) {
        long startTime = System.nanoTime();
        algoritmoBusqueda.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

}
