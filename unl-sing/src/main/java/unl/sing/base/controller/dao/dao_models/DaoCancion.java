package unl.sing.base.controller.dao.dao_models;

import unl.sing.base.controller.dao.AdapterDao;
import unl.sing.base.models.Cancion;
import unl.sing.base.models.TipoArchivoEnum;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;

    public DaoCancion(){
        super(Cancion.class);
    }

    public Cancion getObj() {
        if(obj==null)
            this.obj=new Cancion();
        return this.obj;
    }

    
    public void setObj(Cancion obj) {
        this.obj = obj;
    }
    public Boolean save() {
        try {
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(Integer pos){
        try{
            this.update(obj, pos);
            return true;

        }catch(Exception e){
            e.printStackTrace(); 
            System.out.println(e);
            //LOG DE ERROR
            return false;
        }
    }

    public Boolean listar(){
        try{
            this.listAll();
            for(int i=0; i>this.listAll().getLength();i++){
            }
            return true;

        }catch(Exception e){
            e.printStackTrace(); 
            System.out.println(e);
            //LOG DE ERROR
            return false;
        }
    }
}
