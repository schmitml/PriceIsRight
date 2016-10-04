package edu.schmitml;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Marc Schmitt
 * schmitml@rose-hulman.edu
 * MA381
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
        TwoPlayerGame g1 = new TwoPlayerGame();
        String g1Result = g1.playGame();
    }


    /*****************UTIL*****************/
    public static int spinWheel(){
        return ThreadLocalRandom.current().nextInt(0, 20 + 1);
    }
}
