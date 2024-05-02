package entreprise.projet.input;

import com.badlogic.gdx.Gdx;

public class KeyboardController implements InputController{
    private int[] keys;
    public static final int DRIFT = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;


    public KeyboardController(int [] keys) {
        this.keys = keys.clone();
    }
    @Override
    public boolean isDriftPressed() {
        return Gdx.input.isKeyPressed(this.keys[DRIFT]);
    }

    @Override
    public boolean isLeftPressed() {
        return false;
    }

    @Override
    public boolean isRightPressed() {
        return false;
    }

    @Override
    public boolean isUpPressed() {
        return false;
    }

    @Override
    public boolean isDownPressed() {
        return false;
    }
}
