package unl.sing.base.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import unl.sing.base.controller.dataStruct.list.LinkedList;

public class Practica {
    private LinkedList<Integer> listaDatos = new LinkedList<>();
    private LinkedList<Integer> listaRepetidos = new LinkedList<>();
    protected static String base_path="data"+File.separatorChar;
    private int[] numerosRepetidos;
    private int[] numeros;
    private int contadorRepetidos;
    private int contador;

    public void cargarArchivo(String archivo){
        try {
            BufferedReader linecount = new BufferedReader(new FileReader(archivo));
            int totalNumeros = 0;
            while (linecount.readLine() != null) {
                totalNumeros++;
            }
            linecount.close();
    
            this.numeros = new int[totalNumeros]; 
            this.numerosRepetidos = new int[totalNumeros];
        } catch (Exception e) {
            System.out.println("Error al abrir el archivo: " + e);
        }
    }


    public void contador_Array(String archivo){
        try {
            this.contador = 0;
            this.contadorRepetidos = 0;
            cargarArchivo(archivo);
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea;
            int i = 0, m = 0;
            while ((linea = lector.readLine()) != null) {
                int numero = Integer.parseInt(linea.trim());
                if (esRepetidoArray(this.numeros, numero)) {
                    if (!esRepetidoArray(this.numerosRepetidos, numero)) { 
                        this.numerosRepetidos[m++] = numero;
                        contadorRepetidos++;
                    }
                } else {
                    this.numeros[i++] = numero;
                    contador++;
                }
            }
            lector.close();

            System.out.println("Números sin repetir en arreglos:");
            printNumerosArray();
            System.out.println("Números repetidos en arreglos:");
            printRepetidosArray();    

            System.out.println("Se registraron " + contadorRepetidos + " Numeros repetidos en arreglos\n");
            System.out.println("Se registraron " + contador + " Numeros sin repetir en arreglos\n");
        } catch (Exception e) {
            System.out.println("Error al almacenar datos: " + e);
        }
    }

    public boolean esRepetidoArray(int[] numeros, int lineaActual) {
        for (int i = 0; i < contador; i++) {
            if (numeros[i] == lineaActual) {
                return true;
            }
        }
        return false;
    }

    public void printNumerosArray() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contador; i++) {
            sb.append(numeros[i]);
            if (i < contador - 1) {
                sb.append(" - ");
            }
        }
        System.out.println(sb.toString());
    }


    public void printRepetidosArray() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contadorRepetidos; i++) {
            sb.append(numerosRepetidos[i]);
            if (i < contadorRepetidos - 1) {
                sb.append(" - ");
            }
        }
        System.out.println(sb.toString());
    }

    public void contador_Lista(String archivo){
        try {
            cargarArchivo(archivo);
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = lector.readLine()) != null) {
                int numero = Integer.parseInt(linea.trim());
                if (listaDatos.contains(numero)) {
                    if (!listaRepetidos.contains(numero)) {
                        listaRepetidos.add(numero);
                        contadorRepetidos++;
                    }
                } else {
                    listaDatos.add(numero);
                    contador++;
                }
            }
            lector.close();
            System.out.println("Números sin repetir en Lista enlazada:");
            System.out.println(listaDatos.print());
            System.out.println("Números repetidos en Lista enlazada:");
            System.out.println(listaRepetidos.print());
            System.out.println("Se registraron " + contadorRepetidos + " Numeros repetidos en la lista\n");
            System.out.println("Se registraron " + contador + " Numeros sin repetir en la lista\n");
        } catch (Exception e) {
            System.out.println("Error al almacenar datos: " + e);
        }
    }

    public static void main(String[] args) {
        Practica p_array = new Practica();
        long inicioArray = System.nanoTime();
        p_array.contador_Array("data.txt");
        long finArray = System.nanoTime();
        
        Practica p_listas = new Practica();
        long inicioLista = System.nanoTime();
        p_listas.contador_Lista("data.txt");
        long finLista = System.nanoTime();

        System.out.println("Tiempo usando Arreglos: " + (finArray - inicioArray) + " ns");
        System.out.println("Tiempo usando Listas: " + (finLista - inicioLista) + " ns");
        
    }

}
