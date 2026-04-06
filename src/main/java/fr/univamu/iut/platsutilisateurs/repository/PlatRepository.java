package fr.univamu.iut.platsutilisateurs.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univamu.iut.platsutilisateurs.model.Plat;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PlatRepository {
    private List<Plat> plats = new ArrayList<Plat>();
    private int nextId = 9;

    /**
     * Charge les plats à partir du fichier JSON lors de l'initialisation du repository.
     * Le fichier JSON doit être structuré avec une clé "plats" contenant une liste de plats,
     * chaque plat ayant les champs "id", "nom", "description" et "prix".
     * En cas d'erreur lors du chargement, une trace de la pile d'erreurs sera affichée dans la console.
     */
    @PostConstruct
    public void init(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("plats-utilisateurs.json");
            Map<String, Object> data = mapper.readValue(is, Map.class);
            List<Map<String, Object>> PlatData = (List<Map<String, Object>>) data.get("plats");

            for (Map<String, Object> p : PlatData) {
                Plat plat = new Plat( (Integer) p.get("id"),
                        (String) p.get("nom"),
                        (String) p.get("description"),
                        ((Number) p.get("prix")).doubleValue()
                );
                plats.add(plat);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Retourne la liste de tous les plats.
     *
     * @return une liste d'objets Plat représentant tous les plats stockés dans le repository.
     */
    public List<Plat> findAll(){
        return plats;
    }

   /**
     * Recherche un plat par son identifiant unique.
     *
     * @param id l'identifiant du plat à rechercher.
     * @return l'objet Plat correspondant à l'identifiant fourni, ou null si aucun plat n'est trouvé.
     */
    public Plat findByID(int id) {
        for(Plat p :plats){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

   /**
     * Crée un nouveau plat et l'ajoute à la liste des plats.
     * L'identifiant du plat est automatiquement généré en incrémentant le compteur nextId.
     *
     * @param plat l'objet Plat à créer, qui doit contenir les informations nécessaires (nom, description, prix).
     * @return l'objet Plat créé avec son identifiant assigné, ou null si les informations du plat sont invalides.
     */
    public Plat create(Plat plat){
        plat.setId(nextId++);
        plats.add(plat);
        return plat;
    }

   /**
     * Met à jour les informations d'un plat existant identifié par son identifiant unique.
     *
     * @param id l'identifiant du plat à mettre à jour.
     * @param plat l'objet Plat contenant les nouvelles informations (nom, description, prix) à appliquer au plat existant.
     * @return l'objet Plat mis à jour, ou null si aucun plat n'est trouvé avec l'identifiant fourni.
     */
    public Plat update(int id, Plat plat){
        for(Plat p :plats){
            if(p.getId() == id){
                p.setNom(plat.getNom());
                p.setDescription(plat.getDescription());
                p.setPrix(plat.getPrix());
                return p;
            }
        }
        return null;
    }

    /**
     * Supprime un plat existant identifié par son identifiant unique.
     *
     * @param id l'identifiant du plat à supprimer.
     * @return true si le plat a été trouvé et supprimé avec succès, ou false si aucun plat n'est trouvé avec l'identifiant fourni.
     */
    public boolean delete(int id){
        for(Plat p :plats){
            if(p.getId() == id){
                plats.remove(p);
                return true;
            }
        }
        return false;
    }

}
