package com.margaret;

import java.awt.Point;
import java.util.LinkedList;

public class Snake {

    final int DIRECTION_UP = 0;
    final int DIRECTION_DOWN = 1;
    final int DIRECTION_LEFT = 2;
    final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers.

    private boolean hitWall = false;
    private boolean ateTail = false;
    private boolean hitMaze = false;

    protected static int snakeSquares[][];  //represents all of the squares on the screen
    //NOT pixels!
    //A 0 means there is no part of the snake in this square
    //A non-zero number means part of the snake is in the square
    //The head of the snake is 1, rest of segments are numbered in order

    private int currentHeading;  //Direction snake is going in, ot direction user is telling snake to go
    private int lastHeading;    //Last confirmed movement of snake. See moveSnake method

    private int snakeSize;   //size of snake - how many segments?

    private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble

    private int justAteMustGrowThisMuch = 0;

    private int maxX, maxY, squareSize;
    private int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

    // constructor called at the beginning of the game; sets object maxX, maxY and squareSize; inits a snakeSquares array - the board; maxX and maxY are the dimension divided by the square size - therefore how many squares in each dimension
    public Snake(int maxX, int maxY, int squareSize){
        this.maxX = maxX;
        this.maxY = maxY;
        this.squareSize = squareSize;
        //Create and fill snakeSquares with 0s
        snakeSquares = new int[maxX][maxY];
        fillSnakeSquaresWithZeros();
        createStartSnake();
    }

    protected void createStartSnake(){
        //snake starts as 3 horizontal squares in the center of the screen, moving left
        //divide the number of y squares by 2 and the number of x squares by 2 to get the array value for the center square
        int screenXCenter = (int) maxX/2;  //Cast just in case we have an odd number
        int screenYCenter = (int) maxY/2;  //Cast just in case we have an odd number

        snakeSquares[screenXCenter][screenYCenter] = 1; // the center
        snakeSquares[screenXCenter+1][screenYCenter] = 2; // one to the right
        snakeSquares[screenXCenter+2][screenYCenter] = 3; // one more to the right

        snakeHeadX = screenXCenter;
        snakeHeadY = screenYCenter;

        snakeSize = 3;

        currentHeading = DIRECTION_LEFT;
        lastHeading = DIRECTION_LEFT;

        justAteMustGrowThisMuch = 0;

    }

    private void fillSnakeSquaresWithZeros() {
        for (int x = 0; x < this.maxX; x++){
            for (int y = 0 ; y < this.maxY ; y++) {
                snakeSquares[x][y] = 0;
            }
        }
    }

    public int getSnakeHeadX() {
        return snakeHeadX;
    }

    public int getSnakeHeadY() {
        return snakeHeadY;
    }
    //    private void fillSnakeSquaresWithZeros() {
//        for (int x = 0; x < this.maxX; x++){
//            for (int y = 0 ; y < this.maxY ; y++) {
//                if (y == 5 && x < 8) {
//                    snakeSquares[x][y] = 0;
//                }
//                else {
//                    snakeSquares [x][y] = 1;
//                }
//            }
//        }
//    }

    public LinkedList<Point> segmentsToDraw(){
        //Return a list of the actual x and y coordinates of the top left of each snake segment
        //Useful for the Panel class to draw the snake
        LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
        for (int segment = 1 ; segment <= snakeSize ; segment++ ) {
            //search array for each segment number
            for (int x = 0 ; x < maxX ; x++) {
                for (int y = 0 ; y < maxY ; y++) {
                    if (snakeSquares[x][y] == segment){
                        //make a Point for this segment's coordinates and add to list
                        Point p = new Point(x * squareSize , y * squareSize);
                        segmentCoordinates.add(p);
                    }
                }
            }
        }
        return segmentCoordinates;

    }

    public void snakeUp(){  // you can't turn back on yourself so direction_down go ahead and return because there is no option to go down; the player is going to hit the wall if they're close to the wall and try to just reverse - and the game will be over
        if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
        currentHeading = DIRECTION_UP;
    }
    public void snakeDown(){  // as above no option to go up
        if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
        currentHeading = DIRECTION_DOWN;
    }
    public void snakeLeft(){  // as above no option to go right
        if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
        currentHeading = DIRECTION_LEFT;
    }
    public void snakeRight(){  // as above no option to go left
        if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
        currentHeading = DIRECTION_RIGHT;
    }

	public void	eatKibble(){
		//record how much snake needs to grow after eating food
		justAteMustGrowThisMuch += growthIncrement;
	}

    protected void moveSnake(){
        //Called every clock tick

        //Must check that the direction snake is being sent in is not contrary to current heading
        //So if current heading is down, and snake is being sent up, then should ignore.
        //Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
        if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
            currentHeading = DIRECTION_UP; //keep going the same way
        }
        if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
            currentHeading = DIRECTION_DOWN; //keep going the same way
        }
        if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
            currentHeading = DIRECTION_RIGHT; //keep going the same way
        }
        if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
            currentHeading = DIRECTION_LEFT; //keep going the same way
        }

        //Did you hit the wall, snake?
        //Or eat your tail? Don't move.

        if ( hitWall == true || ateTail == true) { // TODO do we need this?
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            return;
        }

        //Use snakeSquares array, and current heading, to move snake

        //Put a 1 in new snake head square
        //increase all other snake segments by 1
        //set tail to 0 if snake did not just eat
        //Otherwise leave tail as is until snake has grown the correct amount

        //Find the head of the snake - snakeHeadX and snakeHeadY

        //Increase all snake segments by 1
        //All non-zero elements of array represent a snake segment

        for (int x = 0 ; x < maxX ; x++) {
            for (int y = 0 ; y < maxY ; y++){
                if (snakeSquares[x][y] != 0) {
                    snakeSquares[x][y]++;
                }
            }
        }

        //now identify where to add new snake head
        if (currentHeading == DIRECTION_UP) {
            //Subtract 1 from Y coordinate so head is one square up
            snakeHeadY-- ;
        }
        if (currentHeading == DIRECTION_DOWN) {
            //Add 1 to Y coordinate so head is 1 square down
            snakeHeadY++ ;
        }
        if (currentHeading == DIRECTION_LEFT) {
            //Subtract 1 from X coordinate so head is 1 square to the left
            snakeHeadX -- ;
        }
        if (currentHeading == DIRECTION_RIGHT) {
            //Add 1 to X coordinate so head is 1 square to the right
            snakeHeadX ++ ;
        }

        //Create the warp walls feature

        if (!SnakeGUI.warpWalls) {
            if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0 ){
                hitWall = true;
                SnakeGame.setGameStage(SnakeGame.GAME_OVER);
                System.out.println("I lost");
                return;
            }
        }
        else {
            if (snakeHeadX >= maxX) {
                snakeHeadX = 0;
            }
            if (snakeHeadX < 0) {
                snakeHeadX = maxX - Math.abs(snakeHeadX) % maxX;
            }

            if (snakeHeadY >= maxY) {
                snakeHeadY = 0;
            }
            if (snakeHeadY < 0) {
                snakeHeadY = maxY - Math.abs(snakeHeadY) % maxY;
            }
        }

        //Does this make the snake eat its tail?

        if (snakeSquares[snakeHeadX][snakeHeadY] != 0 && snakeSquares[snakeHeadX][snakeHeadY] != -1) {  // just added -1 to this test to see if the game is losing when the snake is in the maze
            ateTail = true;
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            System.out.println("I lost because I ate my tail");
            return;
        }

        //Did the snake hit a maze wall?
        if (SnakeGUI.isMazes()){
            if (Mazes.mazeGrid[snakeHeadX][snakeHeadY] == -1) {
                hitMaze = true;
                SnakeGame.setGameStage(SnakeGame.GAME_OVER);
                System.out.println("I lost because the snake hit the maze");
                return;
            }
        }

        //Otherwise, game is still on. Add new head
        snakeSquares[snakeHeadX][snakeHeadY] = 1;

        //If snake did not just eat, then remove tail segment
        //to keep snake the same length.
        //find highest number, which should now be the same as snakeSize+1, and set to 0

        if (justAteMustGrowThisMuch == 0) {
            for (int x = 0 ; x < maxX ; x++) {
                for (int y = 0 ; y < maxY ; y++){
                    if (snakeSquares[x][y] == snakeSize+1) {
                        snakeSquares[x][y] = 0;
                    }
                }
            }
        }
        else {
            //Snake has just eaten. leave tail as is.  Decrease justAte... variable by 1.
            justAteMustGrowThisMuch -- ;
            snakeSize ++;
        }

        lastHeading = currentHeading; //Update last confirmed heading

    }

    protected boolean didHitWall(){
        return hitWall;
    }

    protected boolean didEatTail(){
        return ateTail;
    }

    public boolean isSnakeSegment(int kibbleX, int kibbleY) {
        // make sure kibble isn't in snake or in a maze grid spot
        if (snakeSquares[kibbleX][kibbleY] == 0) {
            return false;
        }
        return true;
    }

    public boolean didEatKibble(Kibble kibble) {
        //Is this kibble in the snake? It should be in the same square as the snake's head
        if (kibble.getKibbleX() == snakeHeadX && kibble.getKibbleY() == snakeHeadY){
            java.awt.Toolkit.getDefaultToolkit().beep();
            justAteMustGrowThisMuch += growthIncrement;
            return true;
        }
        return false;
    }

    public String toString(){
        String textsnake = "";
        //This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees.
        for (int y = 0 ; y < maxY ; y++) {
            for (int x = 0 ; x < maxX ; x++){
                textsnake = textsnake + snakeSquares[x][y];
            }
            textsnake += "\n";
        }
        return textsnake;
    }

    public boolean wonGame() {

        //If all of the squares have snake segments in, the snake has eaten so much kibble
        //that it has filled the screen. Win!
        for (int x = 0 ; x < maxX ; x++) {
            for (int y = 0 ; y < maxY ; y++){
                if (snakeSquares[x][y] == 0) {
                    //there is still empty space on the screen, so haven't won
                    return false;
                }
            }
        }
        //But if we get here, the snake has filled the screen. win!
        SnakeGame.setGameStage(SnakeGame.GAME_WON);

        return true;
    }

    public void reset() {
        hitWall = false;
        ateTail = false;
        hitMaze = false;
        SnakeGame.maze = new Mazes();
        fillSnakeSquaresWithZeros();
        createStartSnake();

    }

    public boolean isGameOver() {
        if (hitWall == true || ateTail == true){
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            System.out.println("I lost in isGameOver");
            return true;

        }
        return false;
    }

    public static void setSnakeSquares(int[][] snakeSquares) {
        Snake.snakeSquares = snakeSquares;
    }

    protected static int[][] getSnakeSquares() {
        return snakeSquares;
    }
}
