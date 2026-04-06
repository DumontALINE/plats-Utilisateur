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

    public List<Plat> findAll(){
        return repository.findAll();
    }

    public Plat findById(int id){
        return repository.findByID(id);
    }

    public Plat create(Plat plat){
        if(plat.getNom() == null || plat.getNom().isBlank() || plat.getPrix() <= 0){
            return null;
        }
        return repository.create(plat);
    }

    public Plat update(int id, Plat plat){
        if(plat.getNom() == null || plat.getNom().isBlank() || plat.getPrix() <= 0){
            return null;
        }
        return repository.update(id, plat);
    }

    public boolean delete(int id){
        return repository.delete(id);
    }

}
