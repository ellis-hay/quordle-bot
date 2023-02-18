package com.quordlebot;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class QuordleBot {
    static WordListReader wordListReader = new WordListReader();
    static String[] wordArray = wordListReader.wordListExtractor();
    static Map<String, Set<String>> wordsAndLettersMap = GuessOptimizer.allWordsLetterCountMap(wordArray);
    static Map<String, Set<String>> wordsAndLetterPlacementMap = GuessOptimizer.allWordsLetterPlacementMap(wordArray);
    static final char[] CORRECT_PATTERN = new char[] {'$', '$', '$', '$', '$'};
    static final int DEFAULT = 15;

    public static void main(String[] args) {
        GameLog guesses = quordleBot(RandomQuordleGenerator.selectGuessIndex(), RandomQuordleGenerator.selectRandomAnswers());
        System.out.println(guesses.getGuessLog() + " " + guesses.getGuessesByWord());
    }


    public static GameLog quordleBot(String givenGuess, String[] givenAnswers){
        String guess = givenGuess;
        String[] answers = givenAnswers;
        String[][] wordPossibilities = new String[][] {wordArray, wordArray, wordArray, wordArray};
        int unknownAnswers = 4;
        int[] guessesNeeded = new int[] {DEFAULT, DEFAULT, DEFAULT, DEFAULT};
        String[] guessLog = new String[9];
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
                } if (guessesNeeded[i] == DEFAULT) {
                    wordPossibilities[i] = GuessOptimizer.possibleAnswerFinder(guess, wordPossibilities[i],
                            quordleDiagrams[i]).toArray(new String[0]);
                }
            } if (unknownAnswers == 0) {
                break;
            } guess = GuessOptimizer.nextGuessFinder(wordPossibilities, wordsAndLettersMap, wordsAndLetterPlacementMap);
        }
        return new GameLog(givenGuess, answers, guessLog, guessesNeeded);
    }
}
