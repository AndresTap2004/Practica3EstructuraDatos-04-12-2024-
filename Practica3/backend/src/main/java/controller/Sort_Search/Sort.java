package controller.Sort_Search;

import java.util.Random;

public class Sort {

    public static void quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        int pivot = arr[middle];
        int i = low, j = high;
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(arr, low, j);
        if (high > i)
            quickSort(arr, i, high);
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }

    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] tamanios = { 10000, 20000, 25000 };
        System.out.printf("%-15s%-20s%-20s%-20s%n", "Tamaño", "QuickSort (ms)", "MergeSort (ms)", "ShellSort (ms)");
        System.out.println("--------------------------------------------------------------------------");

        for (int tam : tamanios) {
            int[] array = arregloAleatorio(tam);
            int[] arrayQuickSort = array.clone();
            int[] arrayMergeSort = array.clone();
            int[] arrayShellSort = array.clone();

            double quickSortTime = tiempoEjecucion(
                    () -> Sort.quickSort(arrayQuickSort, 0, arrayQuickSort.length - 1));
            double mergeSortTime = tiempoEjecucion(
                    () -> Sort.mergeSort(arrayMergeSort, 0, arrayMergeSort.length - 1));
            double shellSortTime = tiempoEjecucion(() -> Sort.shellSort(arrayShellSort));

            System.out.printf("%-15d%-20.5f%-20.5f%-20.5f%n", tam, quickSortTime, mergeSortTime, shellSortTime);
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

    private static double tiempoEjecucion(Runnable sortingAlgorithm) {
        long startTime = System.nanoTime();
        sortingAlgorithm.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000.0;
    }

}
