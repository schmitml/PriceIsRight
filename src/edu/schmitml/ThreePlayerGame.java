package edu.schmitml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Class that replicates a three person "Big Wheel" game. Attempts to find the optimal stopping value for player one.
 *
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class ThreePlayerGame {
    private int bestTwoPlayerStop;

    public ThreePlayerGame(int bestTwoPlayer){
        bestTwoPlayerStop = bestTwoPlayer; // Used for the special two player spin
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
    public int playGame(int stop) {
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
        if(playerOneScore > 20){ // If player one goes over 20 then they automatically lose, so we end the game
            return 0;
        } else if(playerOneScore == 20){ // Since player one wins ties at 20, they win the game
            return 1;
        }
        max.offer(playerOneScore); // Add score to the PQ to find score to beat

        // Custom logic for the middle player
        int playerTwoScore = playerTwoSpecial(playerOneScore);
        if(playerTwoScore > 20){
            playerTwoScore = -1; // Player two lost, set his score such that it wont accidentally win
        }
        max.offer(playerTwoScore);

        int playerThreeScore = playerSpins(max.peek()); // Use the current winning score(the highest in the PQ) as the value to stop at
        if(playerThreeScore > 20){
            playerThreeScore = -1;
        }
        max.offer(playerThreeScore);

        if(max.peek().equals(playerOneScore)){ // If the highest score is the first player's score then they win. If player one and another tie then it goes to player one.
            return 1;
        }
        return 0;
    }

    /**
     * The general spin strategy for a player, where stop is either assigned for player one or is the current highest
     * score that this player must beat to win.
     *
     * @param stop - the number the player will stop spinning at
     * @return - the player's score
     */
    private int playerSpins(int stop){
        int spin = Main.spinWheel(); // Spin the wheel

        if (spin < stop){
            spin += Main.spinWheel();
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
}
