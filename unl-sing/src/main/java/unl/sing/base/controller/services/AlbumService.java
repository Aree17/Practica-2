package unl.sing.base.controller.services;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;
import unl.sing.base.controller.dao.dao_models.DaoAlbum;
import unl.sing.base.models.Album;

@BrowserCallable

@AnonymousAllowed

public class AlbumService {
private DaoAlbum de;
    public AlbumService(){
        de = new DaoAlbum();
    }
    public void create(@NotEmpty String nombre) throws Exception{
        de.getObj().setNombre(nombre);
        
        if(!de.save())
            throw new  Exception("No se pudo guardar los datos de artista");
    }

    
    public List<Album> listAll() {  
       // System.out.println("**********Entro aqui");  
        //System.out.println("lengthy "+Arrays.asList(da.listAll().toArray()).size());    
        return (List<Album>)Arrays.asList(de.listAll().toArray());
    }
}
