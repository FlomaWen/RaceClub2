package entreprise.projet.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import entreprise.projet.entities.Map;
import entreprise.projet.entities.Player;

public class MyInputProcessor implements InputProcessor {
    private Player car1;
    private Player car2;
    Map map;


    public MyInputProcessor(Player car1, Player car2, Map map) {
        this.car1 = car1;
        this.car2 = car2;

    }


    public boolean resetGame(int keycode){
        if (keycode == Input.Keys.R){
            car1 = new Player(map,550,950,"voituretest.png", Color.WHITE);
            car2 = new Player(map,550,925,"voituretest.png", Color.YELLOW);
        }
        return true;
    }



    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                car1.setLeftPressed(true);
                break;
            case Input.Keys.RIGHT:
                car1.setRightPressed(true);
                break;
            case Input.Keys.UP:
                car1.setUpPressed(true);
                break;
            case Input.Keys.DOWN:
                car1.setDownPressed(true);
                break;
            case Input.Keys.CONTROL_RIGHT:
                car1.startDrift();
                break;
            case Input.Keys.A:
                car2.setLeftPressed(true);
                break;
            case Input.Keys.D:
                car2.setRightPressed(true);
                break;
            case Input.Keys.W:
                car2.setUpPressed(true);
                break;
            case Input.Keys.S:
                car2.setDownPressed(true);
                break;
            case Input.Keys.SHIFT_LEFT:
                car2.startDrift();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                car1.setLeftPressed(false);
                break;
            case Input.Keys.RIGHT:
                car1.setRightPressed(false);
                break;
            case Input.Keys.UP:
                car1.setUpPressed(false);
                break;
            case Input.Keys.DOWN:
                car1.setDownPressed(false);
                break;
            case Input.Keys.CONTROL_RIGHT:
                car1.stopDrift();
                break;
            case Input.Keys.A:
                car2.setLeftPressed(false);
                break;
            case Input.Keys.D:
                car2.setRightPressed(false);
                break;
            case Input.Keys.W:
                car2.setUpPressed(false);
                break;
            case Input.Keys.S:
                car2.setDownPressed(false);
                break;
            case Input.Keys.SHIFT_LEFT:
                car2.stopDrift();
                break;

        }
        return true;
    }






    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}