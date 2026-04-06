package verif;

import fr.univamu.iut.platsutilisateurs.Verif.UtilisateurVerif;
import fr.univamu.iut.platsutilisateurs.model.Utilisateur;
import fr.univamu.iut.platsutilisateurs.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour {@link UtilisateurVerif}.
 * <p>
 * Vérifie la logique de validation métier sur les abonnés,
 * notamment la vérification des champs obligatoires et la
 * délégation correcte vers {@link UtilisateurRepository}.
 * Le repository est mocké pour isoler le service.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class UtilisateurVerifTest {

    /** Mock du repository injecté dans le service testé. */
    @Mock
    private UtilisateurRepository repository;

    /** Instance du service à tester, avec le mock injecté automatiquement. */
    @InjectMocks
    private UtilisateurVerif verif;

    /** Utilisateur valide réutilisé dans plusieurs tests. */
    private Utilisateur userValide;

    /**
     * Initialise un utilisateur valide avant chaque test.
     */
    @BeforeEach
    void setUp() {
        userValide = new Utilisateur(0, "Dupont", "Marie", "marie.dupont@email.fr", "12 rue des Lilas, 13001 Marseille");
    }

    /**
     * Vérifie que findAll() retourne bien la liste fournie par le repository.
     */
    @Test
    void findAll_retourneLaListeDuRepository() {
        List<Utilisateur> liste = Arrays.asList(
                new Utilisateur(1, "Dupont", "Marie", "marie@email.fr", "adresse 1"),
                new Utilisateur(2, "Martin", "Jean", "jean@email.fr", "adresse 2")
        );
        when(repository.findAll()).thenReturn(liste);

        List<Utilisateur> result = verif.findAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    /**
     * Vérifie que findById() retourne le bon utilisateur quand il existe.
     */
    @Test
    void findById_idExistant_retourneUtilisateur() {
        when(repository.findByID(1)).thenReturn(userValide);

        Utilisateur result = verif.findById(1);

        assertNotNull(result);
        assertEquals("Dupont", result.getnom());
    }

    /**
     * Vérifie que findById() retourne null quand l'id n'existe pas.
     */
    @Test
    void findById_idInexistant_retourneNull() {
        when(repository.findByID(99)).thenReturn(null);

        assertNull(verif.findById(99));
    }

    /**
     * Vérifie que findByEmail() retourne l'utilisateur correspondant à l'email.
     */
    @Test
    void findByEmail_emailExistant_retourneUtilisateur() {
        when(repository.findByEmail("marie.dupont@email.fr")).thenReturn(userValide);

        Utilisateur result = verif.findByEmail("marie.dupont@email.fr");

        assertNotNull(result);
        assertEquals("marie.dupont@email.fr", result.getEmail());
    }

    /**
     * Vérifie que findByEmail() retourne null si l'email n'est pas trouvé.
     */
    @Test
    void findByEmail_emailInexistant_retourneNull() {
        when(repository.findByEmail("inconnu@email.fr")).thenReturn(null);

        assertNull(verif.findByEmail("inconnu@email.fr"));
    }

    /**
     * Vérifie qu'un utilisateur avec toutes les données valides est correctement créé.
     */
    @Test
    void create_userValide_retourneUserAvecId() {
        Utilisateur userCree = new Utilisateur(5, "Dupont", "Marie", "marie.dupont@email.fr", "12 rue des Lilas");
        when(repository.create(userValide)).thenReturn(userCree);

        Utilisateur result = verif.create(userValide);

        assertNotNull(result);
        assertEquals(5, result.getId());
        verify(repository, times(1)).create(userValide);
    }

    /**
     * Vérifie qu'un utilisateur avec un nom vide est rejeté.
     */
    @Test
    void create_nomVide_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "", "Marie", "marie@email.fr", "adresse");

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un utilisateur avec un nom null est rejeté.
     */
    @Test
    void create_nomNull_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, null, "Marie", "marie@email.fr", "adresse");

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un utilisateur avec un prénom vide est rejeté.
     */
    @Test
    void create_prenomVide_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "Dupont", "", "marie@email.fr", "adresse");

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un utilisateur avec un email vide est rejeté.
     */
    @Test
    void create_emailVide_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "Dupont", "Marie", "", "adresse");

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un utilisateur avec un email null est rejeté.
     */
    @Test
    void create_emailNull_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "Dupont", "Marie", null, "adresse");

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un utilisateur avec une adresse vide est rejeté.
     */
    @Test
    void create_adresseVide_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "Dupont", "Marie", "marie@email.fr", "");

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un utilisateur avec une adresse null est rejeté.
     */
    @Test
    void create_adresseNull_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "Dupont", "Marie", "marie@email.fr", null);

        assertNull(verif.create(invalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'une mise à jour avec des données valides retourne l'utilisateur modifié.
     */
    @Test
    void update_donneesValides_retourneUserMisAJour() {
        when(repository.update(1, userValide)).thenReturn(userValide);

        Utilisateur result = verif.update(1, userValide);

        assertNotNull(result);
        verify(repository, times(1)).update(1, userValide);
    }

    /**
     * Vérifie que update() retourne null quand l'utilisateur ciblé n'existe pas.
     */
    @Test
    void update_idInexistant_retourneNull() {
        when(repository.update(99, userValide)).thenReturn(null);

        assertNull(verif.update(99, userValide));
    }

    /**
     * Vérifie qu'une mise à jour avec un nom vide est rejetée sans appel au repository.
     */
    @Test
    void update_nomVide_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "", "Marie", "marie@email.fr", "adresse");

        assertNull(verif.update(1, invalide));
        verify(repository, never()).update(anyInt(), any());
    }

    /**
     * Vérifie qu'une mise à jour avec un email vide est rejetée sans appel au repository.
     */
    @Test
    void update_emailVide_retourneNull() {
        Utilisateur invalide = new Utilisateur(0, "Dupont", "Marie", "", "adresse");

        assertNull(verif.update(1, invalide));
        verify(repository, never()).update(anyInt(), any());
    }

    /**
     * Vérifie que delete() retourne true quand l'utilisateur existe et est supprimé.
     */
    @Test
    void delete_userExistant_retourneTrue() {
        when(repository.delete(1)).thenReturn(true);

        assertTrue(verif.delete(1));
    }

    /**
     * Vérifie que delete() retourne false quand l'utilisateur n'existe pas.
     */
    @Test
    void delete_userInexistant_retourneFalse() {
        when(repository.delete(99)).thenReturn(false);

        assertFalse(verif.delete(99));
    }
}