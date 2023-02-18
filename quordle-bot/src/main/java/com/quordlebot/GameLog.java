package com.quordlebot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLog implements Serializable {

    private String startingWord;
    private String[] answers;
    private String[] guessLog;
    private int[] guessesByWord;

    public GameLog(String startingWord, String[] answers, String[] guessLog, int[] guessesByWord) {
        this.startingWord = startingWord;
        this.answers = answers;
        this.guessLog = guessLog;
        this.guessesByWord = guessesByWord;
    }

    public String getStartingWord() {
        return startingWord;
    }

    public String[] getAnswers() {
        return answers;
    }

    public List<String> getGuessLog() {
        List<String> guesses = new ArrayList<>();
        for (String guess : guessLog) {
            if (guess != null) {
                guesses.add(guess);
            }
        }
        return guesses;
    }

    public String getGuessesByWord() {
        return Arrays.toString(guessesByWord);
    }

    public int getNumberBombedOut() {
        int numberBombedOut = 0;
        final int BOMB_OUT_SIGNIFIER = 15;
        for (int i = 0; i < 4; i++) {
            if (guessesByWord[i] == BOMB_OUT_SIGNIFIER) {
                numberBombedOut += 1;
            }
        }
        return numberBombedOut;
    }

    public boolean didBombOut() {
        return getNumberBombedOut() >= 1;
    }

    public int getTotalGuesses() {
        int totalGuesses = 0;
        for (int i = 0; i < 4; i++) {
            totalGuesses += guessesByWord[i];
        }
        return totalGuesses;
    }

}
