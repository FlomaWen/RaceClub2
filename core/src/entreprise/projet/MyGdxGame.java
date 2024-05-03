package entreprise.projet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import entreprise.projet.entities.Map;
import entreprise.projet.entities.Player;
import entreprise.projet.input.KeyboardController;
import entreprise.projet.input.MyInputProcessor;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	static final Color[] carColors = new Color[]{
			Color.WHITE,
			Color.YELLOW
	};
	static final int[][] carInputs = new int[][]{
			{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.CONTROL_RIGHT},
			{Input.Keys.Q, Input.Keys.D, Input.Keys.Z, Input.Keys.S, Input.Keys.SHIFT_LEFT}
	};

	Player [] cars;
	Player car2;
	Texture background;
	Texture backgroundTexture;
	Map map;
	BitmapFont font;



	@Override
		public void create() {

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		map = new Map("map.png");
		cars = new Player[carColors.length];
		for(int i =0; i<cars.length; i++) {
			cars[i] = new Player(
					map,
					new KeyboardController(carInputs[i]),
					550,925+25*i,
					"voituretest.png",
					carColors[i]
			);
		}

		backgroundTexture = new Texture("map.png");
		Gdx.input.setInputProcessor(new MyInputProcessor(cars[0], cars[1],map));

		}

		@Override
		public void render() {
			float delta = Gdx.graphics.getDeltaTime();
			ScreenUtils.clear(1, 0, 0, 1);
			batch.setColor(Color.WHITE);
			batch.begin();

			batch.draw(backgroundTexture, 0, 0);
			for(Player c : cars) {
				c.update(delta);
				c.draw(batch);
			}

			// Display the points on the screen
			for(int i = 0; i < cars.length; i++) {
				font.draw(batch, "Car " + (i+1) + " Points: " + cars[i].getPoints(), 10, 460 - i * 20);
			}

			batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		for(Player car : cars) {
			car.img.dispose();
		}
		backgroundTexture.dispose();
		map.dispose();
	}


}
