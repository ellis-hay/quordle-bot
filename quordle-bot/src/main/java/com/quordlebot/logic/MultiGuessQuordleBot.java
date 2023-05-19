package com.quordlebot.logic;

import java.util.Arrays;

import static com.quordlebot.logic.QuordleBot.wordArray;

public class MultiGuessQuordleBot {
    static final char[] CORRECT_PATTERN = new char[] {'$', '$', '$', '$', '$'};
    private static final int MAX_GUESS_DEFAULT = 15;

    public static void main(String[] args) {
        RandomQuordleGenerator quordleGenerator = new RandomQuordleGenerator();
        String guess1 = quordleGenerator.selectRandomGuess();
        String guess2 = quordleGenerator.selectRandomGuess();
        GameLog guesses = quordleBot(quordleGenerator.selectRandomAnswers(), guess1, guess2);
        System.out.println(guesses.getGuessLog() +" "+ guesses.getGuessesByWord());
    }

    /**
     * The method below allows a varArg of Strings as parameters for use as guesses and to compare what the "computer"
     * would choose vs. a person's choice
     */

    public static GameLog quordleBot(String[] givenAnswers, String... presetGuesses){
        String[] answers = givenAnswers;
        String[][] wordPossibilities = new String[][] {wordArray, wordArray, wordArray, wordArray};
        int unknownAnswers = 4;
        String guess = presetGuesses[0];
        int[] guessesNeeded = new int[] {MAX_GUESS_DEFAULT, MAX_GUESS_DEFAULT, MAX_GUESS_DEFAULT, MAX_GUESS_DEFAULT};
        String[] guessLog = new String[9];
        System.arraycopy(presetGuesses, 0, guessLog, 0, presetGuesses.length);
        PossibleWordsLeft[] possibleWordsByRound = new PossibleWordsLeft[9];
        for (int guessNum = 1; guessNum <= 9; guessNum++) {
            char[][] quordleDiagrams = QuordleRoundOutcome.quordleVisual(guess, answers);
            for (int i = 0; i < 4; i++) {
                if (Arrays.equals(quordleDiagrams[i], CORRECT_PATTERN)){
                    guessesNeeded[i] = guessNum;
                    unknownAnswers -= 1;
                    wordPossibilities[i] = new String[0];
                }
                if (guessesNeeded[i] == MAX_GUESS_DEFAULT) {
                    wordPossibilities[i] = GuessOptimizer.possibleAnswerFinder(guess, wordPossibilities[i],
                            quordleDiagrams[i]).toArray(new String[0]);
                }
            }
            if (unknownAnswers == 0) {
                break;
            }
            possibleWordsByRound[guessNum - 1] = new PossibleWordsLeft(wordPossibilities.clone());
            if (guessLog[guessNum] == null) {  //guessNum is offset by 1 from list index count
                guess = GuessOptimizer.nextGuessFinder(wordPossibilities, unknownAnswers);
                guessLog[guessNum] = guess;
            } else {
                guess = guessLog[guessNum];
            }
        }
        return new GameLog(guessLog[0], answers, guessLog, guessesNeeded, possibleWordsByRound);
    }
}
