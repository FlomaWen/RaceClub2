import entreprise.project.entities.Drivable;
import entreprise.project.input.InputController;
import entreprise.project.entities.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TestPlayer {
    // Initialisation du joueur avec des valeurs de départ
    float startX = 100.0f;
    float startY = 200.0f;
    private Drivable mockMap;
    private InputController mockInputController;
    Player player = new Player(mockMap, mockInputController, startX, startY);

    public void setUp() {
        mockMap = Mockito.mock(Drivable.class);
        mockInputController = Mockito.mock(InputController.class);
    }

    @Test
    public void testPlayerConstructor() {



        // Vérifie si les valeurs de départ sont correctement initialisées
        assertEquals(startX, player.getX(), 0.01);
        assertEquals(startY, player.getY(), 0.01);
        assertEquals(270, player.getRotation(), 0.01); // Rotation initiale
        assertEquals(0, player.getSpeed(), 0.01);      // Vitesse initiale
        assertEquals(0, player.getPoints());           // Points initiaux

        // Assure que les contrôleurs et la carte sont correctement assignés
        assertSame(mockInputController, player.inputCtrl);
        assertSame(mockMap, player.map);
    }

    @Test
    public void testMoveUp() {
        // Simuler le mouvement vers le haut
        when(mockInputController.isUpPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        float initialY = player.getY();

        // Simuler un delta de temps d'une seconde
        player.update(1.0f);

        // Le joueur devrait avoir avancé vers le haut
        assertTrue(player.getY() > initialY);
    }

    @Test
    public void testDrift() {
        // Simuler le drift
        when(mockInputController.isDriftPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        player.update(1.0f);

        // Le drift devrait affecter la vitesse
        assertTrue(player.getSpeed() > 0);
        assertTrue(player.getX() != 0 || player.getY() != 0);
    }

    @Test
    public void testCollision() {
        // Simuler une collision
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(false);

        player.update(1.0f);

        // La vitesse doit être remise à zéro en cas de collision
        assertEquals(0, player.getSpeed(), 0.01);
        assertEquals(0, player.getX(), 0.01);
        assertEquals(0, player.getY(), 0.01);
    }

    @Test
    public void testLapPoints() {
        // Simuler le passage sur un point de contrôle
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);
        when(mockMap.getLapState(anyFloat(), anyFloat(), anyFloat(), anyFloat())).thenReturn(1);

        int initialPoints = player.getPoints();

        player.update(1.0f);

        // Le nombre de points doit avoir augmenté
        assertEquals(initialPoints + 1, player.getPoints());
    }
}
