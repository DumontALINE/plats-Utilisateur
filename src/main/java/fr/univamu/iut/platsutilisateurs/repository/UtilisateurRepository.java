package fr.univamu.iut.platsutilisateurs.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univamu.iut.platsutilisateurs.model.Plat;
import fr.univamu.iut.platsutilisateurs.model.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UtilisateurRepository {
    protected List<Utilisateur> utilisateurs = new ArrayList<>();
    protected int nextId = 4;

    /**
     * Charge les utilisateurs à partir du fichier JSON lors de l'initialisation du repository.
     * Le fichier JSON doit être structuré avec une clé "utilisateurs" contenant une liste d'utilisateurs,
     * chaque utilisateur ayant les champs "id", "nom", "prenom", "email" et "adresse".
     * En cas d'erreur lors du chargement, une trace de la pile d'erreurs sera affichée dans la console.
     */
    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("plats-utilisateurs.json");
            Map<String, Object> data = mapper.readValue(is, Map.class);
            List<Map<String, Object>> utilisateursData = (List<Map<String, Object>>) data.get("utilisateurs");

            for (Map<String, Object> u : utilisateursData) {
                Utilisateur utilisateur = new Utilisateur(
                        (Integer) u.get("id"),
                        (String) u.get("nom"),
                        (String) u.get("prenom"),
                        (String) u.get("email"),
                        (String) u.get("adresse")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne la liste de tous les utilisateurs.
     *
     * @return une liste d'objets Utilisateur représentant tous les utilisateurs stockés dans le repository.
     */
    public List<Utilisateur> findAll() {
        return utilisateurs;
    }

    /**
     * Recherche un utilisateur par son identifiant unique.
     *
     * @param id l'identifiant de l'utilisateur à rechercher.
     * @return l'objet Utilisateur correspondant à l'identifiant fourni, ou null si aucun utilisateur n'est trouvé.
     */
    public Utilisateur findByID(int id) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * Recherche un utilisateur par son adresse e-mail.
     *
     * @param email l'adresse e-mail de l'utilisateur à rechercher.
     * @return l'objet Utilisateur correspondant à l'adresse e-mail fournie, ou null si aucun utilisateur n'est trouvé.
     */
    public Utilisateur findByEmail(String email) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Crée un nouvel utilisateur et l'ajoute à la liste des utilisateurs.
     * L'identifiant de l'utilisateur est automatiquement généré en incrémentant le compteur nextId.
     *
     * @param utilisateur l'objet Utilisateur à créer, qui doit contenir les informations nécessaires (nom, prénom, email, adresse).
     * @return l'objet Utilisateur créé avec son identifiant assigné.
     */
    public Utilisateur create(Utilisateur utilisateur) {
        utilisateur.setId(nextId++);
        utilisateurs.add(utilisateur);
        return utilisateur;
    }

    /**
     * Met à jour les informations d'un utilisateur existant identifié par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour.
     * @param utilisateur un objet Utilisateur contenant les nouvelles informations (nom, prénom, email, adresse) à appliquer à l'utilisateur existant.
     * @return l'objet Utilisateur mis à jour si l'utilisateur est trouvé et mis à jour avec succès, ou null si aucun utilisateur n'est trouvé avec l'identifiant fourni.
     */
    public Utilisateur update(int id, Utilisateur utilisateur) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id) {
                u.setNom(utilisateur.getnom());
                u.setprenom(utilisateur.getprenom());
                u.setEmail(utilisateur.getEmail());
                u.setAddress(utilisateur.getAddress());
                return u;
            }
        }
        return null;
    }

    /**
     * Supprime un utilisateur de la liste des utilisateurs en fonction de son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer.
     * @return true si l'utilisateur a été trouvé et supprimé avec succès, ou false si aucun utilisateur n'est trouvé avec l'identifiant fourni.
     */
    public boolean delete(int id) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id) {
                utilisateurs.remove(u);
                return true;
            }
        }
        return false;
    }
}
