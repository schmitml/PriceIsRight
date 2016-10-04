package edu.schmitml;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class Main {
    public static int NUMBER_OF_TRIALS = 10000000;

    public static void main(String[] args) {
        TwoPlayerGame g1 = new TwoPlayerGame();
        ArrayList<Double> winPercentages = g1.runGame(NUMBER_OF_TRIALS);

        int bestStop = 0;
        double bestPercent = winPercentages.get(0);
        for(int i = 0; i < 21; i++){
            if(winPercentages.get(i) >= bestPercent){
                bestPercent = winPercentages.get(i);
                bestStop = i;
            }
            System.out.println("Stop = " + i + ": " + winPercentages.get(i));
        }
        System.out.println("\nBest stop " + bestStop +" with " + bestPercent);
    }


    /*****************UTIL*****************/
    public static int spinWheel(){
        return ThreadLocalRandom.current().nextInt(0, 20 + 1);
    }
}
