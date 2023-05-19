package com.quordlebot.controllers;

import com.quordlebot.logic.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static com.quordlebot.logic.QuordleBot.wordArray;

@CrossOrigin
@RestController
public class QuordleSolverController {

    private final String[] answerAndGuessableWords = WordListReader.totalWordListExtractor();

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public GameLog showRandomGame() {
        return QuordleBot.quordleBot(RandomQuordleGenerator.selectRandomGuess(),
                RandomQuordleGenerator.selectRandomAnswers());
    }

    @RequestMapping(path = "", method = RequestMethod.GET, params={"guesses", "answers"})
    public List<String> showMultiGuessGame(@RequestParam String[] guesses, @RequestParam String[] answers) {
        Arrays.sort(wordArray);
        Arrays.sort(answerAndGuessableWords);
        if (answers.length != 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There must be 4 answer words in query");
        }
        for (String answer : answers) {
            if (Arrays.binarySearch(wordArray, answer) < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Every answer word must be in wordlist");
            }
        }
        for (String guess: guesses) {
            if (Arrays.binarySearch(answerAndGuessableWords, guess) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If entered, guesses must be valid");
            }
        }
        return MultiGuessQuordleBot.quordleBot(answers, guesses).getGuessLog();
    }

    @RequestMapping(path = "", method = RequestMethod.GET, params={"guess", "answers"})
    public GameLog showSpecificGame(@RequestParam(defaultValue = "") String guess, @RequestParam String[] answers) {
        GameLog gameLog;
        Arrays.sort(wordArray);
        Arrays.sort(answerAndGuessableWords);
        if (answers.length != 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There must be 4 answer words in query");
        }
        for (String answer : answers) {
            if (Arrays.binarySearch(wordArray, answer) < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Every answer word must be in wordlist");
            }
        } if (guess.isEmpty()) {
            gameLog = QuordleBot.quordleBot("trace", answers);
        } else if (Arrays.binarySearch(answerAndGuessableWords, guess) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "If entered, guess must be valid");
        } else {
            gameLog = QuordleBot.quordleBot(guess, answers);
        }
        return gameLog;
    }
}


