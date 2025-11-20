package senai.miguelmassane.jogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.Iterator;

public class TelaJogo implements Screen {
    final Main game;
    private OrthographicCamera camera;

    // ESTADOS DO JOGO
    private static final int ESTADO_JOGANDO = 0;
    private static final int ESTADO_POUSANDO = 1;
    private static final int ESTADO_LOJA = 2;
    private static final int ESTADO_DECOLANDO = 3;
    private int estadoAtual = ESTADO_JOGANDO;

    // Dimensões
    private static final int LARGURA_TELA = 800;
    private static final int ALTURA_TELA = 600;

    // Texturas
    private Texture imgAviao, imgInimigo, imgTiroJog, imgTiroInim;
    private Texture imgShield, imgExplosao, imgPowerUp, imgPlaneta;
    private Texture imgSuperficie;

    // Objetos
    private Rectangle jogador;
    private float escalaNave = 1.0f;
    private float rotacaoNave = 0f;
    private Array<Rectangle> tirosJogador;
    private Array<Rectangle> tirosInimigo;
    private Array<Rectangle> inimigos;
    private Array<Rectangle> powerups;
    private Rectangle planetaBase;

    // Efeitos Visuais
    private float alphaFade = 0;
    private float zoomCamera = 1.0f;

    // Cenário
    private Array<Estrela> estrelas;
    private static final int NUM_ESTRELAS = 250;
    private float velocidadeTurbo = 0;

    // Variáveis de Jogo
    private float velocidadeLateral = 500f;
    private long intervaloTiro = 200000000;
    private float dificuldadeInimigo = 1.0f;

    // Tempos
    private long ultimoTiroTime;
    private long ultimoInimigoTime;
    private long ultimoPlanetaTime;

    // Status
    private int vidas = 3;
    private int pontuacao = 0;
    private int navesDestruidas = 0;
    private boolean gameOver = false;

    // Escudo
    private boolean shieldAtivo = false;
    private float energiaShield = 100f;
    private final float CONSUMO_SHIELD = 40f;
    private final float RECARGA_SHIELD = 10f;

    // Efeitos
    private ShapeRenderer shapeRenderer;
    private Array<Explosao> explosoes;

    public TelaJogo(final Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, LARGURA_TELA, ALTURA_TELA);

        // Carregar Imagens
        imgAviao = carregarOuCriar("player_ship.png", Color.CYAN);
        imgInimigo = carregarOuCriar("enemy_ship.png", Color.MAGENTA);
        imgTiroJog = carregarOuCriar("laser_blue.png", Color.YELLOW);
        imgTiroInim = carregarOuCriar("laser_red.png", Color.RED);
        imgShield = carregarOuCriar("shield_barrier.png", Color.BLUE);
        imgExplosao = carregarOuCriar("explosion.png", Color.ORANGE);
        imgPowerUp = carregarOuCriar("powerup_repair.png", Color.GREEN);
        imgPlaneta = carregarOuCriar("planet_base.png", Color.TEAL);
        imgSuperficie = carregarOuCriar("planet_surface.png", Color.GRAY);

        tirosJogador = new Array<>();
        tirosInimigo = new Array<>();
        inimigos = new Array<>();
        powerups = new Array<>();
        explosoes = new Array<>();
        shapeRenderer = new ShapeRenderer();

        estrelas = new Array<>();
        for (int i = 0; i < NUM_ESTRELAS; i++) {
            estrelas.add(new Estrela(LARGURA_TELA, ALTURA_TELA));
        }

        jogador = new Rectangle();
        jogador.width = 64; jogador.height = 64;
        jogador.x = LARGURA_TELA / 2 - 32;
        jogador.y = 50;

        spawnInimigo();
        ultimoPlanetaTime = TimeUtils.nanoTime();
    }

    private Texture carregarOuCriar(String nome, Color corErro) {
        try { return new Texture(nome); }
        catch (Exception e) {
            Pixmap p = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
            p.setColor(corErro); p.fill(); return new Texture(p);
        }
    }

    private void spawnInimigo() {
        Rectangle inimigo = new Rectangle();
        inimigo.width = 64; inimigo.height = 64;
        inimigo.x = MathUtils.random(0, LARGURA_TELA - 64);
        inimigo.y = ALTURA_TELA;
        inimigos.add(inimigo);
        ultimoInimigoTime = TimeUtils.nanoTime();
    }

    private void spawnPowerUp(float x, float y) {
        Rectangle powerup = new Rectangle();
        powerup.x = x; powerup.y = y; powerup.width = 32; powerup.height = 32;
        powerups.add(powerup);
    }

    private void spawnPlaneta() {
        planetaBase = new Rectangle();
        planetaBase.width = 200;
        planetaBase.height = 200;
        planetaBase.x = MathUtils.random(100, LARGURA_TELA - 300);
        planetaBase.y = ALTURA_TELA;
        ultimoPlanetaTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        camera.zoom = zoomCamera;
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        switch (estadoAtual) {
            case ESTADO_JOGANDO:
                atualizarJogo(delta);
                desenharEspaco(delta);
                break;
            case ESTADO_POUSANDO:
                animacaoPouso(delta);
                desenharEspaco(delta);
                break;
            case ESTADO_LOJA:
                atualizarLoja();
                desenharLoja();
                break;
            case ESTADO_DECOLANDO:
                animacaoDecolagem(delta);
                desenharEspaco(delta);
                break;
        }

        if (alphaFade > 0) {
            Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, alphaFade);
            shapeRenderer.rect(camera.position.x - LARGURA_TELA, camera.position.y - ALTURA_TELA, LARGURA_TELA * 2, ALTURA_TELA * 2);
            shapeRenderer.end();
        }
    }

    // ---------------------------------------------------
    //  ANIMAÇÕES
    // ---------------------------------------------------

    private void animacaoPouso(float delta) {
        float alvoX = planetaBase.x + planetaBase.width/2 - jogador.width/2;
        float alvoY = planetaBase.y + planetaBase.height/2 - jogador.height/2;

        jogador.x += (alvoX - jogador.x) * 3 * delta;
        jogador.y += (alvoY - jogador.y) * 3 * delta;

        escalaNave -= 0.5f * delta;
        rotacaoNave += 90 * delta;

        if (escalaNave < 0.1f) {
            alphaFade += 2 * delta;
            if (alphaFade >= 1.0f) {
                estadoAtual = ESTADO_LOJA;
                alphaFade = 1.0f;
                jogador.x = LARGURA_TELA/2 - 32;
                jogador.y = 150;
                escalaNave = 2.0f;
                rotacaoNave = 0;
                zoomCamera = 1.0f;
                camera.position.set(LARGURA_TELA/2, ALTURA_TELA/2, 0);
            }
        } else {
            zoomCamera -= 0.2f * delta;
            if(zoomCamera < 0.5f) zoomCamera = 0.5f;
            camera.position.set(jogador.x + 32, jogador.y + 32, 0);
        }
    }

    private void animacaoDecolagem(float delta) {
        alphaFade -= 1.0f * delta;
        jogador.y += 300 * delta;
        escalaNave = 1.0f;
        rotacaoNave = 0;

        if (alphaFade <= 0) {
            alphaFade = 0;
            estadoAtual = ESTADO_JOGANDO;
            planetaBase = null;
            jogador.y = 50;
            jogador.x = LARGURA_TELA/2 - 32;
            inimigos.clear();
            tirosInimigo.clear();
            camera.position.set(LARGURA_TELA/2, ALTURA_TELA/2, 0);
        }
    }

    private void atualizarLoja() {
        if (alphaFade > 0) alphaFade -= Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) && pontuacao >= 1000) { pontuacao -= 1000; vidas++; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) && pontuacao >= 2000) {
            pontuacao -= 2000; intervaloTiro -= 50000000; if(intervaloTiro < 50000000) intervaloTiro = 50000000;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) && pontuacao >= 1500) { pontuacao -= 1500; velocidadeLateral += 100f; }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            alphaFade = 1.0f;
            estadoAtual = ESTADO_DECOLANDO;
        }
    }

    // ---------------------------------------------------
    //  DESENHO
    // ---------------------------------------------------

    private void desenharLoja() {
        game.batch.begin();
        game.batch.draw(imgSuperficie, 0, 0, LARGURA_TELA, ALTURA_TELA);

        // CORREÇÃO DO DESENHO DA NAVE NA LOJA
        game.batch.draw(imgAviao,
            jogador.x, jogador.y,
            32, 32, // Origem (centro da rotação)
            64, 64, // Largura/Altura destino
            escalaNave, escalaNave, // Escala
            0, // Rotação 0 na loja
            0, 0, imgAviao.getWidth(), imgAviao.getHeight(), // Fonte da imagem (CORRIGIDO)
            false, false);

        game.font.getData().setScale(2);
        game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "OFICINA PLANETARIA", 52, ALTURA_TELA - 48);
        game.font.setColor(Color.CYAN);
        game.font.draw(game.batch, "OFICINA PLANETARIA", 50, ALTURA_TELA - 50);

        game.font.getData().setScale(1.2f);
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Creditos: " + pontuacao, 50, ALTURA_TELA - 120);

        game.font.draw(game.batch, "[1] REPARO (+1 Vida)........... 1000", 50, 400);
        game.font.draw(game.batch, "[2] CANHAO DE PLASMA........... 2000", 50, 350);
        game.font.draw(game.batch, "[3] MOTORES TURBO.............. 1500", 50, 300);

        game.font.setColor(Color.LIME);
        game.font.draw(game.batch, "PRESSIONE [ESPACO] PARA DECOLAR", 50, 150);

        game.font.getData().setScale(1);
        game.batch.end();
    }

    private void desenharEspaco(float delta) {
        shapeRenderer.begin(ShapeType.Filled);
        for (Estrela estrela : estrelas) {
            estrela.velocidadeExtra = velocidadeTurbo;
            estrela.update(delta);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(estrela.x, estrela.y, estrela.tamanho);
        }
        shapeRenderer.end();

        game.batch.begin();

        if (planetaBase != null) {
            game.batch.draw(imgPlaneta, planetaBase.x, planetaBase.y, planetaBase.width, planetaBase.height);
        }

        if (estadoAtual != ESTADO_LOJA && !gameOver) {
            // CORREÇÃO DO DESENHO DA NAVE NO JOGO (PARA NÃO FICAR INVISÍVEL)
            game.batch.draw(imgAviao,
                jogador.x, jogador.y,
                32, 32, // Origem no centro
                64, 64, // Tamanho na tela
                escalaNave, escalaNave, // Escala
                rotacaoNave, // Rotação
                0, 0, imgAviao.getWidth(), imgAviao.getHeight(), // Pega a imagem INTEIRA
                false, false);

            if (shieldAtivo && estadoAtual == ESTADO_JOGANDO)
                game.batch.draw(imgShield, jogador.x - 10, jogador.y + 40, 84, 40);
        }

        if (estadoAtual == ESTADO_JOGANDO && !gameOver) {
            for (Rectangle p : powerups) game.batch.draw(imgPowerUp, p.x, p.y, 32, 32);
            for (Rectangle ini : inimigos) game.batch.draw(imgInimigo, ini.x, ini.y, 64, 64);
            for (Rectangle tj : tirosJogador) game.batch.draw(imgTiroJog, tj.x, tj.y, 16, 32);
            for (Rectangle ti : tirosInimigo) game.batch.draw(imgTiroInim, ti.x, ti.y, 16, 32);

            for (Explosao exp : explosoes) {
                game.batch.setColor(1, 1, 1, exp.vida);
                float tamanho = 64 + (1 - exp.vida) * 30;
                game.batch.draw(imgExplosao, exp.x, exp.y, tamanho, tamanho);
            }
            game.batch.setColor(1, 1, 1, 1);
        }
        game.batch.end();

        if (estadoAtual == ESTADO_JOGANDO) {
            // HUD
            game.batch.setProjectionMatrix(camera.combined);

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(20, ALTURA_TELA - 80, 150, 15);
            if (vidas > 0) {
                shapeRenderer.setColor(Color.GREEN);
                float vidaVisual = Math.min(vidas, 10);
                shapeRenderer.rect(20, ALTURA_TELA - 80, (150f / 3f) * vidaVisual, 15);
            }
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(20, ALTURA_TELA - 100, 150, 15);
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(20, ALTURA_TELA - 100, (150f / 100f) * energiaShield, 15);
            shapeRenderer.end();

            game.batch.begin();
            game.font.draw(game.batch, "Pontos: " + pontuacao, 20, ALTURA_TELA - 20);
            game.font.draw(game.batch, "Abatidos: " + navesDestruidas, 20, ALTURA_TELA - 40);
            game.font.draw(game.batch, "VIDA", 25, ALTURA_TELA - 67);
            game.font.draw(game.batch, "ESCUDO", 25, ALTURA_TELA - 87);

            if (gameOver) {
                game.font.getData().setScale(2);
                game.font.draw(game.batch, "GAME OVER", LARGURA_TELA/2 - 100, ALTURA_TELA/2 + 50);
                game.font.getData().setScale(1);
                game.font.draw(game.batch, "Aperte R para Reiniciar", LARGURA_TELA/2 - 100, ALTURA_TELA/2);
            }
            game.batch.end();
        }
    }

    private void atualizarJogo(float delta) {
        if (!gameOver) {
            velocidadeTurbo = 0;

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) jogador.x -= velocidadeLateral * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) jogador.x += velocidadeLateral * delta;

            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocidadeTurbo = 800f;
                if (jogador.y < 150) jogador.y += 50 * delta;
            } else {
                if (jogador.y > 50) jogador.y -= 50 * delta;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                velocidadeTurbo = -100f;
                if (jogador.y > 20) jogador.y -= 100 * delta;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (TimeUtils.nanoTime() - ultimoTiroTime > intervaloTiro) {
                    Rectangle tiro = new Rectangle();
                    tiro.width = 16; tiro.height = 32;
                    tiro.x = jogador.x + 24; tiro.y = jogador.y + 64;
                    tirosJogador.add(tiro);
                    ultimoTiroTime = TimeUtils.nanoTime();
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) shieldAtivo = !shieldAtivo;
            if (shieldAtivo) {
                energiaShield -= CONSUMO_SHIELD * delta;
                if (energiaShield <= 0) { energiaShield = 0; shieldAtivo = false; }
            } else {
                if (energiaShield < 100) energiaShield += RECARGA_SHIELD * delta;
            }

            if (jogador.x < 0) jogador.x = 0;
            if (jogador.x > LARGURA_TELA - 64) jogador.x = LARGURA_TELA - 64;
            if (jogador.y < 10) jogador.y = 10;
            if (jogador.y > 200) jogador.y = 200;

            long tempoSpawn = 1000000000 - (navesDestruidas * 5000000);
            if (tempoSpawn < 300000000) tempoSpawn = 300000000;
            if (velocidadeTurbo > 0) tempoSpawn /= 2.5;

            if (TimeUtils.nanoTime() - ultimoInimigoTime > tempoSpawn) spawnInimigo();

            long tempoParaPlaneta = 20000000000L;
            if (velocidadeTurbo > 0) tempoParaPlaneta = 7000000000L;

            if (planetaBase == null && TimeUtils.nanoTime() - ultimoPlanetaTime > tempoParaPlaneta) {
                spawnPlaneta();
            }

            if (planetaBase != null) {
                planetaBase.y -= (80 + velocidadeTurbo) * delta;

                if (planetaBase.overlaps(jogador)) {
                    estadoAtual = ESTADO_POUSANDO;
                } else if (planetaBase.y + 200 < 0) {
                    planetaBase = null;
                    ultimoPlanetaTime = TimeUtils.nanoTime();
                }
            }

            atualizarEntidades(delta);

        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                vidas = 3; pontuacao = 0; navesDestruidas = 0;
                inimigos.clear(); powerups.clear();
                tirosInimigo.clear(); tirosJogador.clear(); explosoes.clear();
                jogador.x = LARGURA_TELA / 2 - 32; jogador.y = 50;
                gameOver = false;
            }
        }
    }

    private void atualizarEntidades(float delta) {
        for (Iterator<Rectangle> iter = powerups.iterator(); iter.hasNext(); ) {
            Rectangle p = iter.next();
            p.y -= (150 + velocidadeTurbo) * delta;
            if (p.overlaps(jogador)) { vidas++; iter.remove(); }
            else if (p.y + 32 < 0) iter.remove();
        }

        for (Iterator<Rectangle> iter = inimigos.iterator(); iter.hasNext(); ) {
            Rectangle inimigo = iter.next();
            inimigo.y -= ((200 * dificuldadeInimigo) + velocidadeTurbo) * delta;

            if (MathUtils.random(0, 1000) < 15 * dificuldadeInimigo) {
                Rectangle tiroInim = new Rectangle();
                tiroInim.width = 16; tiroInim.height = 32;
                tiroInim.x = inimigo.x + 24; tiroInim.y = inimigo.y - 32;
                tirosInimigo.add(tiroInim);
            }

            if (inimigo.overlaps(jogador)) {
                if (shieldAtivo) {
                    explosoes.add(new Explosao(inimigo.x, inimigo.y));
                    iter.remove(); energiaShield -= 20;
                } else {
                    vidas--; explosoes.add(new Explosao(jogador.x, jogador.y));
                    iter.remove(); if(vidas <= 0) gameOver = true;
                }
            } else if (inimigo.y + 64 < 0) iter.remove();
        }

        for (Iterator<Rectangle> iter = tirosJogador.iterator(); iter.hasNext(); ) {
            Rectangle tiro = iter.next();
            tiro.y += 400 * delta;
            boolean atingiu = false;
            for (Iterator<Rectangle> iterInimigo = inimigos.iterator(); iterInimigo.hasNext(); ) {
                Rectangle inimigo = iterInimigo.next();
                if (tiro.overlaps(inimigo)) {
                    explosoes.add(new Explosao(inimigo.x, inimigo.y));
                    pontuacao += 100; navesDestruidas++;
                    if (MathUtils.random(0, 100) < 10) spawnPowerUp(inimigo.x, inimigo.y);
                    iterInimigo.remove(); atingiu = true; break;
                }
            }
            if (atingiu || tiro.y > ALTURA_TELA) iter.remove();
        }

        for (Iterator<Rectangle> iter = tirosInimigo.iterator(); iter.hasNext(); ) {
            Rectangle tiro = iter.next();
            tiro.y -= ((300 * dificuldadeInimigo) + (velocidadeTurbo * 0.8f)) * delta;
            if (shieldAtivo && tiro.overlaps(new Rectangle(jogador.x - 10, jogador.y + 40, 84, 40))) iter.remove();
            else if (tiro.overlaps(jogador)) {
                vidas--; explosoes.add(new Explosao(jogador.x, jogador.y));
                iter.remove(); if(vidas <= 0) gameOver = true;
            } else if (tiro.y < -50) iter.remove();
        }

        for (Iterator<Explosao> iter = explosoes.iterator(); iter.hasNext(); ) {
            Explosao exp = iter.next();
            exp.update(delta);
            exp.y -= velocidadeTurbo * delta;
            if (exp.vida <= 0) iter.remove();
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {
        if(imgAviao!=null) imgAviao.dispose();
        if(imgInimigo!=null) imgInimigo.dispose();
        if(imgTiroJog!=null) imgTiroJog.dispose();
        if(imgTiroInim!=null) imgTiroInim.dispose();
        if(imgShield!=null) imgShield.dispose();
        if(imgExplosao!=null) imgExplosao.dispose();
        if(imgPowerUp!=null) imgPowerUp.dispose();
        if(imgPlaneta!=null) imgPlaneta.dispose();
        if(imgSuperficie!=null) imgSuperficie.dispose();
        shapeRenderer.dispose();
    }

    class Explosao {
        float x, y, vida;
        public Explosao(float x, float y) { this.x = x; this.y = y; this.vida = 1.0f; }
        public void update(float delta) { vida -= delta * 2; }
    }

    class Estrela {
        float x, y, velocidadeBase, tamanho, velocidadeExtra;
        public Estrela(int w, int h) { reset(w, h); }
        public void update(float delta) {
            y -= (velocidadeBase + velocidadeExtra) * delta;
            if (y < 0) { reset(LARGURA_TELA, ALTURA_TELA); y = ALTURA_TELA; }
        }
        private void reset(int w, int h) {
            x = MathUtils.random(w); y = MathUtils.random(h);
            velocidadeBase = MathUtils.random(50, 200);
            tamanho = MathUtils.random(1.0f, 3.0f);
            velocidadeExtra = 0;
        }
    }
}
