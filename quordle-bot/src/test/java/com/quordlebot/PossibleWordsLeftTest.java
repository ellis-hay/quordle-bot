/*package com.quordlebot.logic;

import org.junit.jupiter.api.*;



import static com.quordlebot.logic.QuordleBot.wordArray;

public class PossibleWordsLeftTest {
    @Test
    public void toStringPWLReturnsLongArraysCorrectly(){
        PossibleWordsLeft pwl = new PossibleWordsLeft(new String[][]{wordArray, wordArray, wordArray, wordArray});
        String pwlAsString = pwl.toString();
        AssertTrue(pwlAsString.length() > wordArray.length * 6 * 4);
    }

    @Test
    public void toStringPWLDoesNotIncludeEmptyArrays(){
        PossibleWordsLeft pwl = new PossibleWordsLeft(new String[][]{{},{}, {}, wordArray});
        String pwlAsString = pwl.toString();
        AssertFalse(pwlAsString.contains("Word 1 Possibilities:"));
        Assert.assertFalse(pwlAsString.contains("Word 2 Possibilities:"));
        Assert.assertFalse(pwlAsString.contains("Word 3 Possibilities:"));
        Assert.assertTrue(pwlAsString.contains("Word 4 Possibilities:"));
    }
}*/
