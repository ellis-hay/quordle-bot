package com.quordlebot.logic;

import java.util.Arrays;

public class QuordleBot {
    static WordListReader wordListReader = new WordListReader();
    static String[] wordArray = wordListReader.wordListExtractor();
    static final char[] CORRECT_PATTERN = new char[] {'$', '$', '$', '$', '$'};
    static final int DEFAULT = 15;

    public static void main(String[] args) {
        GameLog guesses = quordleBot("loamy", new String[] {"musky", "froze", "guild", "maple"});
        System.out.println(guesses.getGuessLog() +" "+ guesses.getGuessesByWord());

        /*long start = System.currentTimeMillis();
        GameLog[] results = new GameLog[wordArray.length * wordArray.length/4];
        for(int i = 0; i < wordArray.length; i++) {
            for(int j = 0; j < 1; j += 4) {
                results[i] = quordleBot(wordArray[i], new String[]{wordArray[j], wordArray[j+1], wordArray[j+2], wordArray[j+3]});
            }
            System.out.println(wordArray[i]+ " (" + i +") done");
        }

        //long start = System.currentTimeMillis();
        //GameLog[] results = new GameLog[wordArray.length];
        int cumulativeScores = 0;
        List<Integer> bombedOut = new ArrayList<>();
        for(int i = 0; i < wordArray.length; i++) {
            results[i] = quordleBot(wordArray[i], new String[]{wordArray[(4*i)%2309], wordArray[(4*i+1)%2309],
                    wordArray[(4*i+2)%2309], wordArray[(4*i+3)%2309]});
            System.out.println(wordArray[i]+ " (" + i +") done");
            cumulativeScores += results[i].getTotalGuesses();
            if (results[i].didBombOut()) {
                bombedOut.add(i);
            }
        }
        System.out.println((double) cumulativeScores/wordArray.length);
        for (Integer integer : bombedOut) {
            System.out.println(integer + "was bombed out");
        }
        long end = System.currentTimeMillis();
        long totalTime = end - start;
        System.out.println("that took" + totalTime + "ms");*/

//        GameLog guesses= quordleBot("snort", new String[] {"prose", "leach", "flute", "folio"});
//        System.out.println(guesses.getGuessLog() +" "+ guesses.getGuessesByWord());

//        GameLog guesses = quordleBot(RandomQuordleGenerator.selectRandomGuess(), RandomQuordleGenerator.selectRandomAnswers());
//        System.out.println(guesses.getGuessLog() + " " + guesses.getGuessesByWord());
    }

    public static GameLog quordleBot(String givenGuess, String[] givenAnswers){
        String guess = givenGuess;
        String[] answers = givenAnswers;
        String[][] wordPossibilities = new String[][] {wordArray, wordArray, wordArray, wordArray};
        int unknownAnswers = 4;
        int[] guessesNeeded = new int[] {DEFAULT, DEFAULT, DEFAULT, DEFAULT};
        String[] guessLog = new String[9];
        PossibleWordsLeft[] possibleWordsByRound = new PossibleWordsLeft[9];
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
                }
                if (guessesNeeded[i] == DEFAULT) {
                    wordPossibilities[i] = GuessOptimizer.possibleAnswerFinder(guess, wordPossibilities[i],
                            quordleDiagrams[i]).toArray(new String[0]);
                }
            }
            if (unknownAnswers == 0) {
                break;
            }
            possibleWordsByRound[guessNum - 1] = new PossibleWordsLeft(wordPossibilities);
            guess = GuessOptimizer.nextGuessFinder(wordPossibilities, unknownAnswers);
        }
        return new GameLog(givenGuess, answers, guessLog, guessesNeeded, possibleWordsByRound);
    }
}

