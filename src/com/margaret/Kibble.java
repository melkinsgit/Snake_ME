package com.margaret;

import java.util.Random;

/*
 *
 * @author Clara
 * Additions by Margaret Elkins
 */

public class Kibble {

    /** Identifies a random square to display a kibble
     * Any square is ok, so long as it doesn't have any snake segments in it.
     *
     */

    private int kibbleX; //This is the square number (not pixel)
    private int kibbleY;  //This is the square number (not pixel)

    // constructor that just determines the x, y coords for a Kibble
    public Kibble(Snake s){
        //Kibble needs to know where the snake is, so it does not create a kibble in the snake
        //Pick a random location for kibble, check if it is in the snake
        //If in snake, try again

        moveKibble(s);
    }

    protected void moveKibble(Snake s){

        Random rng = new Random();
        boolean kibbleInSnake = true;
        boolean kibbleInMaze = true;
        while (kibbleInSnake == true || kibbleInMaze == true) {
            //Generate random kibble location
            kibbleX = rng.nextInt(SnakeGame.xSquares);
            kibbleY = rng.nextInt(SnakeGame.ySquares);
            kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);
            if (SnakeGUI.isMazes()) { // don't put a kibble in a maze spot
                kibbleInMaze = SnakeGame.maze.isMazeSegment(kibbleX, kibbleY);
            }
            else {  // but if there's no maze, no need to check
                kibbleInMaze = false;
            }
        }
    }

    public int getKibbleX() {
        return kibbleX;
    }

    public int getKibbleY() {
        return kibbleY;
    }



}