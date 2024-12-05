// package com.example.rest;

// import java.util.HashMap;
// import javax.ws.rs.GET;
// import javax.ws.rs.Path;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;  // Import correcto para Response

// import controller.Dao.servicies.PersonaServicies;
// // Importar tu LinkedList personalizada


// /**
//  * Root resource (exposed at "myresource" path)
//  */
// @Path("myresource")
// public class MyResource {

//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response getIt() {
    
//     HashMap mapa = new HashMap<>();
//     PersonaServicies pd = new PersonaServicies(); 
//     String aux = "";
//     try{
//         pd.getPersona().setNombre("Ariana Sophia");
//         pd.getPersona().setApellido("Sarango Tandazo");
//         pd.getPersona().setDNI("12345678");
//         pd.getPersona().setCelular("0989700718");
//         pd.save();
        
//         pd.getPersona().setNombre("Eberson Daniel");
//         pd.getPersona().setApellido("Guayllas Poma");
//         pd.getPersona().setDNI("1105765745");
//         pd.getPersona().setCelular("0997288985");
//         pd.save();
//         aux = " La lista esta vacia"+pd.listAll().isEmpty();
//     } catch (Exception e){
//         System.out.println("Error al guardar: "+e);
//         // Todo 

//     }
//         mapa.put("msg", "Ok");
//         mapa.put("data", "test "+aux);
        
//         // Construir la respuesta correctamente
//         return Response.ok(mapa).build();
//     }
// }
