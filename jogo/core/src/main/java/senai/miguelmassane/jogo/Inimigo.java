package senai.miguelmassane.jogo;

import java.awt.*;

public class Inimigo {
    private int x, y;
    private int velocidade;
    private int tipo;
    private Color cor;
    private Image imagem;

    public Inimigo(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        carregarImagem();

        switch (tipo) {
            case 0:
                velocidade = 2;
                cor = Color.RED;
                break;
            case 1:
                velocidade = 3;
                cor = Color.ORANGE;
                break;
            case 2:
                velocidade = 4;
                cor = Color.YELLOW;
                break;
            default:
                velocidade = 2;
                cor = Color.RED;
        }
    }

    private void carregarImagem() {
        String nomeArquivo = "";
        switch (tipo) {
            case 0: nomeArquivo = "inimigo_vermelho.png"; break;
            case 1: nomeArquivo = "inimigo_laranja.png"; break;
            case 2: nomeArquivo = "inimigo_amarelo.png"; break;
        }

        imagem = CarregadorImagens.carregarImagem(nomeArquivo, 40, 30);
        if (imagem == null) {
            System.out.println("Imagem do inimigo não encontrada: " + nomeArquivo);
        }
    }

    public void mover() {
        y += velocidade;
    }

    public void desenhar(Graphics g) {
        if (imagem != null) {
            g.drawImage(imagem, x, y, null);
        } else {
            g.setColor(cor);
            int[] xPoints = {x, x + 20, x + 40};
            int[] yPoints = {y + 30, y, y + 30};
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 30);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTipo() { return tipo; }
}
