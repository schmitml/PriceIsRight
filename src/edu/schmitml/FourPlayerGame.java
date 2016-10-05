package edu.schmitml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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
        PriorityQueue<Integer> max = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        int playerOneScore = playerSpins(stop);
        if(playerOneScore > 20){
            return 0;
        }
        max.offer(playerOneScore);

        int score;
        for(int i = 0; i < 3; i++){
            score = playerSpins(max.peek());
            if(score > 20){
                score = -1;
            }
            max.offer(score);
        }

        if(max.peek().equals(playerOneScore)){
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

        if (spin < stop){
            spin += Main.spinWheel();
        }

        return spin;
    }
}
