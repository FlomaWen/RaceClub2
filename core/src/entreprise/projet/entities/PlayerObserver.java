package entreprise.projet.entities;

public interface PlayerObserver {
    void onPlayerPositionChanged(float oldX, float oldY, float newX, float newY);
}
