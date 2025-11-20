package senai.miguelmassane.jogo;

import java.awt.*;

public class Tiro {
    private int x, y;
    private int velocidade = 7;
    private Image imagem;

    public Tiro(int x, int y) {
        this.x = x;
        this.y = y;
        carregarImagem();
    }

    private void carregarImagem() {
        imagem = CarregadorImagens.carregarImagem("tiro.png", 5, 15);
        if (imagem == null) {
            System.out.println("Imagem do tiro não encontrada. Usando gráficos básicos.");
        }
    }

    public void mover() {
        y -= velocidade;
    }

    public void desenhar(Graphics g) {
        if (imagem != null) {
            g.drawImage(imagem, x, y, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, 3, 10);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 15);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
