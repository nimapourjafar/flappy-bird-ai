package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Bird;
import Entities.Pipe;
import Game.GamePanel;

public class PlayingState extends GameState {
    private BufferedImage background;
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private int score;

    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    public void init() {
        try {
            // background =
            // ImageIO.read(getClass().getResourceAsStream("/assets/Background.png"));

            bird = new Bird(50, 200, 52, 24);

            pipes = new ArrayList<>();
            pipes.add(Pipe.generatePipe(GamePanel.WIDTH, 50, 200, 150, 5));

            score = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        bird.update();

        for (Pipe pipe : pipes) {
            pipe.update();
            if (pipe.isOffscreen()) {
                pipes.remove(pipe);
                try {
                    pipes.add(Pipe.generatePipe(GamePanel.WIDTH, 50, 200, 150, 5));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                score++;
            }
            if (pipe.collidesWith(bird.getBounds())) {
                init();
            }
        }
    }

    public void draw(Graphics2D g) {
        // g.drawImage(background, 0, 0, null);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        bird.draw(g);
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_SPACE) {
            bird.jump();
        }
    }

    @Override
    public void keyReleased(int k) {
        return;
    }
}