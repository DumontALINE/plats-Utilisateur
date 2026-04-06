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

    /**
     * Récupère tous les plats disponibles.
     *
     * @return Une réponse HTTP contenant la liste de tous les plats.
     */
    @GET
    public Response getAll(){
        List<Plat> plats = verif.findAll();
        return Response.ok(plats).build();
    }

    /**
     * Récupère un plat spécifique par son ID.
     *
     * @param id L'ID du plat à récupérer.
     * @return Une réponse HTTP contenant le plat correspondant ou une erreur si non trouvé.
     */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        Plat plat = verif.findById(id);
        if(plat == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(plat).build();
    }

    /**
     * Crée un nouveau plat.
     *
     * @param plat L'objet Plat à créer.
     * @return Une réponse HTTP contenant le plat créé ou une erreur si les données sont invalides.
     */
    @POST
    public Response create(Plat plat){
        Plat newPlat = verif.create(plat);
        if(newPlat == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        URI uri = UriBuilder.fromResource(PlatResource.class).path(String.valueOf(newPlat.getId())).build();
        return Response.created(uri).entity(newPlat).build();
    }

    /**
     * Met à jour un plat existant.
     *
     * @param id   L'ID du plat à mettre à jour.
     * @param plat L'objet Plat contenant les nouvelles données.
     * @return Une réponse HTTP contenant le plat mis à jour ou une erreur si les données sont invalides ou si le plat n'existe pas.
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Plat plat) {
        if (plat.getNom() == null || plat.getNom().isBlank() || plat.getPrix() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Plat updated = verif.update(id, plat);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    /**
     * Supprime un plat existant.
     *
     * @param id L'ID du plat à supprimer.
     * @return Une réponse HTTP indiquant le succès de la suppression ou une erreur si le plat n'existe pas.
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
