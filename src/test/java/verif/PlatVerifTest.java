package verif;

import fr.univamu.iut.platsutilisateurs.Verif.PlatVerif;
import fr.univamu.iut.platsutilisateurs.model.Plat;
import fr.univamu.iut.platsutilisateurs.repository.PlatRepository;
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
 * Tests unitaires pour la classe {@link PlatVerif}.
 * <p>
 * Vérifie la logique de validation métier et la délégation
 * correcte vers {@link PlatRepository}.
 * Le repository est mocké pour isoler complètement le service.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class PlatVerifTest {

    /** Mock du repository injecté dans le service testé. */
    @Mock
    private PlatRepository repository;

    /** Instance du service à tester, avec le mock injecté automatiquement. */
    @InjectMocks
    private PlatVerif verif;

    /** Plat valide réutilisé dans plusieurs tests. */
    private Plat platValide;

    /**
     * Initialise un plat valide avant chaque test.
     */
    @BeforeEach
    void setUp() {
        platValide = new Plat(0, "Salade niçoise", "Avec thon et anchois", 8.50);
    }

    /**
     * Vérifie que findAll() retourne bien la liste fournie par le repository.
     */
    @Test
    void findAll_retourneLaListeDuRepository() {
        List<Plat> liste = Arrays.asList(
                new Plat(1, "Salade niçoise", "desc", 8.50),
                new Plat(2, "Aïoli", "desc", 12.00)
        );
        when(repository.findAll()).thenReturn(liste);

        List<Plat> result = verif.findAll();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    /**
     * Vérifie que findById() retourne le bon plat quand il existe.
     */
    @Test
    void findById_idExistant_retournePlat() {
        when(repository.findByID(1)).thenReturn(platValide);

        Plat result = verif.findById(1);

        assertNotNull(result);
        assertEquals("Salade niçoise", result.getNom());
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
     * Vérifie qu'un plat valide est bien créé et retourné avec son id généré.
     */
    @Test
    void create_platValide_retournePlatAvecId() {
        Plat platCree = new Plat(9, "Salade niçoise", "Avec thon et anchois", 8.50);
        when(repository.create(platValide)).thenReturn(platCree);

        Plat result = verif.create(platValide);

        assertNotNull(result);
        assertEquals(9, result.getId());
        verify(repository, times(1)).create(platValide);
    }

    /**
     * Vérifie qu'un plat avec un nom vide est rejeté (retourne null).
     */
    @Test
    void create_nomVide_retourneNull() {
        Plat platInvalide = new Plat(0, "", "description", 8.50);

        assertNull(verif.create(platInvalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un plat avec un nom ne contenant que des espaces est rejeté.
     */
    @Test
    void create_nomBlanc_retourneNull() {
        Plat platInvalide = new Plat(0, "   ", "description", 8.50);

        assertNull(verif.create(platInvalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un plat avec un nom null est rejeté.
     */
    @Test
    void create_nomNull_retourneNull() {
        Plat platInvalide = new Plat(0, null, "description", 5.0);

        assertNull(verif.create(platInvalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un plat avec un prix égal à zéro est rejeté.
     */
    @Test
    void create_prixZero_retourneNull() {
        Plat platInvalide = new Plat(0, "Nom", "description", 0.0);

        assertNull(verif.create(platInvalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'un plat avec un prix négatif est rejeté.
     */
    @Test
    void create_prixNegatif_retourneNull() {
        Plat platInvalide = new Plat(0, "Nom", "description", -5.0);

        assertNull(verif.create(platInvalide));
        verify(repository, never()).create(any());
    }

    /**
     * Vérifie qu'une mise à jour avec des données valides retourne le plat modifié.
     */
    @Test
    void update_donneesValides_retournePlatMisAJour() {
        when(repository.update(1, platValide)).thenReturn(platValide);

        Plat result = verif.update(1, platValide);

        assertNotNull(result);
        verify(repository, times(1)).update(1, platValide);
    }

    /**
     * Vérifie que update() retourne null quand le plat ciblé n'existe pas.
     */
    @Test
    void update_idInexistant_retourneNull() {
        when(repository.update(99, platValide)).thenReturn(null);

        assertNull(verif.update(99, platValide));
    }

    /**
     * Vérifie qu'une mise à jour avec un nom vide est rejetée sans appel au repository.
     */
    @Test
    void update_nomVide_retourneNull() {
        Plat invalide = new Plat(0, "", "desc", 5.0);

        assertNull(verif.update(1, invalide));
        verify(repository, never()).update(anyInt(), any());
    }

    /**
     * Vérifie qu'une mise à jour avec un prix invalide est rejetée sans appel au repository.
     */
    @Test
    void update_prixInvalide_retourneNull() {
        Plat invalide = new Plat(0, "Nom", "desc", -1.0);

        assertNull(verif.update(1, invalide));
        verify(repository, never()).update(anyInt(), any());
    }

    /**
     * Vérifie que delete() retourne true quand le plat existe et est supprimé.
     */
    @Test
    void delete_platExistant_retourneTrue() {
        when(repository.delete(1)).thenReturn(true);

        assertTrue(verif.delete(1));
    }

    /**
     * Vérifie que delete() retourne false quand le plat n'existe pas.
     */
    @Test
    void delete_platInexistant_retourneFalse() {
        when(repository.delete(99)).thenReturn(false);

        assertFalse(verif.delete(99));
    }
}