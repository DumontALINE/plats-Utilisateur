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

    public List<Utilisateur> findAll(){
        return repository.findAll();
    }

    public Utilisateur findById(int id){
        return repository.findByID(id);
    }

    public Utilisateur findByEmail(String email){
        return repository.findByEmail(email);
    }

    public Utilisateur create(Utilisateur utilisateur){
        if(utilisateur.getnom() == null || utilisateur.getnom().isEmpty() ||
           utilisateur.getprenom() ==null || utilisateur.getprenom().isEmpty() ||
           utilisateur.getEmail() ==null || utilisateur.getEmail().isEmpty() ||
           utilisateur.getAddress() ==null || utilisateur.getAddress().isEmpty())
        {
            return null;
        }
        return repository.create(utilisateur);
    }

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

    public boolean delete(int id){
        return repository.delete(id);
    }
}
