package fr.univamu.iut.platsutilisateurs.Verif;

import fr.univamu.iut.platsutilisateurs.model.Utilisateur;
import fr.univamu.iut.platsutilisateurs.repository.UtilisateurRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UtilisateurVerif {
    @Inject
    private UtilisateurRepository repository;

    /**
     * Vérifie les données de l'utilisateur avant de les transmettre au repository.
     * Assure que les champs obligatoires sont remplis et que l'email est unique.
     * @return  null si les données sont invalides ou si l'email existe déjà, sinon retourne l'utilisateur créé ou mis à jour.
     */
    public List<Utilisateur> findAll(){
        return repository.findAll();
    }

    /**
     * Trouve un utilisateur par son ID.
     * @param id L'ID de l'utilisateur à trouver.
     * @return L'utilisateur correspondant à l'ID, ou null si aucun utilisateur n'est trouvé.
     */
    public Utilisateur findById(int id){
        return repository.findByID(id);
    }

    /**
     * Trouve un utilisateur par son email.
     * @param email L'email de l'utilisateur à trouver.
     * @return L'utilisateur correspondant à l'email, ou null si aucun utilisateur n'est trouvé.
     */
    public Utilisateur findByEmail(String email){
        return repository.findByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur après validation des données.
     * @param utilisateur L'utilisateur à créer.
     * @return L'utilisateur créé, ou null si les données sont invalides ou si l'email existe déjà.
     */
    public Utilisateur create(Utilisateur utilisateur){
        if(utilisateur.getnom() == null || utilisateur.getnom().isEmpty() ||
           utilisateur.getprenom() ==null || utilisateur.getprenom().isEmpty() ||
           utilisateur.getEmail() ==null || utilisateur.getEmail().isEmpty() ||
           utilisateur.getAddress() ==null || utilisateur.getAddress().isEmpty()) {
            return null;
        }
        if(repository.findByEmail(utilisateur.getEmail()) != null){
            return null;
        }
        return repository.create(utilisateur);
    }

    /**
     * Met à jour un utilisateur existant après validation des données.
     * @param id L'ID de l'utilisateur à mettre à jour.
     * @param utilisateur L'utilisateur avec les nouvelles données.
     * @return L'utilisateur mis à jour, ou null si les données sont invalides ou si l'email existe déjà pour un autre utilisateur.
     */
    public Utilisateur update(int id, Utilisateur utilisateur){
        if(utilisateur.getnom() == null || utilisateur.getnom().isEmpty() ||
                utilisateur.getprenom() ==null || utilisateur.getprenom().isEmpty() ||
                utilisateur.getEmail() ==null || utilisateur.getEmail().isEmpty() ||
                utilisateur.getAddress() ==null || utilisateur.getAddress().isEmpty())
        {
            return null;
        }
        return repository.update(id, utilisateur);
    }

    /**
     * Supprime un utilisateur par son ID.
     * @param id L'ID de l'utilisateur à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    public boolean delete(int id){
        return repository.delete(id);
    }
}
