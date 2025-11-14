class PainelJogo extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private AviãoJogador aviaoJogador;
    private ArrayList<Inimigo> inimigos;
    private ArrayList<Tiro> tiros;
    private Random random;
    private int pontuacao;
    private boolean gameOver;
    private int nivel;
    private int inimigosDestruidos;

    public PainelJogo() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        aviaoJogador = new AviãoJogador(400, 500);
        inimigos = new ArrayList<>();
        tiros = new ArrayList<>();
        random = new Random();
        pontuacao = 0;
        gameOver = false;
        nivel = 1;
        inimigosDestruidos = 0;

        timer = new Timer(16, this); // ≈60 FPS
        timer.start();

        // Inicia com alguns inimigos
        for (int i = 0; i < 5; i++) {
            adicionarInimigo();
        }
    }

    private void adicionarInimigo() {
        int x = random.nextInt(700) + 50;
        int y = random.nextInt(200) - 300; // Começa acima da tela
        int tipo = random.nextInt(3); // Diferentes tipos de inimigos
        inimigos.add(new Inimigo(x, y, tipo));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha fundo estrelado
        desenharFundo(g);

        if (!gameOver) {
            // Desenha avião do jogador
            aviaoJogador.desenhar(g);

            // Desenha inimigos
            for (Inimigo inimigo : inimigos) {
                inimigo.desenhar(g);
            }

            // Desenha tiros
            for (Tiro tiro : tiros) {
                tiro.desenhar(g);
            }

            // Desenha HUD
            desenharHUD(g);
        } else {
            desenharGameOver(g);
        }
    }

    private void desenharFundo(Graphics g) {
        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(800);
            int y = random.nextInt(600);
            g.fillRect(x, y, 2, 2);
        }
    }

    private void desenharHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Pontuação: " + pontuacao, 20, 30);
        g.drawString("Nível: " + nivel, 20, 60);
        g.drawString("Vidas: " + aviaoJogador.getVidas(), 20, 90);
    }

    private void desenharGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("GAME OVER", 250, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Pontuação Final: " + pontuacao, 300, 300);
        g.drawString("Pressione R para reiniciar", 270, 350);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            atualizarJogo();
        }
        repaint();
    }

    private void atualizarJogo() {
        // Move avião do jogador
        aviaoJogador.mover();

        // Move inimigos
        for (int i = inimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = inimigos.get(i);
            inimigo.mover();

            // Verifica colisão com jogador
            if (inimigo.getBounds().intersects(aviaoJogador.getBounds())) {
                inimigos.remove(i);
                aviaoJogador.dano();
                if (aviaoJogador.getVidas() <= 0) {
                    gameOver = true;
                }
                continue;
            }

            // Remove inimigos que saíram da tela
            if (inimigo.getY() > 600) {
                inimigos.remove(i);
                adicionarInimigo(); // Adiciona novo inimigo
            }
        }

        // Move tiros
        for (int i = tiros.size() - 1; i >= 0; i--) {
            Tiro tiro = tiros.get(i);
            tiro.mover();

            // Remove tiros que saíram da tela
            if (tiro.getY() < 0) {
                tiros.remove(i);
                continue;
            }

            // Verifica colisão com inimigos
            for (int j = inimigos.size() - 1; j >= 0; j--) {
                Inimigo inimigo = inimigos.get(j);
                if (tiro.getBounds().intersects(inimigo.getBounds())) {
                    tiros.remove(i);
                    inimigos.remove(j);
                    pontuacao += 10 * (inimigo.getTipo() + 1);
                    inimigosDestruidos++;
                    adicionarInimigo();

                    // Aumenta nível a cada 10 inimigos destruídos
                    if (inimigosDestruidos % 10 == 0) {
                        nivel++;
                    }
                    break;
                }
            }
        }

        // Adiciona novos inimigos ocasionalmente
        if (random.nextInt(100) < 2) { // 2% de chance a cada frame
            adicionarInimigo();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                reiniciarJogo();
            }
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                aviaoJogador.setMovendoEsquerda(true);
                break;
            case KeyEvent.VK_RIGHT:
                aviaoJogador.setMovendoDireita(true);
                break;
            case KeyEvent.VK_UP:
                aviaoJogador.setMovendoCima(true);
                break;
            case KeyEvent.VK_DOWN:
                aviaoJogador.setMovendoBaixo(true);
                break;
            case KeyEvent.VK_SPACE:
                tiros.add(new Tiro(aviaoJogador.getX() + 20, aviaoJogador.getY()));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                aviaoJogador.setMovendoEsquerda(false);
                break;
            case KeyEvent.VK_RIGHT:
                aviaoJogador.setMovendoDireita(false);
                break;
            case KeyEvent.VK_UP:
                aviaoJogador.setMovendoCima(false);
                break;
            case KeyEvent.VK_DOWN:
                aviaoJogador.setMovendoBaixo(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void reiniciarJogo() {
        aviaoJogador = new AviãoJogador(400, 500);
        inimigos.clear();
        tiros.clear();
        pontuacao = 0;
        gameOver = false;
        nivel = 1;
        inimigosDestruidos = 0;

        for (int i = 0; i < 5; i++) {
            adicionarInimigo();
        }
    }
}
