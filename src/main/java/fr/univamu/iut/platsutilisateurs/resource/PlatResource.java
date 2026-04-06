package fr.univamu.iut.platsutilisateurs.resource;

import fr.univamu.iut.platsutilisateurs.Verif.PlatVerif;
import fr.univamu.iut.platsutilisateurs.model.Plat;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("/plats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PlatResource {
    @Inject
    private PlatVerif verif;

    @GET
    public Response getAll(){
        List<Plat> plats = verif.findAll();
        return Response.ok(plats).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        Plat plat = verif.findById(id);
        if(plat == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(plat).build();
    }

    @POST
    public Response create(Plat plat){
        Plat newPlat = verif.create(plat);
        if(newPlat == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        URI uri = UriBuilder.fromResource(PlatResource.class).path(String.valueOf(newPlat.getId())).build();
        return Response.created(uri).entity(newPlat).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Plat plat){
        Plat updatedPlat = verif.update(id, plat);
        if(updatedPlat == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(updatedPlat).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        boolean deleted = verif.delete(id);
        if(!deleted){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
