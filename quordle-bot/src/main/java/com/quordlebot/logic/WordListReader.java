package com.quordlebot.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class WordListReader {
    public static String[] answerWordListExtractor() {
        try {
            File wordleWordsFile = new File("src/main/Wordlists/wordle_words.txt");
            Scanner myReader = new Scanner(wordleWordsFile);
            String dataString = myReader.nextLine();
            String[] wordArray = dataString.replaceAll("\"", "").split(", ");
            myReader.close();
            return wordArray;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    private static String[] guessableWordListExtractor() {
        try {
            File wordleWordsFile = new File("src/main/Wordlists/quordle_guessable.txt");
            Scanner myReader = new Scanner(wordleWordsFile);
            String dataString = myReader.nextLine();
            String[] wordArray = dataString.split(" ");
            myReader.close();
            return wordArray;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public static String[] totalWordListExtractor() {
        String[] array1 = answerWordListExtractor();
        String[] array2 = guessableWordListExtractor();
        String[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}



