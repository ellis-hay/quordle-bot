package com.quordlebot.logic;

import java.util.Arrays;

import static com.quordlebot.logic.QuordleBot.wordArray;

public class varArgQuordleBot {
    static final char[] CORRECT_PATTERN = new char[] {'$', '$', '$', '$', '$'};
    private static final int DEFAULT = 15;

    public static void main(String[] args) {
        RandomQuordleGenerator quordleGenerator= new RandomQuordleGenerator();
        GameLog guesses = quordleBot( quordleGenerator.selectRandomAnswers());
        System.out.println(guesses.getGuessLog() +" "+ guesses.getGuessesByWord());
    }

    /**
     * The method below allows a varArg of Strings as parameters for use as guesses and to compare what the "computer"
     * would choose vs. a person's choice
     */

    public static GameLog quordleBot(String[] givenAnswers, String... presetGuessesAfterFirst){
        String[] answers = givenAnswers;
        String[][] wordPossibilities = new String[][] {wordArray, wordArray, wordArray, wordArray};
        int unknownAnswers = 4;
        String guess;
        if (presetGuessesAfterFirst.length > 0) {
            guess = presetGuessesAfterFirst[0];
        } else {
            guess = GuessOptimizer.nextGuessFinder(wordPossibilities, unknownAnswers);; // "trace"
        }
        int[] guessesNeeded = new int[] {DEFAULT, DEFAULT, DEFAULT, DEFAULT};
        String[] guessLog = new String[9];
        for (int i = 0; i < presetGuessesAfterFirst.length; i++){
            guessLog[i+1] = presetGuessesAfterFirst[i];
        }
        PossibleWordsLeft[] possibleWordsByRound = new PossibleWordsLeft[9];
        for (int guessNum = 1; guessNum <= 9; guessNum++) {
            //System.out.println(guess);
            guessLog[guessNum - 1] = guess;
            char[][] quordleDiagrams = QuordleRoundOutcome.quordleVisual(guess, answers);
            for (int i = 0; i < 4; i++) {
                if (Arrays.equals(quordleDiagrams[i], CORRECT_PATTERN)){
                    //System.out.println("Word " + (i+1) + " ("+ answers[i] +") guessed in " + guessNum + " tries.");
                    guessesNeeded[i] = guessNum;
                    unknownAnswers -= 1;
                    wordPossibilities[i] = new String[0];
                }
                if (guessesNeeded[i] == DEFAULT) {
                    wordPossibilities[i] = GuessOptimizer.possibleAnswerFinder(guess, wordPossibilities[i],
                            quordleDiagrams[i]).toArray(new String[0]);
                }
            }
            if (unknownAnswers == 0) {
                break;
            }
            possibleWordsByRound[guessNum - 1] = new PossibleWordsLeft(wordPossibilities);
            if (guessLog[guessNum] == null) {  //guessNum is offset by 1 from list index count
                guess = GuessOptimizer.nextGuessFinder(wordPossibilities, unknownAnswers);
            } else {
                guess = guessLog[guessNum];
            }
        }
        return new GameLog(guessLog[0], answers, guessLog, guessesNeeded, possibleWordsByRound);
    }
}
