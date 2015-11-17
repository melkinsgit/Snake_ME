package com.margaret;

import java.util.Timer;

import javax.swing.*;


public class SnakeGame {

    // TODO fix ugly start screen
    // TODO add other features
    // TODO get rid of magic numbers

//    public static int xPixelMaxDimension = 501;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
//    public static int yPixelMaxDimension = 501;

    public static int xPixelMaxDimension;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
    public static int yPixelMaxDimension;

    public static int xSquares;
    public static int ySquares;

//    public static int squareSize = 50;

    public static int squareSize;

    // instantiate a Snake, protected static for access, a Snake contains 2D array of squares that all start at value 0, and an attendant LinkedList of points which are the coordinates of the current status of the snake; the LinkedList reflects points of the array, which are the squaras the snake is actually in; the Snake also moves the snake, keeps track of wins and can tell the Kibble if a certain 2D array spot has snake in it - any segement of the current snake
    // TODO why static?
    protected static Snake snake ;

    // instantiate a Kibble, a Kibble is a randomly placed in the Snake 2D array, as long as the current snake is not there
    protected static Kibble kibble;

    protected static Score score;

    protected static Mazes maze;

    static final int BEFORE_GAME = 1;
    static final int DURING_GAME = 2;
    static final int GAME_OVER = 3;
    static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
    //instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
    //Using constant names instead makes it easier to keep it straight. Refer to these variables
    //using statements such as SnakeGame.GAME_OVER

    private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening.
    //Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value

//    protected static long clockInterval = 1250; //controls game speed

    protected static long clockInterval;
    //Every time the clock ticks, the snake moves
    //This is the time between clock ticks, in milliseconds
    //1000 milliseconds = 1 second.

    static JFrame snakeFrame;
    static DrawSnakeGamePanel snakePanel;
    //Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
    //http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
    //http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

    private static void createAndShowGUI() {
        //Create and set up the window.
        snakeFrame = new JFrame();
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
        snakeFrame.setUndecorated(true); //hide title bar
        snakeFrame.setVisible(true);
        snakeFrame.setResizable(false);

        snakePanel = new DrawSnakeGamePanel(snake, kibble, score, maze);
        snakePanel.setFocusable(true);
        snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

        snakeFrame.add(snakePanel);
        snakePanel.addKeyListener(new GameControls(snake));

        setGameStage(BEFORE_GAME);

        snakeFrame.setVisible(true);
    }

    private static void initializeGame() {
        //set up score, snake and first kibble
        xSquares = xPixelMaxDimension / squareSize;
        ySquares = yPixelMaxDimension / squareSize;

        snake = new Snake(xSquares, ySquares, squareSize);
        if (SnakeGUI.isMazes()){
            maze = new Mazes();
        }
        score = new Score();
        kibble = new Kibble(snake);
        gameStage = BEFORE_GAME;
    }

    protected static void newGame() {
        Timer timer = new Timer();
        GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
        timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
    }

    public static void main(String[] args) {

        SnakeGUI snakeGUI = new SnakeGUI();  // display the interactive start window for the player

    }

    public static void startGame() {

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {  // this has to be overridden, i.e. must be defined wtih Interface Runnable
                initializeGame();
                createAndShowGUI();
            }
        });
    }



    public static int getGameStage() {
        return gameStage;
    }

    // pure function only alters vars inside
    public static boolean gameEnded() {
        if (gameStage == GAME_OVER || gameStage == GAME_WON){
            return true;
        }
        return false;
    }

    public static void setGameStage(int gameStage) {
        SnakeGame.gameStage = gameStage;
    }
}