package com.quordlebot.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordListReader {
    public String[] wordListExtractor() {
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
}



