package com.margaret;

import java.util.TimerTask;

public class GameClock extends TimerTask {


    // declare vars for a snake, a kibble, a score and a gamePanel
    Snake snake;  //
    Kibble kibble;
    Score score;
    DrawSnakeGamePanel gamePanel;

    // constructor
    public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel){
        this.snake = snake;
        this.kibble = kibble;
        this.score = score;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        // This method will be called every clock tick
        // it manages the clock depending on the stage of the game
        // it takes action with methods during game and keeps going, otherwise
        // it keeps going or stops

        int stage = SnakeGame.getGameStage();  // where are we in the game?

        switch (stage) {
            case SnakeGame.BEFORE_GAME: {
                //don't do anything, waiting for user to press a key to start
                break;
            }
            case SnakeGame.DURING_GAME: {
                //
                snake.moveSnake();
                if (snake.didEatKibble(kibble) == true) {
                    //tell kibble to update, put another piece of kibble on the board
                    kibble.moveKibble(snake);
                    // eat a kibble get a point
                    Score.increaseScore();
                }
                break;
            }
            case SnakeGame.GAME_OVER: {
                this.cancel();  //stop timer
                break;
            }
            case SnakeGame.GAME_WON: {
                this.cancel();   //stop timer
                break;
            }


        }

        gamePanel.repaint();		//In every circumstance, must update screen

    }
}