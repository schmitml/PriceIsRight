package edu.schmitml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Class that replicates a four person "Big Wheel" game. Attempts to find the optimal stopping value for player one.
 *
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class FourPlayerGame {
    private int bestTwoPlayerStop;
    private int bestThreePlayerStop;

    public FourPlayerGame(int bestTwoPlayer, int bestThreePlayer){
        bestTwoPlayerStop = bestTwoPlayer;
        bestThreePlayerStop = bestThreePlayer;
    }

    /**
     * Runs the specified number of trials for each of the possible Q values (0-20).
     * @param numberOfTrials
     * @return - An array list with each of the probabilities for each value of Q
     */
    public ArrayList<Double> runGame(int numberOfTrials){
        ArrayList<Double> winPercentage = new ArrayList<>();

        for(int j = 0; j < 21; j++){ // Loop through each possible stopping value
            double wins = 0;
            for(int i = 0; i < numberOfTrials; i++){ // Run 1000000 trials
                wins += playGame(j); // A trial is for one stopping number
            }
            winPercentage.add(j, wins/numberOfTrials); // Add the percentage of wins for this stopping number
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
        /*
         This is a data structure that sorts integers from highest to lowest
         It allows us to easily take the highest current score and use that as our stopping point.
         Makes for neater code.
          */
        PriorityQueue<Integer> max = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        int playerOneScore = playerSpins(stop);
        if(playerOneScore == 20) { // Since ties go to player one, we short circuit a win here
            return 1;
        } else if(playerOneScore > 20){ //Player one went over so we immediately end the game because player one can't win
            return 0;
        }
        max.offer(playerOneScore); // Add the score to the maximum PQ

        int playerTwoscore = playerThreeSpecial(playerOneScore); // Use the strategy that will behave as three
                                                                    // person game if the first spin beats player one's score
        if(playerTwoscore > 20){
            playerTwoscore = -1;
        }
        max.offer(playerTwoscore);

        int playerThreescore = playerTwoSpecial(max.peek()+1); // Use the strategy that will behave as two
                                                                 // person game if the first spin beats the current highest score
        if(playerThreescore > 20){
            playerThreescore = -1;
        }
        max.offer(playerThreescore);

        // Player Four
        int playerFourScore = playerSpins(max.peek()+1); // Use the normal strategy where the highest score is the
                                                        // stopping point for this player
        if(playerFourScore > 20){
            playerFourScore = -1;
        }
        max.offer(playerFourScore);

        if(max.peek().equals(playerOneScore)){ // If the greatest score is player one's then player one won
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

        if (spin < stop){ // Stop spinning if we've gone over the stopping limit
            spin += Main.spinWheel(); // Spin again
        }

        return spin;
    }

    /**
     * The spin strategy for a two player game. Uses the optimal stopping strategy for a two person game as a limit
     * for a second spin. This method is designed for a special case where player two beats player one's score on their
     * first spin. This is the "middle" strategy.
     *
     * @param stop - the number the player will stop spinning at
     * @return - the player's score
     */
    private int playerTwoSpecial(int stop){
        int spin = Main.spinWheel();

        if((spin > stop && spin < bestTwoPlayerStop) || spin < stop){
            spin += Main.spinWheel();
        }

        return spin;
    }

    /**
     * The spin strategy for a three player game. Uses the optimal stopping strategy for a two person game as a limit
     * for a second spin. This method is designed for a special case where player three beats the highest score on their
     * first spin. This is the "middle" strategy.
     *
     * @param stop
     * @return
     */
    private int playerThreeSpecial(int stop){
        int spin = Main.spinWheel();

        if((spin < stop) || (spin > stop && spin < bestThreePlayerStop) ){
            spin += Main.spinWheel();
        }

        return spin;
    }
}
