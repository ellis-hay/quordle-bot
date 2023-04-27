package com.quordlebot.logic;

import java.util.Arrays;

public class QuordleBot {
    public static String[] wordArray = WordListReader.answerWordListExtractor();
    static final char[] CORRECT_PATTERN = new char[] {'$', '$', '$', '$', '$'};
    static final int MAX_GUESS_DEFAULT = 15;

    public static void main(String[] args) {
        GameLog guesses = quordleBot("start", new String[]{"begin", "wordy", "event", "gamer"});
        System.out.println(guesses.getGuessLog() + " " + guesses.getGuessesByWord());
    }

    public static GameLog quordleBot(String givenGuess, String[] givenAnswers){
        String guess = givenGuess;
        String[] answers = givenAnswers;
        String[][] wordPossibilities = new String[][] {wordArray, wordArray, wordArray, wordArray};
        int unknownAnswers = 4;
        int[] guessesNeeded = new int[] {MAX_GUESS_DEFAULT, MAX_GUESS_DEFAULT, MAX_GUESS_DEFAULT, MAX_GUESS_DEFAULT};
        String[] guessLog = new String[9];
        PossibleWordsLeft[] possibleWordsByRound = new PossibleWordsLeft[9];
        for (int guessNum = 1; guessNum <= 9; guessNum++) {
            guessLog[guessNum - 1] = guess;
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
            guess = GuessOptimizer.nextGuessFinder(wordPossibilities, unknownAnswers);
        }
        return new GameLog(givenGuess, answers, guessLog, guessesNeeded, possibleWordsByRound);
    }
}

