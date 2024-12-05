package com.example.rest;

import controller.Dao.servicies.FamiliaServicies;
import controller.tda.list.LinkedList;
import models.Familia;

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

@Path("familia")
public class FamiliaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilias() {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();
        map.put("msg", "Ok");
        map.put("data", fs.listAll().toArray());
        if (fs.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFamilia(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();
        try {
            fs.setFamilia(fs.get(id));
        } catch (Exception e) {
            // Manejo de errores
        }

        map.put("msg", "Ok");
        map.put("data", fs.getFamilia());

        if (fs.getFamilia() == null || fs.getFamilia().getIdFamilia() == 0) {
            map.put("msg", "No se encontró familia con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }

        if (fs.listAll().isEmpty()) {
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

        try {
            FamiliaServicies fs = new FamiliaServicies();
            fs.getFamilia().setNombre(map.get("nombre").toString());
            fs.save();
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
            FamiliaServicies fs = new FamiliaServicies();
            fs.setFamilia(fs.get(Integer.parseInt(map.get("idFamilia").toString())));
            fs.getFamilia().setNombre(map.get("nombre").toString());

            fs.update();
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
        FamiliaServicies fs = new FamiliaServicies();

        try {
            boolean success = fs.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "No se pudo eliminar la familia");
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @SuppressWarnings("unchecked")
    @Path("/sort/{method}/{field}/{orden}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortGeneradores(@PathParam("method") String method, @PathParam("field") String field,
            @PathParam("orden") Integer orden) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();

        try {
            LinkedList<Familia> lista;
            switch (method) {
                case "QuickSort":
                    lista = fs.quickSort(fs.listAll(), orden, field);
                    break;
                case "MergeSort":
                    lista = fs.mergeSort(orden, field);
                    break;
                case "ShellSort":
                    lista = fs.shellSort(orden, field);
                    break;
                default:
                    throw new IllegalArgumentException("Método de ordenación no válido: " + method);
            }
            map.put("msg", "OK");
            map.put("data", lista.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al ordenar la lista: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/search/{field}/{value}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchGeneradores(@PathParam("field") String field, @PathParam("value") String value) {
        HashMap<String, Object> map = new HashMap<>();
        FamiliaServicies fs = new FamiliaServicies();
        try {
            LinkedList<Familia> lista;
            if ("idFamilia".equals(field)) {
                Familia familia = fs.binaria(field, value);
                lista = new LinkedList<>();
                if (familia != null) {
                    lista.add(familia);
                }
            } else {
                lista = fs.linealBinaria(field, value);
            }
            map.put("msg", "OK");
            map.put("data", lista.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al buscar en la lista: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}