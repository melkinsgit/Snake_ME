package com.margaret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by margaretbrower on 11/10/15.
 */
public class SnakeGUI extends JFrame {

    // a variety of variables used to set SnakeGame variables based on user choices
    protected static final int BOARD_SIZE_SM = 501;
    protected static final int BOARD_SIZE_MED = 601;
    protected static final int BOARD_SIZE_LG = 801;

    protected static final int SQ_SIZE_SM = 50;
    protected static final int SQ_SIZE_MED = 25;
    protected static final int SQ_SIZE_LG = 40;

    protected static final long CLOCK_INT_LO = 1250; //controls game speed
    protected static final long CLOCK_INT_MED = 600; //controls game speed
    protected static final long CLOCK_INT_HI = 200; //controls game speed

    protected static int SMALL_MAZE_CT = 3;
    protected static int MED_MAZE_CT = 5;
    protected static int LG_MAZE_CT = 6;
    protected static int MAZE_SIZE_SM  = 3;
    protected static int MAZE_SIZE_MED  = 4;
    protected static int MAZE_SIZE_LG  = 6;

    private JLabel WarpWallsLabel;
    private JLabel MazesLabel;
    private JLabel SpeedLabel;
    private JLabel SizeLabel;

    // variables associated with interactive elements of the GUI form
    private JRadioButton MazesOnRadio;
    private JRadioButton MazesOffRadio;
    private JRadioButton WarpOnRadio;
    private JRadioButton WarpOffRadio;
    private JRadioButton SpeedLoRadio;
    private JRadioButton SpeedMedRadio;
    private JRadioButton SpeedHighRadio;
    private JRadioButton SizeSmallRadio;
    private JRadioButton SizeMedRadio;
    private JRadioButton SizeLargeRadio;
    private JPanel rootPanel;
    private JButton DoneButton;

    // booleans set depending on user choices for warp walls and mazes
    protected static boolean mazes;
    protected static boolean warpWalls;

    // the GUI
    public SnakeGUI () {
        setPreferredSize(new Dimension(500,200));
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // radio buttons for Small, Medium or Large games
        SizeSmallRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SnakeGame.xPixelMaxDimension = BOARD_SIZE_SM;
                SnakeGame.yPixelMaxDimension = BOARD_SIZE_SM;
                SnakeGame.squareSize = SQ_SIZE_SM;

            }
        });
        SizeMedRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SnakeGame.xPixelMaxDimension = BOARD_SIZE_MED;
                SnakeGame.yPixelMaxDimension = BOARD_SIZE_MED;
                SnakeGame.squareSize = SQ_SIZE_MED;
            }
        });
        SizeLargeRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SnakeGame.xPixelMaxDimension = BOARD_SIZE_LG;
                SnakeGame.yPixelMaxDimension = BOARD_SIZE_LG;
                SnakeGame.squareSize = SQ_SIZE_LG;
            }
        });
        MazesOnRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mazes = true;
                // if the user has chosen mazes set the maze count and size based on the size of the game
                if (SnakeGame.squareSize == SQ_SIZE_LG){
                    Mazes.thisManyMazes = LG_MAZE_CT;
                    Mazes.setMazeSize (MAZE_SIZE_LG);
                }
                else if (SnakeGame.squareSize == SQ_SIZE_MED){
                    Mazes.thisManyMazes = MED_MAZE_CT;
                    Mazes.setMazeSize (MAZE_SIZE_MED);
                } else {
                    Mazes.thisManyMazes = SMALL_MAZE_CT;
                    Mazes.setMazeSize (MAZE_SIZE_SM);
                }
            }
        });
        MazesOffRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mazes = false;
            }
        });
        // set warp walls to true if the user chooses this option
        WarpOnRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                warpWalls = true;
            }
        });
        WarpOffRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                warpWalls = false;
            }
        });
        // set the clock interval based on users speed choice
        SpeedLoRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SnakeGame.clockInterval = CLOCK_INT_LO;
            }
        });
        SpeedMedRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SnakeGame.clockInterval = CLOCK_INT_MED;
            }
        });
        SpeedHighRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SnakeGame.clockInterval = CLOCK_INT_HI;
            }
        });
        // when the user clicks Done button, remove the GUI form and start the game
        DoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                SnakeGame.startGame();
            }
        });
    }

    public static boolean isMazes() {
        return mazes;
    }
    // Setter
    public static void setMazes(boolean mazes) {
        SnakeGUI.mazes = mazes;
    }
}
