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
    private List<Utilisateur> utilisateurs = new ArrayList<>();
    private int nextId = 4;

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

    public List<Utilisateur> findAll() {
        return utilisateurs;
    }

    public Utilisateur findByID(int id) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public Utilisateur findByEmail(String email) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public Utilisateur create(Utilisateur utilisateur) {
        utilisateur.setId(nextId++);
        utilisateurs.add(utilisateur);
        return utilisateur;
    }

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
