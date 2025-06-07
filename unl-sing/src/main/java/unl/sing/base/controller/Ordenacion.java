package unl.sing.base.controller;


import java.io.BufferedReader;
import java.io.FileReader;

class Ordenacion{
    int contador=0;
    int cont=0;


    public void cargarArchivo(int [] arreglo) {
        try {
            BufferedReader linecount = new BufferedReader(new FileReader("src/data.txt"));
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
        // Choose the pivot
        int pivot = arr[high];
        // Index of smaller element and indicates 
        // the right position of pivot found so far
        int i = low - 1;

        // Traverse arr[low..high] and move all smaller
        // elements to the left side. Elements from low to 
        // i are smaller after every iteration
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                contador++;
            }
        }
        
        // Move pivot after smaller elements and
        // return its position
        swap(arr, i + 1, high);  
        return i + 1;
    }

    // Swap function
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // The QuickSort function implementation
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            
            // pi is the partition return index of pivot
            int pi = partition(arr, low, high);

            // Recursion calls for smaller elements
            // and greater or equals elements
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }


    //shell******************************
    public void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    /* function to sort arr using shellSort */
    public int sort(int arr[])
    {
        int n = arr.length;

        // Start with a big gap, then reduce the gap
        for (int gap = n/2; gap > 0; gap /= 2)
        {
            // Do a gapped insertion sort for this gap size.
            // The first gap elements a[0..gap-1] are already
            // in gapped order keep adding one more element
            // until the entire array is gap sorted
            for (int i = gap; i < n; i += 1)
            {
                // add a[i] to the elements that have been gap
                // sorted save a[i] in temp and make a hole at
                // position i
                int temp = arr[i];

                // shift earlier gap-sorted elements up until
                // the correct location for a[i] is found
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap){
                    arr[j] = arr[j - gap];
                    cont++;
                }

                // put temp (the original a[i]) in its correct
                // location
                arr[j] = temp;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        long inicioQuick = System.nanoTime();
        Ordenacion qSort= new Ordenacion();
        int[] arr = new int[20000];
        qSort.cargarArchivo(arr);
        int n = arr.length;
        qSort.quickSort(arr, n, n);
        qSort.quickSort(arr, 0, n - 1);
        /*for (int val : arr) {
            System.out.print(val + " ");  
        }*/
        System.out.println("Relizo "+qSort.contador+" intercambios");
        long finQuick = System.nanoTime();

        long inicioShell = System.nanoTime();
        Ordenacion ob = new Ordenacion();
        int arre[] = new int[200000];
        ob.cargarArchivo(arre);
        ob.sort(arre);
        System.out.println("Relizo "+ob.cont+" intercambios");
        long finShell = System.nanoTime();
        //System.out.println("Array after sorting");
        //ob.printArray(arre);

        System.out.println("Tiempo usando QuickSort: " + (finQuick - inicioQuick) + " ns");
        System.out.println("Tiempo usando ShellSort: " + (finShell - inicioShell) + " ns");


    }
}