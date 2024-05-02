package entreprise.projet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Player car1;
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
		car1 = new Player(map,550,950, "voituretest.png",Color.WHITE);
		car2 = new Player(map,550,925, "voituretest.png",Color.YELLOW);

		backgroundTexture = new Texture("map.png");
		Gdx.input.setInputProcessor(new MyInputProcessor(car1,car2,map));

		}

		@Override
		public void render() {
			ScreenUtils.clear(1, 0, 0, 1);
			batch.setColor(Color.WHITE);
			batch.begin();

			batch.draw(backgroundTexture, 0, 0);
			car1.update(Gdx.graphics.getDeltaTime());
			car1.draw(batch);
			car2.update(Gdx.graphics.getDeltaTime());
			car2.draw(batch);


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
