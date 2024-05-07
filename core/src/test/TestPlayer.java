import entreprise.project.entities.Drivable;
import entreprise.project.input.InputController;
import entreprise.project.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TestPlayer {
    private Drivable mockMap;
    private InputController mockInputController;
    @BeforeEach
    public void setUp() {
        mockMap = Mockito.mock(Drivable.class);
        mockInputController = Mockito.mock(InputController.class);
    }

    @Test
    public void testPlayerConstructorCasual() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        assertEquals(100.0f, player.getX(), 0.01);
        assertEquals(200.0f, player.getY(), 0.01);
        assertEquals(270, player.getRotation(), 0.01);
        assertEquals(0, player.getSpeed(), 0.01);
        assertEquals(0, player.getPoints());
    }
    @Test
    public void testPlayerConstructorNullMap() {
        assertThrows(NullPointerException.class, () -> {
            new Player(null, mockInputController, 100.0f, 200.0f);
        });
    }

    @Test
    public void testPlayerConstructorNullInputController() {
        assertThrows(NullPointerException.class, () -> {
            new Player(mockMap, null, 100.0f, 200.0f);
        });
    }

    @Test
    public void testMoveUpWithoutCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isUpPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        float initialY = player.getY();

        player.update(1.0f);

        assertTrue(player.getY() > initialY, "Player should have moved upwards.");
    }

    @Test
    public void testMoveUpWithCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isUpPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(false);

        float initialY = player.getY();

        player.update(1.0f);

        assertEquals(initialY, player.getY(), 0.01, "Player should not have moved due to collision.");
    }

    @Test
    public void testMoveLeftWithoutCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isLeftPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        float initialX = player.getX();

        player.update(1.0f);

        assertTrue(player.getX() < initialX, "Player should have moved leftwards.");
    }
    @Test
    public void testMoveLeftWithCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isLeftPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(false);

        float initialX = player.getX();

        player.update(1.0f);

        assertEquals(initialX, player.getX(), 0.01, "Player should not have moved due to collision.");
    }
    @Test
    public void testMoveRightWithoutCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isRightPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        float initialX = player.getX();

        player.update(1.0f);

        assertTrue(player.getX() > initialX, "Player should have moved rightwards.");
    }
    @Test
    public void testMoveRightWithCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isRightPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(false);

        float initialX = player.getX();

        player.update(1.0f);

        assertEquals(initialX, player.getX(), 0.01, "Player should not have moved due to collision.");
    }
    @Test
    public void testMoveDownWithoutCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isDownPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        float initialY = player.getY();

        player.update(1.0f);

        assertTrue(player.getY() < initialY, "Player should have moved downwards.");
    }
    @Test
    public void testMoveDownWithCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isDownPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(false);

        float initialY = player.getY();

        player.update(1.0f);

        assertEquals(initialY, player.getY(), 0.01, "Player should not have moved due to collision.");
    }


    @Test
    public void testDrift() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockInputController.isDriftPressed()).thenReturn(true);
        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);

        player.update(1.0f);

        assertTrue(player.getX() != 0 || player.getY() != 0);
    }

    @Test
    public void testCollision() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(false);

        float initialX = player.getX();
        float initialY = player.getY();

        player.update(1.0f);

        assertEquals(initialX, player.getX(), 0.01, "Player's X position should not change.");
        assertEquals(initialY, player.getY(), 0.01, "Player's Y position should not change.");
        assertEquals(0, player.getSpeed(), 0.01, "Player's speed should be zero after collision.");
    }

    @Test
    public void testLapPointsUp() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);
        when(mockMap.getLapState(anyFloat(), anyFloat(), anyFloat(), anyFloat())).thenReturn(1);

        int initialPoints = player.getPoints();

        player.update(1.0f);

        assertEquals(initialPoints + 1, player.getPoints(), "Points should increase by one after lap state update.");
    }
    @Test
    public void testLapPointsDown() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);
        when(mockMap.getLapState(anyFloat(), anyFloat(), anyFloat(), anyFloat())).thenReturn(-1);

        int initialPoints = player.getPoints();

        player.update(1.0f);

        assertEquals(initialPoints - 1, player.getPoints(), "Points should decrease by one after lap state update.");
    }

    @Test
    public void testLapPointsOutOfFinishLine() {
        Player player = new Player(mockMap, mockInputController, 100.0f, 200.0f);

        when(mockMap.isDrivable(anyInt(), anyInt())).thenReturn(true);
        when(mockMap.getLapState(anyFloat(), anyFloat(), anyFloat(), anyFloat())).thenReturn(0);

        int initialPoints = player.getPoints();

        player.update(1.0f);

        assertEquals(initialPoints, player.getPoints(), "Points should not change if lap state is zero.");
    }
}
