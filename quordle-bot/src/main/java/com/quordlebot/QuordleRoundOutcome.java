package com.quordlebot;

public final class QuordleRoundOutcome {

    public static char[][] quordleVisual(String guess, String[] answers){
        char[][] fourGuessDiagrams = new char[4][];
        for (int i = 0; i < 4; i++) {
            fourGuessDiagrams[i] = GuessOptimizer.getGuessCorrectness(guess, answers[i]);
        }
        return fourGuessDiagrams;
    }
}
