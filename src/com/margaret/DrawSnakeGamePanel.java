package com.margaret;

/*
 *
 * @author Clara
 * Additions by Margaret Elkins
 */


import java.awt.*;
import java.util.LinkedList;
import javax.swing.JPanel;

public class DrawSnakeGamePanel extends JPanel {

    protected static final int SCREEN_SECOND = 3;  // these are variables used to establish spacing of the text on the display screen
    protected static final int SCREEN_THIRD = 5;
    protected static final int SCREEN_FOURTH = 6;
    protected static final int SCREEN_FIFTH = 7;
    protected static final int SCREEN_SIXTH = 8;
    protected static final int SCREEN_SEGS = 10;

    protected static final int MOVE_LEFT = -40;  // another variable used for spacing the text on the display screen

    private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint

    private Snake snake;  // variables for this class and the panel
    private Kibble kibble;
    private Score score;
    private Mazes maze;

    // constructor that assigns the values from SnakeGame to the Snake Game Panel class
    DrawSnakeGamePanel(Snake s, Kibble k, Score sc, Mazes m){
        this.snake = s;
        this.kibble = k;
        this.score = sc;
        this.maze = m;
    }

    public Dimension getPreferredSize() { // these values come from user input and determine the size of 
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

    private void displayGameWon(Graphics g) {  // never got here
        // TODO Replace this with something really special!
        g.clearRect(100,100,350,350);
        g.drawString("YOU WON SNAKE!!!", 150, 150);

    }
    private void displayGameOver(Graphics g) {
        // replaced the number values here with variables

        g.clearRect(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension, SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
        g.drawString("GAME OVER", MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

        String textScore = score.getStringScore();
        String newHighScore = score.newHighScore();
        String textHighScore = score.getStringHighScore();

        // the screen second, third, fourth, etc., space the text veritcally throughout the screen
        g.drawString("SCORE = " + textScore, MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_SECOND * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

        g.drawString("HIGH SCORE = " + textHighScore, MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_THIRD * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
        g.drawString(newHighScore, MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_FOURTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);

        // instead of the two lines of code that were here, I just recalled the display instructions method and made sure the instructions were always in a place on the screen that didn't interfere with game_over displays
        displayInstructions(g);
    }

    private void displayGame(Graphics g) {
        displayGameGrid(g);  // these are method calls to show the grid, the snake and the first kibble
        displaySnake(g);
        if (SnakeGUI.isMazes()) {  // if the user had chosen to have mazes display the maze
            displayMaze(g);
        }
        displayKibble(g);
        if (SnakeGUI.isMazes()) {  // if the user had chosen to have mazes display the maze
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

        // get the position of the kibble
        int x = kibble.getKibbleX() * SnakeGame.squareSize;
        int y = kibble.getKibbleY() * SnakeGame.squareSize;

        // if mazes are turned on and the kibble is in the maze move it
        if (SnakeGUI.isMazes()) {
            if (maze.isMazeSegment(kibble.getKibbleX(), kibble.getKibbleY())) {
                SnakeGame.kibble.moveKibble(snake);
            }
        }
        // display the kibble
        g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 1, SnakeGame.squareSize - 1);
    }

    private void displayMaze(Graphics g){

        //Make the maze Aqua
        g.setColor(Color.CYAN);

        //Draw maze - this works the same way the draw snake works; but the maze positions are not store in the snake 2D array; they are stored in a 2D array in maze called mazeGrid
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

        // except the tail, make that orange
        g.setColor(Color.ORANGE);
        g.fillRect((int)tail.getX()+2, (int)tail.getY()+2, SnakeGame.squareSize-3, SnakeGame.squareSize-3);

    }

    private void displayInstructions(Graphics g) {
        // again, numbers replaced with variables
        g.drawString("Press any key to begin!",MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_FIFTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
        g.drawString("Press q to quit the game",MOVE_LEFT + SnakeGame.xPixelMaxDimension/2, SCREEN_SIXTH * SnakeGame.xPixelMaxDimension/SCREEN_SEGS);
    }
}
