package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.HistorialTransaccion;
import controller.Dao.HistorialTransaccionDao;

public class HistorialTransaccionServices {
    private HistorialTransaccionDao obj;

    public HistorialTransaccionServices(){
        obj = new HistorialTransaccionDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id); 
    } 
    
    public LinkedList<HistorialTransaccion> listAll() {
        return obj.getListAll();
    }

    public HistorialTransaccion getHistorialTransaccion() {
        return obj.getHistorialTransaccion();
    }

    public void setHistorialTransaccion(HistorialTransaccion historialTransaccion) {
        obj.setHistorialTransaccion(historialTransaccion);
    }

    public HistorialTransaccion get(Integer id) throws Exception {
        return obj.get(id);
    }
}