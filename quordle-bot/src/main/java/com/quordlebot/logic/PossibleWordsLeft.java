package com.quordlebot.logic;

import java.io.Serializable;

public class PossibleWordsLeft implements Serializable {
    private final String STRING_OPEN = "Possible Words:\n";
    private final int WORD_PLUS_NEW_LINE_LENGTH = 6;
    private final int EXTRA_APPENDAGES_CAPACITY = 92;
    private String[][] wordsLeft;

    public PossibleWordsLeft(String[][] wordsLeft) {
        this.wordsLeft = wordsLeft;
    }

    @Override
    public String toString() {
        int stringBuilderCapacity = STRING_OPEN.length() + (wordsLeft[0].length + wordsLeft[1].length
                + wordsLeft[2].length + wordsLeft[3].length) * WORD_PLUS_NEW_LINE_LENGTH + EXTRA_APPENDAGES_CAPACITY;
        StringBuilder stringBuilder = new StringBuilder(stringBuilderCapacity);
        stringBuilder.append(STRING_OPEN);
        for (int i = 0; i < 4; i++) {
            if (wordsLeft[i].length > 0) {
                stringBuilder.append("Word " + (i + 1) + " Possibilities:\n");
                for (int j = 0; j <wordsLeft[i].length; j++){
                    stringBuilder.append(wordsLeft[i][j]).append("\n");
                }
            }
        }
        return stringBuilder.toString();
    }
}
