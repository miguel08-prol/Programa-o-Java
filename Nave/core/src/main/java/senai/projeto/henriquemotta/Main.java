
package senai.projeto.henriquemotta;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture planoDeFundoTexture;
    Texture carrinhoTexture;
    Texture carrinhosCaindoTexture;

    Sound somCarroBatendo;
    Music musica;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Sprite carrinhoSprite;

    Vector2 touchPos;

    Array<Sprite> carrinhosSprites;

    Rectangle hitboxCarrinho;
    Rectangle hitboxcarrinhosCaindo;
    float cairTime;

    @Override
    public void create (){
        planoDeFundoTexture = new Texture("background.png");

        viewport = new FitViewport(8,5);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }
}

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
    }
