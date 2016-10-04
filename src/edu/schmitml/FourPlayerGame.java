package edu.schmitml;

import java.util.ArrayList;

/**
 * Created by marcs on 10/4/2016.
 */
public class FourPlayerGame {
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
        int playerTwoScore = playerSpins(playerOneScore);
        if(playerTwoScore > 20){
            playerTwoScore = -1; // Player two lost, set his score such that it wont accidentally win
        }

        int playerThreeScore = playerSpins(playerTwoScore);
        if(playerThreeScore > 20){
            playerThreeScore = -1;
        }

        int playerFourScore = playerSpins(playerThreeScore);
        if(playerFourScore > 20){
            playerFourScore = -1;
        }

        if(playerOneScore >= playerTwoScore && playerOneScore >= playerThreeScore && playerOneScore >= playerFourScore){ // Tie goes to player one
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
