package com.quordlebot;

import org.junit.Assert;
import org.junit.Test;

import static com.quordlebot.QuordleBot.wordArray;

public class PossibleWordsLeftTest {
    @Test
    public void toStringPWLReturnsLongArraysCorrectly(){
        PossibleWordsLeft pwl = new PossibleWordsLeft(new String[][]{wordArray, wordArray, wordArray, wordArray});
        String pwlAsString = pwl.toString();
        Assert.assertTrue(pwlAsString.length() > wordArray.length * 6 * 4);
    }

    @Test
    public void toStringPWLDoesNotIncludeEmptyArrays(){
        PossibleWordsLeft pwl = new PossibleWordsLeft(new String[][]{{},{}, {}, wordArray});
        String pwlAsString = pwl.toString();
        Assert.assertFalse(pwlAsString.contains("Word 1 Possibilities:"));
        Assert.assertFalse(pwlAsString.contains("Word 2 Possibilities:"));
        Assert.assertFalse(pwlAsString.contains("Word 3 Possibilities:"));
        Assert.assertTrue(pwlAsString.contains("Word 4 Possibilities:"));
    }
}
