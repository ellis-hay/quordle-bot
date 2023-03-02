package com.quordlebot.logic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.quordlebot.logic.QuordleBot.wordArray;

public class RandomQuordleGenerator {
    static int UPPERBOUND = wordArray.length; // =2309

    public static String selectRandomGuess() {
        SecureRandom rand = new SecureRandom();
        return wordArray[rand.nextInt(UPPERBOUND)];
    }

    public static String[] selectRandomAnswers() {
        SecureRandom rand = new SecureRandom();
        int i = 0;
        List<Integer> randomIndices = new ArrayList<>();
        String[] randomAnswers = new String[4];
        while (i <= 3){
            int randomInt = rand.nextInt(UPPERBOUND);
            if (randomIndices.contains(randomInt))
                continue;
            randomIndices.add(randomInt);
            randomAnswers[i] = wordArray[randomInt];
            i++;
        }
        return randomAnswers;
    }
}
