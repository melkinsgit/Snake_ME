package com.margaret;

import java.util.Random;

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
            if (SnakeGUI.isMazes()) {
                kibbleInMaze = SnakeGame.maze.isMazeSegment(kibbleX, kibbleY);
                System.out.println("Have created maze. Now making a kibble for the board. Kibble in " + kibbleX + " ," + kibbleY + " of maze is " + kibbleInMaze);
                System.out.println("Maze of " + kibbleX + " ," + kibbleY + " is " + SnakeGame.maze.isMazeSegment(kibbleX, kibbleY));
                System.out.println("The maze boolean should be the kibble value in maze.");
            }
            else {
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