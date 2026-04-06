package repository;

import fr.univamu.iut.platsutilisateurs.model.Utilisateur;
import fr.univamu.iut.platsutilisateurs.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link UtilisateurRepository}.
 * <p>
 * Teste directement les opérations CRUD sur la liste d'utilisateurs en mémoire.
 * Utilise une sous-classe qui remplace {@code @PostConstruct init()} pour
 * injecter des données fixes et contrôlées, sans dépendance au fichier JSON.
 * </p>
 */
class UtilisateurRepositoryTest {

    /**
     * Sous-classe de test qui court-circuite le chargement du fichier JSON
     * et initialise la liste avec des utilisateurs prédéfinis.
     */
    static class UtilisateurRepositoryTestable extends UtilisateurRepository {
        /**
         * Remplace l'initialisation depuis le fichier JSON par des données en dur.
         * Appelé manuellement dans {@link #setUp()} à la place du {@code @PostConstruct}.
         */
        @Override
        public void init() {
            utilisateurs.add(new Utilisateur(1, "Dupont", "Marie", "marie.dupont@email.fr", "12 rue des Lilas, 13001 Marseille"));
            utilisateurs.add(new Utilisateur(2, "Martin", "Jean", "jean.martin@email.fr", "3 avenue Foch, 13002 Marseille"));
            utilisateurs.add(new Utilisateur(3, "Bernard", "Sophie", "sophie.bernard@email.fr", "7 avenue du Prado, 13008 Marseille"));
            nextId = 4;
        }
    }

    /** Instance du repository testable réinitialisée avant chaque test. */
    private UtilisateurRepositoryTestable repository;

    /**
     * Crée et initialise une nouvelle instance du repository avant chaque test
     * pour garantir l'isolation entre les tests.
     */
    @BeforeEach
    void setUp() {
        repository = new UtilisateurRepositoryTestable();
        repository.init();
    }


    /**
     * Vérifie que findAll() retourne bien les 3 utilisateurs initialisés.
     */
    @Test
    void findAll_retourneTousLesUtilisateurs() {
        List<Utilisateur> utilisateurs = repository.findAll();

        assertEquals(3, utilisateurs.size());
    }

    /**
     * Vérifie que findAll() ne retourne pas null.
     */
    @Test
    void findAll_retourneListeNonNull() {
        assertNotNull(repository.findAll());
    }

    /**
     * Vérifie que findByID() retourne le bon utilisateur pour un id existant.
     */
    @Test
    void findByID_idExistant_retourneUtilisateur() {
        Utilisateur result = repository.findByID(1);

        assertNotNull(result);
        assertEquals("Dupont", result.getnom());
        assertEquals("Marie", result.getprenom());
        assertEquals("marie.dupont@email.fr", result.getEmail());
    }

    /**
     * Vérifie que findByID() retourne null pour un id inexistant.
     */
    @Test
    void findByID_idInexistant_retourneNull() {
        assertNull(repository.findByID(999));
    }

    /**
     * Vérifie que findByID() retourne null pour un id négatif.
     */
    @Test
    void findByID_idNegatif_retourneNull() {
        assertNull(repository.findByID(-1));
    }

    /**
     * Vérifie que findByEmail() retourne le bon utilisateur pour un email existant.
     */
    @Test
    void findByEmail_emailExistant_retourneUtilisateur() {
        Utilisateur result = repository.findByEmail("jean.martin@email.fr");

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Martin", result.getnom());
    }

    /**
     * Vérifie que findByEmail() retourne null pour un email inexistant.
     */
    @Test
    void findByEmail_emailInexistant_retourneNull() {
        assertNull(repository.findByEmail("inconnu@email.fr"));
    }

    /**
     * Vérifie que findByEmail() est sensible à la casse (email en majuscules non trouvé).
     */
    @Test
    void findByEmail_emailMajuscules_retourneNull() {
        assertNull(repository.findByEmail("MARIE.DUPONT@EMAIL.FR"));
    }


    /**
     * Vérifie que create() ajoute l'utilisateur et lui attribue l'id suivant.
     */
    @Test
    void create_nouvelUtilisateur_estAjouteAvecIdGenere() {
        Utilisateur nouveau = new Utilisateur(0, "Leroux", "Paul", "paul.leroux@email.fr", "45 bd Michelet, 13009 Marseille");

        Utilisateur result = repository.create(nouveau);

        assertEquals(4, result.getId());
        assertEquals(4, repository.findAll().size());
    }

    /**
     * Vérifie que deux créations successives incrémentent bien l'id.
     */
    @Test
    void create_deuxUtilisateurs_idsIncrementesCorrectement() {
        Utilisateur u1 = repository.create(new Utilisateur(0, "Nom1", "Prenom1", "u1@email.fr", "adresse 1"));
        Utilisateur u2 = repository.create(new Utilisateur(0, "Nom2", "Prenom2", "u2@email.fr", "adresse 2"));

        assertEquals(4, u1.getId());
        assertEquals(5, u2.getId());
    }

    /**
     * Vérifie que l'utilisateur créé est bien récupérable par findByID().
     */
    @Test
    void create_utilisateurCree_estRecuperableParFindById() {
        Utilisateur nouveau = new Utilisateur(0, "Leroux", "Paul", "paul@email.fr", "adresse");
        Utilisateur cree = repository.create(nouveau);

        Utilisateur retrouve = repository.findByID(cree.getId());

        assertNotNull(retrouve);
        assertEquals("paul@email.fr", retrouve.getEmail());
    }

    /**
     * Vérifie que l'utilisateur créé est bien retrouvable par findByEmail().
     */
    @Test
    void create_utilisateurCree_estRecuperableParFindByEmail() {
        Utilisateur nouveau = new Utilisateur(0, "Leroux", "Paul", "paul.unique@email.fr", "adresse");
        repository.create(nouveau);

        assertNotNull(repository.findByEmail("paul.unique@email.fr"));
    }

    /**
     * Vérifie que update() modifie correctement tous les champs de l'utilisateur.
     */
    @Test
    void update_utilisateurExistant_modifieLesDonnees() {
        Utilisateur modif = new Utilisateur(0, "Dupont", "Marie-Claire", "marie.claire@email.fr", "99 rue Nouvelle, 13005 Marseille");

        Utilisateur result = repository.update(1, modif);

        assertNotNull(result);
        assertEquals("Marie-Claire", result.getprenom());
        assertEquals("marie.claire@email.fr", result.getEmail());
        assertEquals("99 rue Nouvelle, 13005 Marseille", result.getAddress());
    }

    /**
     * Vérifie que update() retourne null quand l'id ciblé n'existe pas.
     */
    @Test
    void update_idInexistant_retourneNull() {
        Utilisateur modif = new Utilisateur(0, "Nom", "Prenom", "email@test.fr", "adresse");

        assertNull(repository.update(999, modif));
    }

    /**
     * Vérifie que la mise à jour est bien persistée (findByID reflète le changement).
     */
    @Test
    void update_utilisateurExistant_persisteLeChangement() {
        Utilisateur modif = new Utilisateur(0, "Martin", "Jean-Pierre", "jp.martin@email.fr", "nouvelle adresse");
        repository.update(2, modif);

        Utilisateur retrouve = repository.findByID(2);

        assertEquals("Jean-Pierre", retrouve.getprenom());
        assertEquals("jp.martin@email.fr", retrouve.getEmail());
    }

    /**
     * Vérifie que update() ne modifie pas l'id de l'utilisateur.
     */
    @Test
    void update_utilisateurExistant_idResteInchange() {
        Utilisateur modif = new Utilisateur(999, "Nouveau", "Prenom", "new@email.fr", "adresse");

        Utilisateur result = repository.update(1, modif);

        assertEquals(1, result.getId());
    }

    /**
     * Vérifie que delete() retourne true et que l'utilisateur n'est plus dans la liste.
     */
    @Test
    void delete_utilisateurExistant_supprimeEtRetourneTrue() {
        boolean result = repository.delete(1);

        assertTrue(result);
        assertNull(repository.findByID(1));
        assertEquals(2, repository.findAll().size());
    }

    /**
     * Vérifie que delete() retourne false pour un id inexistant.
     */
    @Test
    void delete_idInexistant_retourneFalse() {
        boolean result = repository.delete(999);

        assertFalse(result);
        assertEquals(3, repository.findAll().size());
    }

    /**
     * Vérifie que l'utilisateur supprimé n'est plus retrouvable par findByEmail().
     */
    @Test
    void delete_utilisateurExistant_plusTrouvableParEmail() {
        repository.delete(1);

        assertNull(repository.findByEmail("marie.dupont@email.fr"));
    }

    /**
     * Vérifie que supprimer tous les utilisateurs laisse une liste vide.
     */
    @Test
    void delete_tousLesUtilisateurs_listeDevientVide() {
        repository.delete(1);
        repository.delete(2);
        repository.delete(3);

        assertEquals(0, repository.findAll().size());
    }
}