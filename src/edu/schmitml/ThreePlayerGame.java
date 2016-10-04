package edu.schmitml;

import java.util.ArrayList;

/**
 * Class that replicates a three person "Big Wheel" game. Attempts to find the optimal stopping value for playerSpins one.
 *
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class ThreePlayerGame {

    public ArrayList<Double> runGame(int numberOfTrials){
        ArrayList<Double> winPercentage = new ArrayList<>();

        for(int j = 0; j < 21; j++){
            double wins = 0;
            for(int i = 0; i < numberOfTrials; i++){ // Run 10000 trials
                wins += playGame(j); // A trial is for one stopping number
            }
            winPercentage.add(j, wins/numberOfTrials);
        }

        return winPercentage;
    }

    /**
     * Simulates one game.
     *
     * @param stop - the stopping value for player one.
     * @return - +1 if player one won
     *           0 if player one lost
     */
    public int playGame(int stop) {
        int playerOneScore = playerSpins(stop);
        if(playerOneScore > 20){
            return 0;
        }
        int playerTwoScore = playerSpins(playerOneScore);
        if(playerTwoScore > 20){
            playerTwoScore = -1; // Player two lost, set his score such that it wont accidentally win
        }

        int playerThreeScore = playerSpins(playerTwoScore);
        if(playerThreeScore > 20 && playerTwoScore > 20){
            return 1;
        }

        if(playerOneScore >= playerTwoScore && playerOneScore >= playerThreeScore){ // Tie goes to player one
            return 1;
        }
        return 0;
    }

    /**
     * @param stop - the number the playerSpins will stop at
     * @return - the playerSpins's score
     */
    private int playerSpins(int stop){
        int spin = Main.spinWheel(); // Spin the wheel

        if (spin <= stop){
            spin += Main.spinWheel();
        }

        return spin;
    }
}
