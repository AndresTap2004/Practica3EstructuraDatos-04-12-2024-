package com.example.rest;

import controller.Dao.servicies.HistorialTransaccionServices;
import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

@Path("historial")
public class HistorialTransaccionApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHistorialTransacciones() {
        HashMap<String, Object> map = new HashMap<>();
        HistorialTransaccionServices hs = new HistorialTransaccionServices();
        map.put("msg", "Ok");
        map.put("data", hs.listAll().toArray());
        if (hs.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorialTransaccion(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        HistorialTransaccionServices hs = new HistorialTransaccionServices();
        try {
            hs.setHistorialTransaccion(hs.get(id));
        } catch (Exception e) {
            // Manejo de errores
        }

        map.put("msg", "Ok");
        map.put("data", hs.getHistorialTransaccion());

        if (hs.getHistorialTransaccion() == null || hs.getHistorialTransaccion().getHistorialTransaccion() == 0) {
            map.put("msg", "No se encontró historial de transacción con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }

        if (hs.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }



    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();

        System.out.println("Entrando a la función save con los datos: " + map.toString());

        try {
            HistorialTransaccionServices hs = new HistorialTransaccionServices();
            hs.getHistorialTransaccion().setIdFamilia(Integer.parseInt(map.get("idFamilia").toString()));
            hs.getHistorialTransaccion().setIdGenerador(Integer.parseInt(map.get("idGenerador").toString()));
            hs.save();
            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            HistorialTransaccionServices hs = new HistorialTransaccionServices();
            hs.setHistorialTransaccion(hs.get(Integer.parseInt(map.get("historialTransaccion").toString())));
            hs.getHistorialTransaccion().setIdFamilia(Integer.parseInt(map.get("idFamilia").toString()));
            hs.getHistorialTransaccion().setIdGenerador(Integer.parseInt(map.get("idGenerador").toString()));
            hs.update();
            res.put("msg", "Ok");
            res.put("data", "Actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            HistorialTransaccionServices hs = new HistorialTransaccionServices();
            hs.delete(id);
            res.put("msg", "Ok");
            res.put("data", "Eliminado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
