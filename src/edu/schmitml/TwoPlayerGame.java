package edu.schmitml;

import java.util.ArrayList;

/**
 * Class that replicates a two person "Big Wheel" game. Attempts to find the optimal stopping value for player one.
 *
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class TwoPlayerGame {

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
    private int playGame(int stop) {
        int playerOneScore = playerSpins(stop);
        if(playerOneScore > 20){
            return 0;
        }
        int playerTwoScore = playerSpins(playerOneScore+1);
        if(playerTwoScore > 20){
            return 1;
        }

        if(playerOneScore >= playerTwoScore){ // Tie goes to player one
            return 1;
        }
        return 0;
    }

    /**
     * For a two person game each playerSpins has a similar strategy. The difference between the two players is where
     * they get their stopping number from. Player one has to determine for themselves, which we simulate by trying
     * every option, whereas playerSpins two has their stopping number determined by playerSpins one's score.
     *
     * @param stop - the number the playerSpins will stop at
     * @return - the playerSpins's score
     */
    private int playerSpins(int stop){
        int spin = Main.spinWheel(); // Spin the wheel

        if (spin < stop){
            spin += Main.spinWheel();
        }

        return spin;
    }

}
