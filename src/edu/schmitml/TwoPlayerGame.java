package edu.schmitml;

/**
 * Class that replicates a two person "Big Wheel" game. Attempts to find the optimal stopping value for player one.
 *
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class TwoPlayerGame {
    private int stop;

    public String playGame() {
        int spin; //Value of spin
        for(int i = 0; i < 20; i++){
            // Pick a stopping value stop
            stop = i;

            spin = Main.spinWheel(); // Spin the wheel


        }


        return "Not yet implemented";
    }
}
