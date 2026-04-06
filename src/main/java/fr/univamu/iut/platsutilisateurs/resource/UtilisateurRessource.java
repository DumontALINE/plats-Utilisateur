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

    /**
     * Récupère tous les utilisateurs
     * @return une liste d'utilisateurs ou une réponse vide si aucun utilisateur n'est trouvé
     */
    @GET
    public Response getAll(){
        List<Utilisateur> utilisateurs = verif.findAll();
        return Response.ok(utilisateurs).build();
    }

    /**
     * Récupère un utilisateur par son ID
     * @param id l'ID de l'utilisateur à récupérer
     * @return l'utilisateur correspondant à l'ID ou une réponse 404 si l'utilisateur n'est pas trouvé
     */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        Utilisateur utilisateur = verif.findById(id);
        if(utilisateur == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(utilisateur).build();
    }

    /**
     * Crée un nouvel utilisateur
     * @param utilisateur l'utilisateur à créer, doit contenir un nom, un prénom, une adresse email et une adresse physique
     * @return l'utilisateur créé avec son ID ou une réponse 400 si les données de l'utilisateur sont invalides
     */
    @POST
    public Response create(Utilisateur utilisateur){
        Utilisateur newUtilisateur = verif.create(utilisateur);
        if(newUtilisateur == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        URI uri = UriBuilder.fromResource(UtilisateurRessource.class).path(String.valueOf(newUtilisateur.getId())).build();
        return Response.created(uri).entity(newUtilisateur).build();
    }

    /**
     * Met à jour un utilisateur existant
     * @param id l'ID de l'utilisateur à mettre à jour
     * @param utilisateur les nouvelles données de l'utilisateur, doit contenir un nom, un prénom, une adresse email et une adresse physique
     * @return l'utilisateur mis à jour ou une réponse 400 si les données de l'utilisateur sont invalides ou une réponse 404 si l'utilisateur n'est pas trouvé
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Utilisateur utilisateur){
        if (utilisateur.getnom() == null || utilisateur.getnom().isBlank() ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Utilisateur updated = verif.update(id, utilisateur);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    /**
     * Supprime un utilisateur par son ID
     * @param id l'ID de l'utilisateur à supprimer
     * @return une réponse 204 si la suppression est réussie ou une réponse 404 si l'utilisateur n'est pas trouvé
     */
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
