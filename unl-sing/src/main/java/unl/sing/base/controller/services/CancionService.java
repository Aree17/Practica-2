package unl.sing.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;
import unl.sing.base.controller.dao.dao_models.DaoAlbum;
import unl.sing.base.controller.dao.dao_models.DaoCancion;
import unl.sing.base.controller.dao.dao_models.DaoGenero;
import unl.sing.base.models.Album;
import unl.sing.base.models.Cancion;
import unl.sing.base.models.Genero;
import unl.sing.base.models.TipoArchivoEnum;


@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class CancionService {
    private DaoCancion db;
    public CancionService(){
        db = new DaoCancion();
    }

    public void createCancion(@NotEmpty String nombre, Integer id_genero, Integer duracion, @NotEmpty String url, @NotEmpty String tipo, Integer id_album) throws Exception {
        if(nombre.trim().length() > 0 && url.toString().length() > 0 && tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_album > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_album);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if(!db.save())
                throw new  Exception("No se pudo guardar los datos de la Cancion");
        }
    }

    public void updateCancion(@NotEmpty Integer id, @NotEmpty String nombre, Integer id_genero, Integer duracion, @NotEmpty String url, @NotEmpty String tipo, Integer id_album) throws Exception{
        if(nombre.trim().length() > 0 && url.trim().length() > 0 && tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_album > 0)
             db.setObj(db.listAll().get(id - 1));
             db.getObj().setNombre(nombre);
             db.getObj().setDuracion(duracion);
             db.getObj().setId_album(id_album);
             db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
             db.getObj().setUrl(url);
             db.getObj().setId_genero(id_genero);
             if(!db.update(id - 1))
                 throw new  Exception("No se pudo guardar los datos de Cancion");
     }

    public List<HashMap> listAlbumCombo(){
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da= new DaoAlbum();
        if(!db.listAll().isEmpty()) {
            Album [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listGeneroCombo(){
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da= new DaoGenero();
        if(!db.listAll().isEmpty()) {
            Genero [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for(TipoArchivoEnum r: TipoArchivoEnum.values()) {
            lista.add(r.toString());
        }        
        return lista;
    }


    public List<HashMap> listCancion(){
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()) {
            Cancion [] arreglo = db.listAll().toArray();
           
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString(i));             
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("genero", new DaoGenero().listAll().get(arreglo[i].getId_genero()-1).getNombre());
                aux.put("id_genero", new DaoGenero().listAll().get(arreglo[i].getId_genero()-1).getId().toString());
                aux.put("album", new DaoAlbum().listAll().get(arreglo[i].getId_album()-1).getNombre());
                aux.put("id_album", new DaoAlbum().listAll().get(arreglo[i].getId_album()-1).getId().toString());
                aux.put("duracion", arreglo[i].getDuracion().toString());
                aux.put("url", arreglo[i].getUrl());
                aux.put("tipo", arreglo[i].getTipo().toString());
                lista.add(aux);
            }
        }
        return lista;
    }

        public List<Cancion> listAll() {    
        return (List<Cancion>)Arrays.asList(db.listAll().toArray());
    }

}

