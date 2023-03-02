package com.quordlebot.logic;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.quordlebot.logic.QuordleBot.wordArray;

public final class GuessOptimizer {

    private static final ThreadLocal<ConcurrentMap<String, Map<String, Integer>>> WORD_TO_WORD_DIAGRAM_MAP =
            ThreadLocal.withInitial(GuessOptimizer::wordToWordDiagrams);

    public static char[] getGuessCorrectness (String guess, String answer) {
        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();
        Map<Character, Integer> lettersInAnswer = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            if (guessArray[i] == answerArray[i]) {
                answerArray[i] = '$'; //Green Tile
            } else {
                lettersInAnswer.merge(answerArray[i],  1, Integer::sum);
            }
        } for (int i = 0; i < 5; i++) {
            if (answerArray[i] == '$') {
                ;
            }else if (lettersInAnswer.containsKey(guessArray[i]) && lettersInAnswer.get(guessArray[i]) > 0) {
                answerArray[i] = '*'; //Yellow Tile
                lettersInAnswer.put(guessArray[i], (lettersInAnswer.get(guessArray[i]) - 1));
            } else {
                answerArray[i] = '_'; //Blank
            }
        }
        return answerArray;
    }

    public static List<String> possibleAnswerFinder (String guess, String[] wordArray, char[] guessDiagram){
        List<String> possibleAnswers = new ArrayList<>();
        for(String word: wordArray){
            char[] guessVsWord = getGuessCorrectness(guess, word);
            if (Arrays.equals(guessVsWord, guessDiagram)){
                possibleAnswers.add(word);
            }
        }
        return possibleAnswers;
    }

    public static int diagramToBase3(char[] diagram) {
        for (int i = 0; i < 5; i++) {
            if (diagram[i] == '$'){
                diagram[i] = '3';
            } else if (diagram[i] == '*'){
                diagram[i] = '2';
            } else {
                diagram[i] = '1';
            }
        }
        return Integer.parseInt(String.valueOf(diagram));
    }

    private static ConcurrentMap<String, Map<String, Integer>> wordToWordDiagrams() {
        int coresAvailable = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(coresAvailable);
        int indexRange = wordArray.length / coresAvailable;
        for (int i = 0; i < coresAvailable; i++) {
            int startIndex = i * indexRange;
            int endIndex = startIndex + indexRange;
            if (i == (coresAvailable - 1))
                endIndex = 2309;
            executorService.execute(new WordDiagramMapMultithreader(startIndex, endIndex));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignored) {
            System.out.println("A thread interruption occurred"); //Should not reach this code
        }
        return WordDiagramMapMultithreader.getWordToWordDiagrams();
    }

    private static Map<String, Double> wordByExpectedOutcome(String[][] wordPossibilityArrays, int answersToBeGuessed) {
        long start = System.currentTimeMillis();
        Map<String, Double> propensityToShrinkPossibilitiesMap = new HashMap<>();
        for (String possibleGuess : wordArray) {
            double averagePossibilitiesAfterGuess = 0;
            for (int i = 0; i < 4; i++) {
                Set<Integer> possibleDiagrams = new HashSet<>();
                int wordDivisor = wordPossibilityArrays[i].length;
                for (String possibleAnswer : wordPossibilityArrays[i]) {
                    Integer diagram = WORD_TO_WORD_DIAGRAM_MAP.get().get(possibleGuess).get(possibleAnswer);
                    possibleDiagrams.add(diagram);
                    if (diagram.equals(33333)) {
                        wordDivisor -= answersToBeGuessed;
                    }
                }
                if (possibleDiagrams.size() > 0) {
                    averagePossibilitiesAfterGuess += (double) wordDivisor / possibleDiagrams.size();
                }
            }
            propensityToShrinkPossibilitiesMap.put(possibleGuess, averagePossibilitiesAfterGuess);
        }
        return propensityToShrinkPossibilitiesMap;
    }

    public static String nextGuessFinder (String[][] wordPossibilityArrays, int answersLeftToBeGuessed) {
        for (String[] possibilityArray : wordPossibilityArrays) {
            if (possibilityArray.length == 1) {
                return possibilityArray[0];
            }
        }
        Map<String, Double> expectedOutcomes = wordByExpectedOutcome(wordPossibilityArrays, answersLeftToBeGuessed);
        String bestWord = null;
        double bestOverallExpectedOutcome = 2309;
        for (Map.Entry<String, Double> wordAndScore : expectedOutcomes.entrySet()) {
            if (wordAndScore.getValue() < bestOverallExpectedOutcome) {
                bestWord = wordAndScore.getKey();
                bestOverallExpectedOutcome = wordAndScore.getValue();
            } if (wordAndScore.getValue() == bestOverallExpectedOutcome) {
                bestWord = wordAndScore.getKey();
            }
        }
        return bestWord;
    }

}
