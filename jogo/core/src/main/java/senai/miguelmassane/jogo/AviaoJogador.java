package senai.miguelmassane.jogo;

import java.awt.*;

public class AviaoJogador {
    private int x, y;
    private int velocidade = 5;
    private boolean movendoEsquerda, movendoDireita, movendoCima, movendoBaixo;
    private int vidas;
    private Image imagem;

    public AviaoJogador(int x, int y) {
        this.x = x;
        this.y = y;
        this.vidas = 3;
        carregarImagem();
    }

    private void carregarImagem() {
        imagem = CarregadorImagens.carregarImagem("aviao_jogador.png", 40, 30);
        if (imagem == null) {
            System.out.println("Imagem do avião não encontrada. Usando gráficos básicos.");
        }
    }

    public void mover() {
        if (movendoEsquerda && x > 0) x -= velocidade;
        if (movendoDireita && x < 750) x += velocidade;
        if (movendoCima && y > 0) y -= velocidade;
        if (movendoBaixo && y < 550) y += velocidade;
    }

    public void desenhar(Graphics g) {
        if (imagem != null) {
            g.drawImage(imagem, x, y, null);
        } else {
            g.setColor(Color.CYAN);
            g.fillRect(x + 15, y, 10, 30);
            g.fillRect(x, y + 10, 40, 10);
            g.fillRect(x + 20, y - 10, 5, 10);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 30);
    }

    public void dano() {
        vidas--;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getVidas() { return vidas; }
    public void setMovendoEsquerda(boolean movendo) { movendoEsquerda = movendo; }
    public void setMovendoDireita(boolean movendo) { movendoDireita = movendo; }
    public void setMovendoCima(boolean movendo) { movendoCima = movendo; }
    public void setMovendoBaixo(boolean movendo) { movendoBaixo = movendo; }
}
