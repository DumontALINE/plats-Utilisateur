package fr.univamu.iut.platsutilisateurs.resource;

import fr.univamu.iut.platsutilisateurs.Verif.UtilisateurVerif;
import fr.univamu.iut.platsutilisateurs.model.Utilisateur;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurRessource {
    @Inject
    private UtilisateurVerif verif;

    @GET
    public Response getAll(){
        List<Utilisateur> utilisateurs = verif.findAll();
        return Response.ok(utilisateurs).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        Utilisateur utilisateur = verif.findById(id);
        if(utilisateur == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(utilisateur).build();
    }

    @POST
    public Response create(Utilisateur utilisateur){
        Utilisateur newUtilisateur = verif.create(utilisateur);
        if(newUtilisateur == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        URI uri = UriBuilder.fromResource(UtilisateurRessource.class).path(String.valueOf(newUtilisateur.getId())).build();
        return Response.created(uri).entity(newUtilisateur).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Utilisateur utilisateur){
        Utilisateur updatedUtilisateur = verif.update(id, utilisateur);
        if(updatedUtilisateur == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(updatedUtilisateur).build();
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
