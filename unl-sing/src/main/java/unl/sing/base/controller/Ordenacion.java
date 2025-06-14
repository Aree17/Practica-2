package unl.sing.base.controller;

import java.io.BufferedReader;
import java.io.FileReader;

class Ordenacion {
    int contador = 0;
    int cont = 0;

    public void cargarArchivo(int[] arreglo) {
        try {
            BufferedReader linecount = new BufferedReader(new FileReader("data.txt"));
            String linea;
            int i = 0;
            while ((linea = linecount.readLine()) != null && i < arreglo.length) {
                arreglo[i] = Integer.parseInt(linea.trim());
                i++;
            }
            linecount.close();
            System.out.println("Cargado correctamente");
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el archivo: " + e.getMessage());
        }
    }

    // Partition function
    public int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                contador++;
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swap function
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // The QuickSort function
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // shellSort ********************************
    public int sort(int arr[]) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
                cont++;
            }
        }
        return 0;
    }

    public void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        long inicioQuick = System.nanoTime();
        Ordenacion qSort = new Ordenacion();
        int[] arr = new int[20000];
        qSort.cargarArchivo(arr);
        int n = arr.length;
        qSort.quickSort(arr, 0, n - 1);
        qSort.printArray(arr);
        long finQuick = System.nanoTime();

        long inicioShell = System.nanoTime();
        Ordenacion ob = new Ordenacion();
        int arre[] = new int[200000];
        ob.cargarArchivo(arre);
        ob.sort(arre);
        System.out.println("Relizo " + ob.cont + " intercambios");
        long finShell = System.nanoTime();
        System.out.println("Array after sorting");
        ob.printArray(arre);

        System.out.println("Relizo " + qSort.contador + " intercambios");
        System.out.println("Tiempo usando QuickSort: " + (finQuick - inicioQuick) + " ns");
        System.out.println("Relizo " + ob.cont + " intercambios");
        System.out.println("Tiempo usando ShellSort: " + (finShell - inicioShell) + " ns");

    }
}