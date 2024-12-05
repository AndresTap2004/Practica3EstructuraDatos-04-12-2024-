package controller.Dao;

import models.HistorialTransaccion;  
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class HistorialTransaccionDao extends AdapterDao<HistorialTransaccion> {  
    private HistorialTransaccion historialTransaccion; 
    private LinkedList<HistorialTransaccion> listAll;

    public HistorialTransaccionDao() {  
        super(HistorialTransaccion.class);
        this.listAll = new LinkedList<>();
    }

    public HistorialTransaccion getHistorialTransaccion() {  
        if (historialTransaccion == null) {
            historialTransaccion = new HistorialTransaccion(); 
        }
        return this.historialTransaccion;
    }

    public void setHistorialTransaccion(HistorialTransaccion historialTransaccion) { 
        this.historialTransaccion = historialTransaccion;
    }

    public LinkedList<HistorialTransaccion> getListAll() { 
        if (listAll.isEmpty()) { 
            this.listAll = listAll(); 
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1; 
        historialTransaccion.setHistorialTransaccion(id);  
        this.persist(this.historialTransaccion);
        this.listAll = getListAll(); 
        return true;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getHistorialTransaccion(), getHistorialTransaccion().getHistorialTransaccion() - 1);  
            this.listAll = getListAll(); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(Integer id) throws Exception {
        LinkedList<HistorialTransaccion> list = getListAll(); 
        HistorialTransaccion historialTransaccion = get(id);  
        if (historialTransaccion != null) {
            list.remove(historialTransaccion);  
            String info = g.toJson(list.toArray());
            saveFile(info); 
            this.listAll = list;
            return true;
        } else {
            System.out.println("Historial de transacción con id " + id + " no encontrado.");
            return false;
        }
    }
}