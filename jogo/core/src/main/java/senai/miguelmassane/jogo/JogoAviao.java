package senai.miguelmassane.jogo;

import javax.swing.*;

public class JogoAviao extends JFrame {
    private static final int LARGURA = 800;
    private static final int ALTURA = 600;
    private PainelJogo painelJogo;

    public JogoAviao() {
        setTitle("Batalha de Aviões");
        setSize(LARGURA, ALTURA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        painelJogo = new PainelJogo();
        add(painelJogo);

        setVisible(true);
    }
}
