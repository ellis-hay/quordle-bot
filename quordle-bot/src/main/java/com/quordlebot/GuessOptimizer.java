package com.quordlebot;

import java.util.*;

import static com.quordlebot.QuordleBot.wordArray;

public class GuessOptimizer {

    private static final Map<String, Map<String, Integer>> WORD_TO_WORD_DIAGRAM_MAP = wordToWordDiagrams();

    public static char[] getGuessCorrectness (String guess, String answer) {
        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();
        Map<Character, Byte> lettersInAnswer = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            if (guessArray[i] == answerArray[i]) {
                answerArray[i] = '$'; //Green Tile
            } else {
                lettersInAnswer.merge(answerArray[i], (byte) 1, (oldValue, newValue) -> (byte)(oldValue + newValue));
            }
        } for (int i = 0; i < 5; i++) {
            if (answerArray[i] == '$') {
                ;
            }else if (lettersInAnswer.containsKey(guessArray[i]) && lettersInAnswer.get(guessArray[i]) > 0) {
                answerArray[i] = '*'; //Yellow Tile
                lettersInAnswer.put(guessArray[i], (byte) (lettersInAnswer.get(guessArray[i]) - 1));
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

    private static Map<String, Map<String, Integer>> wordToWordDiagrams() {
        Map<String, Map<String, Integer>> wordToWordDiagrams = new HashMap<>();
        for (String possibleGuess : wordArray) {
            Map<String, Integer> stringDiagramMap = new HashMap<>();
            for (String possibleAnswer : wordArray) {
                char[] diagram = getGuessCorrectness(possibleGuess, possibleAnswer);
                int base3representation = diagramToBase3(diagram);
                stringDiagramMap.put(possibleAnswer, base3representation);
            }
            wordToWordDiagrams.put(possibleGuess, stringDiagramMap);
        }
        //System.out.println(wordToWordDiagrams.toString());
        return wordToWordDiagrams;
    }

    private static Map<String, Double> wordByExpectedOutcome(String[][] wordPossibilityArrays, int answersToBeGuessed) {
        Map<String, Double> propensityToShrinkPossibilitiesMap = new HashMap<>();
        for (String possibleGuess : wordArray) {
            double averagePossibilitiesAfterGuess = 0;
            for (int i = 0; i < 4; i++) {
                Set<Integer> possibleDiagrams = new HashSet<>();
                int wordDivisor = wordPossibilityArrays[i].length;
                for (String possibleAnswer : wordPossibilityArrays[i]) {
                    Integer diagram = WORD_TO_WORD_DIAGRAM_MAP.get(possibleGuess).get(possibleAnswer);
                    possibleDiagrams.add(diagram); //Can be a set as currently construed
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
