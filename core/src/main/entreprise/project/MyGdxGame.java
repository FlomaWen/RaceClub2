package entreprise.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import entreprise.project.entities.Map;
import entreprise.project.entities.Player;
import entreprise.project.input.KeyboardController;
import entreprise.project.input.MyInputProcessor;
import entreprise.project.gdx.PlayerGdx;



public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	static final Color[] carColors = new Color[]{
			Color.WHITE,
			Color.YELLOW
	};
	static final int[][] carInputs = new int[][]{
			{Input.Keys.CONTROL_RIGHT, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN},
			{Input.Keys.SHIFT_LEFT, Input.Keys.Q, Input.Keys.D, Input.Keys.Z, Input.Keys.S}
	};

	PlayerGdx[] cars;
	Texture backgroundTexture;
	Map map;
	BitmapFont font;



	@Override
		public void create() {

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		map = new Map("map.png");
		cars = new PlayerGdx[carColors.length];
		for(int i =0; i<cars.length; i++) {
			cars[i] = new PlayerGdx(
					new Player(
						map,
						new KeyboardController(carInputs[i]),
						550,925+25*i
					), carColors[i]
			);
		}

		backgroundTexture = new Texture("map.png");
		Gdx.input.setInputProcessor(new MyInputProcessor(cars[0].getPlayer(), cars[1].getPlayer(),map));

		}

		@Override
		public void render() {
			float delta = Gdx.graphics.getDeltaTime();
			ScreenUtils.clear(1, 0, 0, 1);
			batch.setColor(Color.WHITE);
			batch.begin();

			batch.draw(backgroundTexture, 0, 0);
			for(PlayerGdx c : cars) {
				c.getPlayer().update(delta);
				c.draw(batch);
			}

			// Display the points on the screen
			for(int i = 0; i < cars.length; i++) {
				font.draw(batch, "Car " + (i+1) + " Points: " + cars[i].getPlayer().getPoints(), 10, 460 - i * 20);
			}

			batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		for(PlayerGdx car : cars) {
			car.dispose();
		}
		backgroundTexture.dispose();
		map.dispose();
	}


}
