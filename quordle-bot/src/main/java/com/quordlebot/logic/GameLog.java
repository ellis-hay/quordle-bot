package com.quordlebot.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLog implements Serializable {

    private String startingWord;
    private String[] answers;
    private String[] guessLog;
    private int[] guessesByWord;
    private PossibleWordsLeft[] possibilitiesByRound;

    public GameLog(String startingWord, String[] answers, String[] guessLog, int[] guessesByWord,
                   PossibleWordsLeft[] possibilitiesByRound) {
        this.startingWord = startingWord;
        this.answers = answers;
        this.guessLog = guessLog;
        this.guessesByWord = guessesByWord;
        this.possibilitiesByRound = possibilitiesByRound;
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

    public List<String[][]> getRoundPossibilities() {
        List<String[][]> roundPossibilities = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            if (possibilitiesByRound[i] == null){
                break;
            }
            roundPossibilities.add(possibilitiesByRound[i].getWordsLeft());
        }
        return roundPossibilities;
    }

    public String getPossibleWordsStringByRound(int roundNumber) {
        if (possibilitiesByRound[roundNumber] != null) {
            return possibilitiesByRound[roundNumber].toString();
        }
        return "Round does not exist";
    }



    public void setStartingWord(String startingWord) {
        this.startingWord = startingWord;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setGuessLog(String[] guessLog) {
        this.guessLog = guessLog;
    }

    public void setGuessesByWord(int[] guessesByWord) {
        this.guessesByWord = guessesByWord;
    }
}
