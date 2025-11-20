package senai.miguelmassane.jogo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class GeradorTexturas {

    public static Texture criarAviao() {
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CLEAR);
        pixmap.fill();
        pixmap.setColor(Color.CYAN);
        pixmap.fillTriangle(32, 0, 0, 64, 64, 64);
        pixmap.setColor(Color.BLUE);
        pixmap.fillRectangle(28, 10, 8, 40);
        Texture t = new Texture(pixmap);
        pixmap.dispose();
        return t;
    }

    public static Texture criarInimigo() {
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CLEAR);
        pixmap.fill();
        pixmap.setColor(Color.RED);
        pixmap.fillTriangle(0, 0, 64, 0, 32, 64);
        pixmap.setColor(Color.ORANGE);
        pixmap.fillCircle(32, 20, 10);
        Texture t = new Texture(pixmap);
        pixmap.dispose();
        return t;
    }

    public static Texture criarTiro() {
        Pixmap pixmap = new Pixmap(8, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.YELLOW);
        pixmap.fill();
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(2, 2, 4, 16);
        Texture t = new Texture(pixmap);
        pixmap.dispose();
        return t;
    }

    public static Texture criarFundo() {
        Pixmap pixmap = new Pixmap(800, 600, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        pixmap.setColor(Color.WHITE);
        for(int i=0; i<100; i++) {
            pixmap.drawPixel((int)(Math.random()*800), (int)(Math.random()*600));
        }
        Texture t = new Texture(pixmap);
        pixmap.dispose();
        return t;
    }
}
