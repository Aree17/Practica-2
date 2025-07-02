package unl.sing.base.controller.dataStruct.graph;

import unl.sing.base.controller.dataStruct.list.LinkedList;

public abstract class Graph {
    public abstract Integer nro_vertex();
    public abstract Integer nro_edge();
    public abstract Adjacency exist_edge(Integer o, Integer d); //origen y destino
    public abstract Float wight_edge(Integer o, Integer d);
    public abstract void insert(Integer o, Integer d);
    public abstract void insert(Integer o, Integer d, Float weight);
   
    public abstract LinkedList<Adjacency> adjacencies(Integer o);


    @Override 
    public String toString(){
        StringBuilder sb= new StringBuilder();
        for(int i=1; i<= nro_vertex(); i++){
            sb.append("Vertex =").append(String.valueOf(i)).append("\n");
            LinkedList<Adjacency> list = adjacencies(i);
            if(!list.isEmpty()){
                Adjacency [] matrix = list.toArray();
                for(Adjacency ad: matrix){
                    sb.append("\tAdjacency = ").append("\n").append("Vertex = ").append(String.valueOf(ad.getDestiny()));
                    if(!ad.getWeight().isNaN()){
                        sb.append(" weight = "+ ad.getWeight().toString()).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }

}