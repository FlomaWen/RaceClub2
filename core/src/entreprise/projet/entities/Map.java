package entreprise.projet.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Map implements Drivable {

    private boolean[][] walkable;
    private Pixmap pixmap;

    public Map(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        pixmap = texture.getTextureData().consumePixmap();

        walkable = new boolean[pixmap.getWidth()][pixmap.getHeight()];
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                int pixel = pixmap.getPixel(x, y);
                walkable[x][y] = !isGreen(pixel) && !isGray(pixel);
            }
        }
        texture.dispose();
    }

    private boolean isGreen(int pixel) {

        Color color = new Color(pixel);
        float redGreen = 37 / 255f;
        float greenGreen = 203 / 255f;
        float blueGreen = 13 / 255f;
        return color.r == redGreen && color.g == greenGreen && color.b == blueGreen && color.a == 1;
    }

    private boolean isGray(int pixel){
        Color color = new Color(pixel);
        float redGray = 189/255f;
        float greenGray = 190/255f;
        float blueGray = 189/255f;
        return color.r == redGray && color.g == greenGray && color.b == blueGray && color.a == 1;
    }

    private boolean isWhite(int pixel){
        Color color = new Color(pixel);
        float red = 1;
        float green = 1;
        float blue = 1;
        return color.r == red && color.g == green && color.b == blue && color.a == 1;
    }

    private boolean isRed(int pixel){
        Color color = new Color(pixel);
        float red = 1;
        float green = 0;
        float blue = 0;
        return color.r == red && color.g == green && color.b == blue && color.a == 1;
    }

    private final static float STARTX = 495f;
    private final static float STARTY = 897f;
    private final static float LAP_HEIGHT = 100f;


    @Override
    public int getLapState(float x0, float y0, float x, float y) {
        float avgy = (y+y0)/2f;
        if(STARTY < avgy && avgy < STARTY+LAP_HEIGHT && STARTX-x > 0 && STARTX-x0 <=0) {
            return 1;
        } else if (STARTY < avgy && avgy < STARTY+LAP_HEIGHT && STARTX-x < 0 && STARTX-x0 >=0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isDrivable(int x, int y) {
        return x >= 0 && y >= 0 && x < walkable.length && y < walkable[0].length && walkable[x][pixmap.getHeight() - 1 - y];
    }

    public boolean isWhite(int x, int y) {
        return x >= 0 && y >= 0 && x < pixmap.getWidth() && y < pixmap.getHeight() && isWhite(pixmap.getPixel(x, pixmap.getHeight() - 1 - y));
    }

    public boolean isRed(int x, int y) {
        return x >= 0 && y >= 0 && x < pixmap.getWidth() && y < pixmap.getHeight() && isRed(pixmap.getPixel(x, pixmap.getHeight() - 1 - y));
    }

    public void dispose() {
        pixmap.dispose();
    }


    // player observer a utiliser


}
