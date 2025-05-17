package unl.sing.base.controller.dao.dao_models;

import unl.sing.base.controller.dao.AdapterDao;
import unl.sing.base.models.Artista;

public class DaoArtista extends AdapterDao<Artista> {
    protected Artista obj;

    public DaoArtista() {
        super(Artista.class);
    }

    public Artista getObj() {
        if (obj == null)
            this.obj = new Artista();
        return this.obj;
    }

    public void setObj(Artista obj) {
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

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
        }
    }

    public static void main(String[] args) {
        DaoArtista da = new DaoArtista();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNacionidad("Ecuatoriana");
        da.getObj().setNombres("Isauro Rivera");
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
        da.setObj(null);
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNacionidad("Ecuatoriana");
        da.getObj().setNombres("Pool Ochao");
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    }

}
