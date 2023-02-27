package com.quordlebot;

import java.util.HashMap;
import java.util.Map;

import static com.quordlebot.QuordleBot.wordArray;

public class WordDiagramMapMultithreader implements Runnable{

    private int wordArrayStart;
    private int wordArrayEnd;
    private static volatile Map<String, Map<String, Integer>> wordToWordDiagrams = new HashMap<>();

    public WordDiagramMapMultithreader(int wordArrayStart, int wordArrayEndExclusive){
        this.wordArrayStart = wordArrayStart;
        this.wordArrayEnd = wordArrayEndExclusive;
    }

    @Override
    public void run() {
        System.out.println();
        long start = System.currentTimeMillis();
        for (int i = wordArrayStart; i < wordArrayEnd; i++) {
            Map<String, Integer> stringDiagramMap = new HashMap<>();
            for (String possibleAnswer : wordArray) {
                char[] diagram = GuessOptimizer.getGuessCorrectness(wordArray[i], possibleAnswer);
                int base3representation = GuessOptimizer.diagramToBase3(diagram);
                stringDiagramMap.put(possibleAnswer, base3representation);
            }
            wordToWordDiagrams.put(wordArray[i], stringDiagramMap);
            wordToWordDiagrams.size();
            System.out.println(wordArray[i]);
        }
        long end = System.currentTimeMillis();
        System.out.println("Number started: " + wordArrayStart + " Number end: " + wordArrayEnd + " took " +
        (end - start) + " milliseconds");
    }

    public static Map<String, Map<String, Integer>> getWordToWordDiagrams() {
        return wordToWordDiagrams;
    }
}
