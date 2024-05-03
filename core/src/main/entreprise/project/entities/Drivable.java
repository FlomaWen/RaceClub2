package entreprise.project.entities;

public interface Drivable {
    boolean isDrivable(int newX, int newY);
    int getLapState(float x, float y, float newX, float newY);
}
