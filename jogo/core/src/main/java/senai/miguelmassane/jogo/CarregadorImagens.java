package senai.miguelmassane.jogo;

import javax.swing.*;
import java.awt.*;

public class CarregadorImagens {

    public static Image carregarImagem(String nomeArquivo) {
        try {
            // Tenta carregar do classpath (pasta assets)
            ImageIcon icon = new ImageIcon(CarregadorImagens.class.getResource("/" + nomeArquivo));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                return icon.getImage();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível carregar: " + nomeArquivo);
        }
        return null;
    }

    public static Image carregarImagem(String nomeArquivo, int largura, int altura) {
        Image imagem = carregarImagem(nomeArquivo);
        if (imagem != null) {
            return imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        }
        return null;
    }
}
