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

    /*
    * permet de lire la fichier Json et stoket uniquement les information plat dans un liste
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

    /*
    * permet de retourner la liste de tous les plats
    */
    public List<Plat> findAll(){
        return plats;
    }

    /*
     * permet de retourner un plat en fonction de son id, si le plat n'existe pas retourne null
     */
    public Plat findByID(int id) {
        for(Plat p :plats){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    /*
    * permet de créer un nouveau plat, le plat doit être envoyé dans le corps de la requete, le plat doit contenir un nom, une description et un prix, le champ id est généré automatiquement, retourne le plat créé
    */
    public Plat create(Plat plat){
        plat.setId(nextId++);
        plats.add(plat);
        return plat;
    }

    /*
    * permet de mettre à jour un plat en fonction de son id, le plat doit être envoyé dans le corps de la requete, le plat doit contenir un nom, une description et un prix, retourne le plat mis à jour, si le plat n'existe pas retourne null
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

    /*
    * permet de supprimer un plat en fonction de son id, retourne true si le plat a
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
