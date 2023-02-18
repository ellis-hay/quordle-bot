package com.quordlebot;

import java.util.*;

public final class GuessOptimizer {

    private static final Map<Integer, Double> INT_PERCENTAGE_TABLE = hundredthsToOutputConversion();

    private static Set<String> individualWordLetterCount (String word) {
        Set<String> letterCount = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            if (letterCount.contains(word.charAt(i) + "2")) {
                letterCount.add(word.charAt(i) + "3");
            } else if (letterCount.contains(String.valueOf(word.charAt(i)))) {
                letterCount.add(word.charAt(i) + "2");
            } else {
                letterCount.add(String.valueOf(word.charAt(i)));
            }
        }
        return letterCount;
    }

    private static Set<String> individualWordLetterPlacement (String word) {
        Set<String> placementAndLetter = new HashSet<> ();
        for (int i = 0; i < 5; i++) {
            placementAndLetter.add(i + "" + word.charAt(i));
        }
        return placementAndLetter;
    }

    public static Map<String, Set<String>> allWordsLetterCountMap (String[] wordArray) {
        Map<String, Set<String>> wordsAndLetters = new HashMap<>();
        for (String word : wordArray) {
            Set<String> letterFrequency = individualWordLetterCount(word);
            wordsAndLetters.put(word, letterFrequency);
        }
        return wordsAndLetters;
    }

    public static Map<String, Set<String>> allWordsLetterPlacementMap (String[] wordArray) {
        Map<String, Set<String>> wordsAndLetterPlacement = new HashMap<>();
        for (String word : wordArray) {
            Set<String> letterFrequency = individualWordLetterPlacement(word);
            wordsAndLetterPlacement.put(word, letterFrequency);
        }
        return wordsAndLetterPlacement;
    }

    public static Map<String, Integer> letterFrequencyInWordArray (String[] wordArraySubset,
                                                                   Map<String, Set<String>> wordsAndLettersMap) {
        Map<String, Integer> letterFrequency = new HashMap<>();
        for (String word : wordArraySubset) {
            for (String letter : wordsAndLettersMap.get(word)) {
                letterFrequency.merge(letter, 1, Integer::sum);
            }
        }
        return letterFrequency;
    }

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
        } return answerArray;
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

    private static Map<Integer, Double> hundredthsToOutputConversion() {
        Map<Integer, Double> hundredthsTable = new HashMap<>();
        for (double i = 0; i <= 101 ; i++) {
            hundredthsTable.put((int) i, (-Math.abs((i/50) - 1) + 1));
        }
        return hundredthsTable;
    }

    private static double unweightedChanceOfBeingWord (String[] possibleWords) {
        return (double) 1/(possibleWords.length);
    }

    private static Map<String, Integer> letterFrequencyInPossibleWords (String[] wordPossibilities, Map<String, Set<String>>
            allWordsLetterCountMap, Map<String, Set<String>> allWordsLetterPlacementMap) {
        Map<String, Integer> letterFrequencyInPossibilityArray = new HashMap<>();
        for (String word: wordPossibilities) {
            Set<String> letterOccurrenceSet = allWordsLetterCountMap.get(word);
            Set<String> letterPlacementSet = allWordsLetterPlacementMap.get(word);
            for (String letterOccurrence : letterOccurrenceSet) {
                letterFrequencyInPossibilityArray.merge(letterOccurrence, 1, Integer::sum);
            } for (String placeAndLetter : letterPlacementSet) {
                letterFrequencyInPossibilityArray.merge(placeAndLetter, 1, Integer::sum);
            }
        }
        return letterFrequencyInPossibilityArray;
    }

    private static Map<String, Double> letterImportanceInPossibleWords (Map<String, Integer> letterFrequencies,
                                                                        double wordImportanceModifier,
                                                                        double percentageMultiplier) {
        Map<String, Double> letterImportances = new HashMap<>();
        for (Map.Entry<String, Integer> letterFrequency : letterFrequencies.entrySet()) {
            int letterAppearancePercentage = (int) (letterFrequency.getValue() * percentageMultiplier);
            double frequencyConversion = INT_PERCENTAGE_TABLE.get(letterAppearancePercentage);
            double letterImportance = frequencyConversion * wordImportanceModifier;
            letterImportances.put(letterFrequency.getKey(), letterImportance);
        }
        return letterImportances;
    }

    private static Map<String, Double> helpfulnessScoreGenerator (Map<String, Set<String>> allWordsLetterCountMap,
                                                                  Map<String, Double> letterImportanceWeights) {
        Map<String, Double> allWordHelpfulnessScores = new HashMap<>();
        for (Map.Entry<String, Set<String>> wordAndLetterOccurrenceSet : allWordsLetterCountMap.entrySet()) {
            double individualWordHelpfulnessScore = 0;
            for (String letterOccurrence : wordAndLetterOccurrenceSet.getValue()) {
                if (letterImportanceWeights.containsKey(letterOccurrence)) {
                    individualWordHelpfulnessScore += letterImportanceWeights.get(letterOccurrence);
                }
            }
            allWordHelpfulnessScores.put(wordAndLetterOccurrenceSet.getKey(), individualWordHelpfulnessScore);
        }
        return allWordHelpfulnessScores;
    }

    public static String nextGuessFinder (String[][] wordPossibilityArrays, Map<String, Set<String>>
            allWordsLetterCountMap, Map<String, Set<String>> allWordsLetterPlacementMap) {
        for (String[] possibilityArray: wordPossibilityArrays) {
            if (possibilityArray.length == 1) {
                return possibilityArray[0];
            }
        }
        Map<String, Double> allLetterImportanceWeights = new HashMap<>();
        for (String[] possibilityArray: wordPossibilityArrays) {
            if (possibilityArray.length == 0) {
                continue;
            }
            double wordLikelihoodModifier = unweightedChanceOfBeingWord(possibilityArray);
            double toPercentageMultiplier = (double) 100/possibilityArray.length;
            Map<String, Integer> letterFrequency = letterFrequencyInPossibleWords(possibilityArray,
                    allWordsLetterCountMap, allWordsLetterPlacementMap);
            Map<String, Double> letterImportances = letterImportanceInPossibleWords(letterFrequency,
                    wordLikelihoodModifier, toPercentageMultiplier);
            for (Map.Entry<String, Double> letterImportance : letterImportances.entrySet()) {
                allLetterImportanceWeights.merge(letterImportance.getKey(), letterImportance.getValue(), Double::sum);
            }
        }
        Map<String, Double> wordHelpfulnessScores = helpfulnessScoreGenerator(allWordsLetterCountMap,
                allLetterImportanceWeights);
        String maxWord = null;
        double maxWordScore = 0;
        for (Map.Entry<String, Double> wordAndScore : wordHelpfulnessScores.entrySet()) {
            if (wordAndScore.getValue() > maxWordScore) {
                maxWord = wordAndScore.getKey();
                maxWordScore = wordAndScore.getValue();
            }
        }
        if (maxWord == null) {
            for (String[] possibilityArray: wordPossibilityArrays) {
                if (possibilityArray.length > 0){
                    return possibilityArray[0];
                }
            }
        }
        return maxWord;
    }

}
