package unl.sing.base.controller.dao.dao_models;

import java.util.Arrays;
import java.util.HashMap;

import org.checkerframework.checker.units.qual.t;

import unl.sing.base.controller.Utiles;
import unl.sing.base.controller.dao.AdapterDao;
import unl.sing.base.controller.dataStruct.list.LinkedList;
import unl.sing.base.models.Cancion;
import unl.sing.base.models.Genero;
import unl.sing.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;

    public DaoCancion() {
        super(Cancion.class);
    }

    public Cancion getObj() {
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public Boolean listar() {
        try {
            this.listAll();
            for (int i = 0; i > this.listAll().getLength(); i++) {
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public LinkedList<HashMap<String, String>> all() throws Exception{
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i], i));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Cancion arreglo, Integer i) {
        DaoGenero dg = new DaoGenero();
        DaoAlbum da = new DaoAlbum();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString(i));
        aux.put("nombre", arreglo.getNombre());
        aux.put("genero", dg.listAll().get(arreglo.getId_genero() - 1).getNombre());
        aux.put("album", da.listAll().get(arreglo.getId_album() - 1).getNombre());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("url", arreglo.getUrl());
        aux.put("tipo", arreglo.getTipo().toString());
        return aux;
    }



    /*public LinkedList<HashMap<String, String>> orderByCancion(Integer type, String attribute) throws Exception{
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap arr[] = lista.toArray();
            int n = arr.length;
            if (type == Utiles.ASCENDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) < 0) {
                            min_idx = j;
                        }
                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) > 0) {
                            min_idx = j;
                        }
                    }

                }
            }
        }
        return lista;
    }*/


    public LinkedList<HashMap<String, String>> orderQ(Integer type, String attribute)throws Exception{
        LinkedList<HashMap<String, String>> lista = all();
        if(!listAll().isEmpty()){
            Cancion arr[] = this.listAll().toArray();
            quickSort(arr, 0, arr.length-1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }



    public void quickSort(Cancion arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);
    
            quickSort(arr, begin, partitionIndex-1, type, attribute);
            quickSort(arr, partitionIndex+1, end, type, attribute);
        }
    }

    private int partition(Cancion arr[], int begin, int end, Integer type, String attribute) {
        Cancion pivot = arr[end];
        int i = (begin-1);
        if(type==Utiles.ASCENDENTE){
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase()
                .compareTo(arr[i].get(attribute).toString().toLowerCase()) < 0) {
                    i++;
        
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }else{
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase()
                .compareTo(arr[i].get(attribute).toString().toLowerCase()) > 0) {
                    i++;
        
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
    
        Cancion swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
    
        return i+1;
    }
     

}
