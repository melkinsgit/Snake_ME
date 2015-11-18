package com.margaret;

/**
 * Created by sn0173nd on 10/21/2015.
 */

import org.omg.PortableInterceptor.DISCARDING;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 *
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {

    protected static final int SCREEN_SECOND = 3;
    protected static final int SCREEN_THIRD = 5;
    protected static final int SCREEN_FOURTH = 6;
    protected static final int SCREEN_FIFTH = 7;
    protected static final int SCREEN_SIXTH = 8;
    protected static final int SCREEN_SEVENTH = 9;
    protected static final int SCREEN_SEGS = 10;

    protected static final int MOVE_LEFT = -40;

    private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint

    private Snake snake;
    private Kibble kibble;
    private Score score;
    private Mazes maze;

    DrawSnakeGamePanel(Snake s, Kibble k, Score sc, Mazes m){
        this.snake = s;
        this.kibble = k;
        this.score = sc;
        this.maze = m;
    }

    public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Where are we at in the game? 4 phases..
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();

        switch (gameStage) {
            case 1: {
                displayInstructions(g);
                break;
            }
            case 2 : {
                displayGame(g);
                break;
            }
            case 3: {
                displayGameOver(g);
                break;
            }
            case 4: {
                displayGameWon(g);
                break;
            }
        }
    }

    private void displayGameWon(Graphics g) {
        // TODO Replace this with something really special!
        g.clearRect(100,100,350,350);
        g.drawString("YOU WON SNAKE!!!", 150, 150);

    }
    private void displayGameOver(Graphics g) {

        g.clearRect(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension, SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
        g.drawString("GAME OVER", MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

        String textScore = score.getStringScore();
        String newHighScore = score.newHighScore();
        String textHighScore = score.getStringHighScore();

        g.drawString("SCORE = " + textScore, MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_SECOND * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

        g.drawString("HIGH SCORE = " + textHighScore, MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_THIRD * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
        g.drawString(newHighScore, MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_FOURTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

        displayInstructions(g);
//        g.drawString("Press a key to play again", MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_FIFTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
//        g.drawString("Press q to quit the game", MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_SIXTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

    }

    private void displayGame(Graphics g) {
        displayGameGrid(g);  // these are method calls to show the grid, the snake and the first kibble
        displaySnake(g);
        displayKibble(g);
        if (SnakeGUI.isMazes()) {
            displayMaze(g);
        }
    }

    private void displayGameGrid(Graphics g) {

        int maxX = SnakeGame.xPixelMaxDimension;
        int maxY= SnakeGame.yPixelMaxDimension;
        int squareSize = SnakeGame.squareSize;

        g.clearRect(0, 0, maxX, maxY);

        g.setColor(Color.RED);

        //Draw grid - horizontal lines
        for (int y=0; y <= maxY ; y+= squareSize){
            g.drawLine(0, y, maxX, y);
        }
        //Draw grid - vertical lines
        for (int x=0; x <= maxX ; x+= squareSize){
            g.drawLine(x, 0, x, maxY);
        }
    }

    private void displayKibble(Graphics g) {

        //Draw the kibble in green
        g.setColor(Color.GREEN);

        int x = kibble.getKibbleX() * SnakeGame.squareSize;
        int y = kibble.getKibbleY() * SnakeGame.squareSize;

        if (maze.isMazeSegment(kibble.getKibbleX(),kibble.getKibbleY())){
            kibble.moveKibble(snake);
        }
        else {
            g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 1, SnakeGame.squareSize - 1);
        }
    }

    private void displayMaze(Graphics g){

        //Make the maze Aqua
        g.setColor(Color.CYAN);

        //Draw maze
        for (Point p : maze.getCoordinates()) {
            g.fillRect((int)p.getX()+2, (int)p.getY()+2, SnakeGame.squareSize-3, SnakeGame.squareSize-3);
        }
    }

    private void displaySnake(Graphics g) {

        LinkedList<Point> coordinates = snake.segmentsToDraw();

        //Draw head in grey
        g.setColor(Color.LIGHT_GRAY);
        Point head = coordinates.pop();
        g.fillRect((int)head.getX()+2, (int)head.getY()+2, SnakeGame.squareSize-3, SnakeGame.squareSize-3);

        Point tail = coordinates.removeLast();

        //Draw rest of snake in black
        g.setColor(Color.BLACK);
        for (Point p : coordinates) {
            g.fillRect((int)p.getX()+2, (int)p.getY()+2, SnakeGame.squareSize-3, SnakeGame.squareSize-3);
        }

        g.setColor(Color.ORANGE);
        g.fillRect((int)tail.getX()+2, (int)tail.getY()+2, SnakeGame.squareSize-3, SnakeGame.squareSize-3);

    }

    private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!",MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_FIFTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
        g.drawString("Press q to quit the game",MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_SIXTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
    }
}
