package com.margaret;

import java.awt.*;
import java.util.Random;
import java.util.LinkedList;

// this is an entirely new class

public class Mazes {

    private static int mazeStartX;  // like the head of the snake
    private static int mazeStartY;
    private static int mazeSize;  // the number of blocks in the maze
    private static int mazeDir;  // the direction in which the maze is currently growing

    private static int mazeX = SnakeGame.xPixelMaxDimension/SnakeGame.squareSize; // the dimensions of the mazeGrid are exactly the same as those for the snakeGrid, and are determined by the size game the user chooses to play
    private static int mazeY = SnakeGame.yPixelMaxDimension/SnakeGame.squareSize;

    protected static int mazeGrid[][];  // a 2D grid that stores the layout of the mazes
    protected static LinkedList<Point> coordinates;  // a list of Points that are the coorindates for the top left of each square of the maze grid

    protected static int thisManyMazes;  // the number of mazes to be drawn

    // constructor
    public Mazes() {
        mazeGrid = new int[mazeX][mazeY];  // set the mazeGrid dimensions
        fillMazeGridWithZeros();  // fill the maze with zeros, much like the snakeGrid
        if (SnakeGUI.isMazes()) {  // if the user has chosen to play with mazes create a set of maze coordinates
            this.coordinates = mazeToDraw();
        }
    }
    public void fillMazeGridWithZeros() {
        for (int x = 0; x < this.mazeX; x++){
            for (int y = 0 ; y < this.mazeY ; y++) {
                mazeGrid[x][y] = 0;
            }
        }
    }

    public static int[][] getMazeGrid() {
        return mazeGrid;
    }

    public LinkedList<Point> mazeToDraw(){

        LinkedList<Point> mazeCoordinates = new LinkedList<Point>();

        Random rng = new Random();

        //for each maze to draw
        for (int j = 0; j < thisManyMazes; j++){
            boolean gotFirstSpot = false;
            mazeDir = rng.nextInt(4)+1;

        // for each block of the maze
        for (int i = 0; i < mazeSize; i++) {

            //Generate random maze start location
            while (!gotFirstSpot){
                mazeStartX = rng.nextInt(SnakeGame.xSquares);
                mazeStartY = rng.nextInt(SnakeGame.ySquares);

                // && mazeStartX != SnakeGame.snake.getSnakeHeadX() || mazeStartY != SnakeGame.snake.getSnakeHeadY()

                // find a first piece of the maze that isn't in the snake head - remember the maze grid dimensions are exaxctly the same as the snake grid dimensions
                if (mazeGrid[mazeStartX][mazeStartY] == 0 && mazeStartX != mazeX/2 && mazeStartY != mazeY/2) {
                    mazeGrid[mazeStartX][mazeStartY] = -1;  // first piece of maze
                    gotFirstSpot = true;
                }

//                System.out.println("After first spot");
            }

            boolean mazeSpot = false;

            // for each spot or square of the maze, try to add a square in the randomly chosen direction mazeDir
            while (!mazeSpot) {
                try {
                    switch (mazeDir) {
                        case 1:
                            {
                            if (mazeGrid[mazeStartX + 1][mazeStartY] != -1 && !SnakeGame.snake.isSnakeSegment(mazeStartX + 1,mazeStartY )){
                                mazeGrid[mazeStartX + 1][mazeStartY] = -1;
                                mazeStartX++;
                                mazeSpot = true;
                                break;
                            }
                        }
                        case 2:
                            {
                            if (mazeGrid[mazeStartX - 1][mazeStartY] != -1 && !SnakeGame.snake.isSnakeSegment(mazeStartX - 1,mazeStartY )){
                                mazeGrid[mazeStartX - 1][mazeStartY] = -1;
                                mazeStartX--;
                                mazeSpot = true;
                                break;
                            }
                        }
                        case 3:
                            {
                            if (mazeGrid[mazeStartX][mazeStartY + 1] != -1 && !SnakeGame.snake.isSnakeSegment(mazeStartX,mazeStartY + 1 )){
                                mazeGrid[mazeStartX][mazeStartY + 1] = -1;
                                mazeStartY++;
                                mazeSpot = true;
                                break;
                            }
                        }
                        case 4:{
                            if (mazeGrid[mazeStartX][mazeStartY - 1] != -1 && !SnakeGame.snake.isSnakeSegment(mazeStartX,mazeStartY - 1 )){
                                mazeGrid[mazeStartX][mazeStartY - 1] = -1;
                                mazeStartY--;
                                mazeSpot = true;
                                break;
                            }
                        }
                        // if the randomly chosen direction doesn't work, choose another random direction
                        default:{
                            mazeDir = rng.nextInt(4)+1;
                        }
                    }
                // if the next square falls outside the grid, reset the random direction and continue the maze somewhere else on the grid
                } catch (ArrayIndexOutOfBoundsException arre) {
                    mazeDir = rng.nextInt(4)+1;
                    gotFirstSpot = false;
                    while (!gotFirstSpot){
                        mazeStartX = rng.nextInt(SnakeGame.xSquares);
                        mazeStartY = rng.nextInt(SnakeGame.ySquares);

                        // find a first piece of the maze that isn't in the snake head - remember the maze grid dimensions are exactly the same as the snake grid dimensions
                        if (mazeGrid[mazeStartX][mazeStartY] == 0 && mazeStartX != mazeX/2 && mazeStartY != mazeY/2) {
                            mazeGrid[mazeStartX][mazeStartY] = -1;  // first piece of maze
                            gotFirstSpot = true;
                        }

//                System.out.println("After first spot");
                    }
                }
                catch (Exception ex){
                    System.out.println("Unhandled maze build exception");
                    ex.printStackTrace();
                }
            }
        }
        }


            //search array for each segment number taken from Clara's snakeToDraw
            for (int x = 0 ; x < mazeX ; x++) {
                for (int y = 0 ; y < mazeY ; y++) {
                    if (mazeGrid[x][y] == -1){
                        //make a Point for this segment's coordinates and add to list
                        Point p = new Point(x * SnakeGame.squareSize, y * SnakeGame.squareSize);
                        mazeCoordinates.add(p);
                    }
                }
            }

        return mazeCoordinates;

    }
    public boolean isMazeSegment(int kibbleX, int kibbleY) {
        // make sure kibble isn't in a maze grid spot
        if (mazeGrid[kibbleX][kibbleY] == -1) {
            return true;
        }
        return false;
    }

    // Getters and Setters
    public static int getMazeStartX() {
        return mazeStartX;
    }

    public static int getMazeStartY() {
        return mazeStartY;
    }

    public static int getMazeSize() {
        return mazeSize;
    }

    public static int getMazeX() {
        return mazeX;
    }

    public static int getMazeY() {
        return mazeY;
    }

    public static void setMazeStartX(int mazeStartX) {
        Mazes.mazeStartX = mazeStartX;
    }

    public static void setMazeStartY(int mazeStartY) {
        Mazes.mazeStartY = mazeStartY;
    }

    public static void setMazeSize(int mazeSize) {
        Mazes.mazeSize = mazeSize;
    }

    public static void setMazeX(int mazeX) {
        Mazes.mazeX = mazeX;
    }

    public static void setMazeY(int mazeY) {
        Mazes.mazeY = mazeY;
    }

    public static void setMazeGrid(int[][] mazeGrid) {
        Mazes.mazeGrid = mazeGrid;
    }

    public static LinkedList<Point> getCoordinates() {
        return coordinates;
    }
}
