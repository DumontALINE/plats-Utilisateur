package repository;

import fr.univamu.iut.platsutilisateurs.model.Plat;
import fr.univamu.iut.platsutilisateurs.repository.PlatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link PlatRepository}.
 * <p>
 * Teste directement les opérations CRUD sur la liste en mémoire.
 * Comme le repository charge ses données depuis un fichier JSON via {@code @PostConstruct},
 * on utilise une sous-classe de test qui remplace {@code init()} pour injecter
 * des données contrôlées, sans dépendance au fichier JSON.
 * </p>
 */
class PlatRepositoryTest {

    /**
     * Sous-classe de test qui court-circuite le {@code @PostConstruct}
     * et initialise la liste avec des données fixes et prévisibles.
     */
    static class PlatRepositoryTestable extends PlatRepository {
        /**
         * Remplace l'initialisation depuis le fichier JSON par des données en dur.
         * Appelé manuellement dans {@link #setUp()} à la place du {@code @PostConstruct}.
         */
        @Override
        public void init() {
            plats.add(new Plat(1, "Salade niçoise", "Avec thon et anchois", 8.50));
            plats.add(new Plat(2, "Aïoli", "Légumes vapeur et morue", 12.00));
            plats.add(new Plat(3, "Gratin dauphinois", "Pommes de terre à la crème", 9.00));
            nextId = 4;
        }
    }

    /** Instance du repository testable réinitialisée avant chaque test. */
    private PlatRepositoryTestable repository;

    /**
     * Crée et initialise une nouvelle instance du repository avant chaque test
     * pour garantir l'isolation entre les tests.
     */
    @BeforeEach
    void setUp() {
        repository = new PlatRepositoryTestable();
        repository.init();
    }

    /**
     * Vérifie que findAll() retourne bien les 3 plats initialisés.
     */
    @Test
    void findAll_retourneTousLesPlats() {
        List<Plat> plats = repository.findAll();

        assertEquals(3, plats.size());
    }

    /**
     * Vérifie que findAll() ne retourne pas null mais bien une liste.
     */
    @Test
    void findAll_retourneListeNonNull() {
        assertNotNull(repository.findAll());
    }

    /**
     * Vérifie que findByID() retourne le bon plat pour un id existant.
     */
    @Test
    void findByID_idExistant_retournePlat() {
        Plat plat = repository.findByID(1);

        assertNotNull(plat);
        assertEquals("Salade niçoise", plat.getNom());
        assertEquals(8.50, plat.getPrix());
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
     * Vérifie que create() ajoute le plat et lui attribue l'id suivant.
     */
    @Test
    void create_nouveauPlat_estAjouteAvecIdGenere() {
        Plat nouveau = new Plat(0, "Bouillabaisse", "Soupe de poissons", 15.50);

        Plat result = repository.create(nouveau);

        assertEquals(4, result.getId());
        assertEquals(4, repository.findAll().size());
    }

    /**
     * Vérifie que deux créations successives incrémentent bien l'id.
     */
    @Test
    void create_deuxPlats_idsIncrementesCorrectement() {
        Plat p1 = repository.create(new Plat(0, "Plat A", "desc", 5.0));
        Plat p2 = repository.create(new Plat(0, "Plat B", "desc", 6.0));

        assertEquals(4, p1.getId());
        assertEquals(5, p2.getId());
    }

    /**
     * Vérifie que le plat créé est bien récupérable ensuite par findByID().
     */
    @Test
    void create_platCree_estRecuperableParFindById() {
        Plat nouveau = new Plat(0, "Mousse chocolat", "Chocolat noir 70%", 5.00);
        Plat cree = repository.create(nouveau);

        Plat retrouve = repository.findByID(cree.getId());

        assertNotNull(retrouve);
        assertEquals("Mousse chocolat", retrouve.getNom());
    }

    /**
     * Vérifie que update() modifie correctement le nom, la description et le prix.
     */
    @Test
    void update_platExistant_modifieLesDonnees() {
        Plat modif = new Plat(0, "Salade niçoise revisitée", "Nouvelle recette", 10.00);

        Plat result = repository.update(1, modif);

        assertNotNull(result);
        assertEquals("Salade niçoise revisitée", result.getNom());
        assertEquals(10.00, result.getPrix());
        assertEquals("Nouvelle recette", result.getDescription());
    }

    /**
     * Vérifie que update() retourne null quand le plat ciblé n'existe pas.
     */
    @Test
    void update_idInexistant_retourneNull() {
        Plat modif = new Plat(0, "Nom", "desc", 5.0);

        assertNull(repository.update(999, modif));
    }

    /**
     * Vérifie que la mise à jour est bien persistée dans la liste (findByID reflète le changement).
     */
    @Test
    void update_platExistant_persisteLeChangement() {
        Plat modif = new Plat(0, "Aïoli premium", "Nouvelle version", 14.00);
        repository.update(2, modif);

        Plat retrouve = repository.findByID(2);

        assertEquals("Aïoli premium", retrouve.getNom());
        assertEquals(14.00, retrouve.getPrix());
    }

    /**
     * Vérifie que update() ne modifie pas l'id du plat existant.
     */
    @Test
    void update_platExistant_idResteinchange() {
        Plat modif = new Plat(999, "Nom modifié", "desc", 7.0);

        Plat result = repository.update(1, modif);

        assertEquals(1, result.getId());
    }

    /**
     * Vérifie que delete() retourne true et que le plat n'est plus dans la liste.
     */
    @Test
    void delete_platExistant_supprimeLePlatEtRetourneTrue() {
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
     * Vérifie que supprimer le dernier plat laisse bien une liste vide.
     */
    @Test
    void delete_tousLesPlats_listeDevientVide() {
        repository.delete(1);
        repository.delete(2);
        repository.delete(3);

        assertEquals(0, repository.findAll().size());
    }
}