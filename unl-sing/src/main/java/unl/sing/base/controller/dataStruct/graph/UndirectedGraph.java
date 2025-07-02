package unl.sing.base.controller.dataStruct.graph;

public class UndirectedGraph extends DirectedGraph {

    public UndirectedGraph(Integer nro_vertex) {
        super(nro_vertex);
    }

    @Override
    public void insert(Integer o, Integer d, Float weight) {
        if (o.intValue() <= nro_vertex().intValue() && d.intValue() <= nro_vertex().intValue()){
            if(exist_edge(o, d) == null){
                //nro_edge++;
                setNro_edge(nro_edge()+1);
                //origen
                Adjacency aux = new Adjacency();
                aux.setWeight(weight);
                aux.setDestiny(d);
                getList_adjacencies()[o].add(aux);
                //destiny
                Adjacency auxD = new Adjacency();
                auxD.setWeight(weight);
                auxD.setDestiny(o);
                getList_adjacencies()[d].add(auxD);

            }
        }else{
            throw new ArrayIndexOutOfBoundsException("Vertex origin o destiny index out ");
        }
    }

}
