package fr.univamu.iut.platsutilisateurs.Verif;

import fr.univamu.iut.platsutilisateurs.model.Plat;
import fr.univamu.iut.platsutilisateurs.repository.PlatRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PlatVerif {
    @Inject
    private PlatRepository repository;

    /**
     * Vérifie les données d'un plat avant de le créer ou de le mettre à jour.
     * Un plat doit avoir un nom non vide et un prix positif.
     */
    public List<Plat> findAll(){
        return repository.findAll();
    }

    /**
     * Vérifie les données d'un plat avant de le créer ou de le mettre à jour.
     * Un plat doit avoir un nom non vide et un prix positif.
     * return null si le plat n'existe pas ou si les données sont invalides.
     */
    public Plat findById(int id){
        return repository.findByID(id);
    }

    /**
     * Vérifie les données d'un plat avant de le créer ou de le mettre à jour.
     * Un plat doit avoir un nom non vide et un prix positif.
     * return null si le plat n'existe pas ou si les données sont invalides.
     * return le plat créé sinon.
     */
    public Plat create(Plat plat){
        if(plat.getNom() == null || plat.getNom().isBlank() || plat.getPrix() <= 0){
            return null;
        }
        return repository.create(plat);
    }

    /**
     * Vérifie les données d'un plat avant de le créer ou de le mettre à jour.
     * Un plat doit avoir un nom non vide et un prix positif.
     * return null si le plat n'existe pas ou si les données sont invalides.
     * return le plat mis à jour sinon.
     */
    public Plat update(int id, Plat plat){
        if(plat.getNom() == null || plat.getNom().isBlank() || plat.getPrix() <= 0){
            return null;
        }
        return repository.update(id, plat);
    }

    /**
     * Vérifie les données d'un plat avant de le supprimer.
     * return false si le plat n'existe pas ou si les données sont invalides.
     * return true sinon.
     */
    public boolean delete(int id){
        return repository.delete(id);
    }

}
