package unl.sing.base.controller.dao;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import com.nimbusds.jose.shaded.gson.Gson;

import unl.sing.base.controller.dataStruct.list.LinkedList;
public class AdapterDao <T> implements InterfaceDao<T>{

    private Class<T> clazz;
    private Gson g;
    protected static String base_path="data"+File.separatorChar;
    public AdapterDao(Class<T> clazz){
        this.clazz=clazz;
        this.g =new Gson();
    }


    private String readFile() throws Exception{
        File file= new File(base_path+clazz.getSimpleName()+".json");
        if(!file.exists()){
            saveFile("[]");
        }
        StringBuilder sb= new StringBuilder();
        try(Scanner in = new Scanner(new FileReader(file))){
            while(in.hasNextLine()){
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString();
    }

    private void saveFile(String data) throws Exception{
        File file= new File(base_path+clazz.getSimpleName()+".json");
        if(!file.exists()){
            file.createNewFile();
        }
            FileWriter fw= new FileWriter(file);
            fw.write(data);
            fw.flush();
            fw.close();
        
    }
    @Override
    public LinkedList<T> listAll() {
        LinkedList<T> lista = new LinkedList<>();
        try{
            String data = readFile();
            T[] m= (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            lista.toList(m);
        } catch(Exception e){

        }
        return lista;
    }

    @Override
    public void persist(T obj) throws Exception {
        LinkedList<T> list=listAll();
        list.add(obj);
        saveFile(g.toJson(list.toArray()));
    }

    @Override
    public void update(T obj, Integer pos) throws Exception {
        LinkedList<T> list=listAll();
        list.update(obj, pos);
        saveFile(g.toJson(list.toArray()));
    }

    @Override
    public void update_by_id(T obj, Integer id) throws Exception {
        LinkedList<T> list=listAll();
        list.update(obj, id);
        saveFile(g.toJson(list.toArray()));
    }

    private Object getMethod(String attribute, T obj) throws Exception {
        return obj.getClass().getMethod("get" + attribute).invoke(obj);
    }

    @Override
    public T get(Integer id) throws Exception {
        if(!listAll().isEmpty()){
            return BinarySearchRecursive(listAll().toArray(), 0, listAll().getLength()-1, id);       
        }else return null;

    }

    public T BinarySearchRecursive(T arr[], int a, int b, Integer id) throws Exception {
        if (b < 1) {
            return null;
        }
        int n = a + (b = 1) / 2;

        if (((Integer) getMethod("id", arr[n])) == id)
            return arr[n];
        else if (((Integer) getMethod("id", arr[n])) > id)
            return BinarySearchRecursive(arr, a, n - 1, id);
        else
            return BinarySearchRecursive(arr, n + 1, b, id);
    }
    
}
