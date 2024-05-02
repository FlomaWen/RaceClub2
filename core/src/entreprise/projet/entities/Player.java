    package entreprise.projet.entities;

    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.Color;
    import entreprise.projet.input.InputController;

    import java.util.ArrayList;
    import java.util.List;

    public class Player {
        public Texture img;
        private float x, y, speedX, speedY, rotation, acceleration, maxSpeed, rotationSpeed, speed;
        private Map map;
        //private boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false,isMoving,isDrifting;
        private boolean isMoving = false;
        private InputController inputCtrl;
        private BitmapFont font = new BitmapFont();
        private float driftFactor = 0.75f;
        private Color tint;
        private int points;

        public Player(Map map, InputController inputCtrl, float startX, float startY, String texturePath, Color tint) {
            img = new Texture("voituretest.png");
            x = startX;
            y = startY;
            rotation = 270;
            this.inputCtrl = inputCtrl;
            this.map = map;
            this.tint = tint;
            maxSpeed = 300;
            acceleration = maxSpeed / 1.5f;
            rotationSpeed = 5;
            speed = 0;
        }

        public void update(float delta) {
            updateSpeed(delta);

            float rad = (float) Math.toRadians(rotation);
            float driftSpeedX = speed * (float) Math.cos(rad - Math.PI / 2);
            float driftSpeedY = speed * (float) Math.sin(rad - Math.PI / 2);

            if (inputCtrl.isDriftPressed()) {
                speedX = speedX * driftFactor + driftSpeedX * (1 - driftFactor);
                speedY = speedY * driftFactor + driftSpeedY * (1 - driftFactor);
            } else {
                speedX = driftSpeedX;
                speedY = driftSpeedY;
            }

            float newX = x + speedX * delta;
            float newY = y + speedY * delta;

            if (map.isWalkable((int) newX, (int) newY)) {
                if(map.isLapComplete(x, y, newX, newY) == 1) {
                    points++;
                } else if (map.isLapComplete(x, y, newX, newY) == -1) {
                    points--;
                }
                x = newX;
                y = newY;

                notifyObservers(x, y);
            } else {
                speed = 0;
                isMoving = false;
            }

        }

        public int getPoints() {
            return points;
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

            float targetSpeedX = dx * maxSpeed;
            float targetSpeedY = dy * maxSpeed;

            speedX = approach(speedX, targetSpeedX, acceleration * delta);
            speedY = approach(speedY, targetSpeedY, acceleration * delta);

            speed = (float) Math.sqrt(speedX * speedX + speedY * speedY);
            if (speed > 0) {
                rotation = (float) Math.toDegrees(Math.atan2(speedY, speedX)) + 90;
                rotation = normalizeAngle(rotation);
                isMoving = true;
            } else {
                isMoving = false;
            }
            if (speed > 0) {
                float targetRotation = (float) Math.toDegrees(Math.atan2(speedY, speedX)) + 90;

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
            } else {
                isMoving = false;
            }


        }

        private float approach(float current, float target, float maxChange) {
            float change = target - current;
            if (change > maxChange) change = maxChange;
            else if (change < -maxChange) change = -maxChange;
            return current + change;
        }

        public void draw(SpriteBatch batch) {
            Color originalColor = batch.getColor();
            batch.setColor(tint);
            float originX = img.getWidth() / 2f;
            float originY = img.getHeight() / 2f;
            batch.draw(img, x - originX, y - originY, originX, originY, img.getWidth(), img.getHeight(), 1, 1, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
            font.draw(batch, String.format("Speed : %.2g", speed), 10, 10);
            batch.setColor(originalColor);

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
