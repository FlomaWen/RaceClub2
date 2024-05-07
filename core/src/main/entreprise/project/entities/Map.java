package entreprise.project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Map implements Drivable {
    public final static int GRASS = 0x25cb0d;

    public final static int WALL = 0xbdbebd;

    public final static int KERBS0 = 0xffffff;
    public final static int KERBS1 = 0xff0000;


    private final boolean[][] walkable;
    private final Pixmap pixmap;

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
                walkable[x][y] = !isColor(pixel, GRASS)
                              && !isColor(pixel, WALL);
            }
        }
        texture.dispose();
    }


    private static boolean isColor(int pixel, int compareTo){
        Color color = new Color(pixel);
        float r = ((compareTo >> 16) & 0xff)/255f;
        float g = ((compareTo >>  8) & 0xff)/255f;
        float b = (compareTo & 0xff)/255f;
        return color.r == r && color.g == g && color.b == b && color.a == 1;
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

    public boolean isKerb(int x, int y) {
        int pixel = pixmap.getPixel(x, pixmap.getHeight() - 1 - y);
        return x >= 0 && y >= 0 && x < pixmap.getWidth() && y < pixmap.getHeight()
                && (isColor(pixel, KERBS0) || isColor(pixel, KERBS1));
    }

    public void dispose() {
        pixmap.dispose();
    }

    // player observer a utiliser


}
