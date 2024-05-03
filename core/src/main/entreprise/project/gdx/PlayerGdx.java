package entreprise.project.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entreprise.project.entities.Player;

public class PlayerGdx {

    public final Texture img;
    private final Color tint;
    private final BitmapFont font = new BitmapFont();
    private final Player player;

    public PlayerGdx(Player player, Color tint) {
        img = new Texture("voituretest.png");
        this.tint = tint;
        this.player = player;
    }

    public void dispose() {
        img.dispose();
        font.dispose();
    }

    public Player getPlayer() {
        return this.player;
    }

    public void draw(SpriteBatch batch) {
        Color originalColor = batch.getColor();
        batch.setColor(tint);
        float originX = img.getWidth() / 2f;
        float originY = img.getHeight() / 2f;
        batch.draw(
                img,
                this.player.getX() - originX,
                this.player.getY() - originY,
                originX, originY, img.getWidth(), img.getHeight(),
                1, 1, this.player.getRotation(),
                0, 0, img.getWidth(), img.getHeight(),
                false, false
        );
        font.draw(batch, String.format("Speed : %.2g", this.player.getSpeed()), 10, 10);
        batch.setColor(originalColor);

    }
}
