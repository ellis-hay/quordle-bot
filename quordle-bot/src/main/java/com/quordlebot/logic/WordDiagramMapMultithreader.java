package com.quordlebot.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.quordlebot.logic.QuordleBot.wordArray;

public class WordDiagramMapMultithreader implements Runnable{

    private int wordArrayStart;
    private int wordArrayEnd;
    private static volatile ConcurrentMap<String, Map<String, Integer>> wordToWordDiagrams = new ConcurrentHashMap<>();

    public WordDiagramMapMultithreader(int wordArrayStart, int wordArrayEndExclusive){
        this.wordArrayStart = wordArrayStart;
        this.wordArrayEnd = wordArrayEndExclusive;
    }

    @Override
    public void run() {
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
        }
    }

    public static ConcurrentMap<String, Map<String, Integer>> getWordToWordDiagrams() {
        return wordToWordDiagrams;
    }
}
