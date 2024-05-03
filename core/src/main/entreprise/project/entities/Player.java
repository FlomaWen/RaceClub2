    package entreprise.project.entities;

    import entreprise.project.input.InputController;

    import java.util.ArrayList;
    import java.util.List;

    public class Player {
        private final static float DRIFT_FACTOR = 0.75f;
        private final static float MAX_SPEED = 300;
        private final static float MAX_ACCELERATION = MAX_SPEED/1.5f;
//PUBLIC??
        public final Drivable map;
//PUBLIC??
        public final InputController inputCtrl;

        private float x;
        private float y;
        private float speedX;
        private float speedY;
        private float rotation;
        private float rotationSpeed;
        private float speed;
        private int points;

        public Player(Drivable map, InputController inputCtrl, float startX, float startY ) {
            x = startX;
            y = startY;
            rotation = 270;
            this.inputCtrl = inputCtrl;
            this.map = map;
            rotationSpeed = 5;
            speed = 0;
        }


        public int getPoints() {
            return points;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
        public float getRotation() {
            return rotation;
        }

        public float getSpeed() {
            return this.speed;
        }

        public void update(float delta) {
            float oldX = x;
            float oldY = y;
            updateSpeed(delta);

            float rad = (float) Math.toRadians(rotation);
            float driftSpeedX = speed * (float) Math.cos(rad - Math.PI / 2);
            float driftSpeedY = speed * (float) Math.sin(rad - Math.PI / 2);

            if (inputCtrl.isDriftPressed()) {
                speedX = speedX * DRIFT_FACTOR + driftSpeedX * (1 - DRIFT_FACTOR);
                speedY = speedY * DRIFT_FACTOR + driftSpeedY * (1 - DRIFT_FACTOR);
            } else {
                speedX = driftSpeedX;
                speedY = driftSpeedY;
            }

            float newX = x + speedX * delta;
            float newY = y + speedY * delta;

            if (map.isDrivable((int) newX, (int) newY)) {
                if(map.getLapState(x, y, newX, newY) == 1) {
                    points++;
                } else if (map.getLapState(x, y, newX, newY) == -1) {
                    points--;
                }
                x = newX;
                y = newY;

                notifyObservers(oldX,oldY,x, y);
            } else {
                speed = 0;
            }

        }
        private float normalizeAngle(float angle) {
            while (angle < 0) angle += 360;
            while (angle >= 360) angle -= 360;
            return angle;
        }


        private void updateSpeed(float delta) {
            float dx = 0, dy = 0;
            if (inputCtrl.isLeftPressed()) dx -= 1;
            if (inputCtrl.isRightPressed()) dx += 1;
            if (inputCtrl.isUpPressed()) dy += 1;
            if (inputCtrl.isDownPressed()) dy -= 1;

            float length = (float) Math.sqrt(dx * dx + dy * dy);
            if (length > 0) {
                dx /= length;
                dy /= length;
            }

            float targetSpeedX = dx * MAX_SPEED;
            float targetSpeedY = dy * MAX_SPEED;

            speedX = approach(speedX, targetSpeedX, MAX_ACCELERATION * delta);
            speedY = approach(speedY, targetSpeedY, MAX_ACCELERATION * delta);

            speed = (float) Math.sqrt(speedX * speedX + speedY * speedY);
            if (speed > 0) {
                float targetRotation = (float) Math.toDegrees(Math.atan2(speedY, speedX)) + 90;

                rotation = normalizeAngle(targetRotation);
                if (inputCtrl.isDriftPressed()) {
                    rotationSpeed = 15;
                    if (inputCtrl.isLeftPressed()) {
                        targetRotation -= 45;
                    } else if (inputCtrl.isRightPressed()) {
                        targetRotation += 45;
                    }
                } else {
                    rotationSpeed = 5;
                }

                float rotationDifference = normalizeAngle(targetRotation - rotation);
                if (rotationDifference > 180) {
                    rotationDifference -= 360;
                }
                rotation += rotationDifference * rotationSpeed * delta;
                rotation = normalizeAngle(rotation);
            }
        }


        private float approach(float current, float target, float maxChange) {
            float change = target - current;
            if (change > maxChange) change = maxChange;
            else if (change < -maxChange) change = -maxChange;
            return current + change;
        }

        private final List<PlayerObserver> observers = new ArrayList<>();

        public void addObserver(PlayerObserver observer) {
            observers.add(observer);
        }

        public void removeObserver(PlayerObserver observer) {
            observers.remove(observer);
        }

        private void notifyObservers(float oldX , float oldY,float newX, float newY) {
            for (PlayerObserver observer : observers) {
                observer.onPlayerPositionChanged(oldX,oldY,newX, newY);
            }
        }
    }
