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
			{Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.CONTROL_RIGHT}
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
		Gdx.input.setInputProcessor(new MyInputProcessor(car1,car2,map));

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
			font.draw(batch, "Car 1 Points: " + car1.getPoints(), 10, 460); // Show Car 1 points
			font.draw(batch, "Car 2 Points: " + car2.getPoints(), 10, 440); // Show Car 2 points

			batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		car1.img.dispose();
		car2.img.dispose();
		backgroundTexture.dispose();
		map.dispose();
	}


}
