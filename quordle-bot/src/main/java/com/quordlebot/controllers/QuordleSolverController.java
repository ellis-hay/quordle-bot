package com.quordlebot.controllers;

import com.quordlebot.logic.GameLog;
import com.quordlebot.logic.QuordleBot;
import com.quordlebot.logic.RandomQuordleGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuordleSolverController {

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public GameLog showRandomGame() {
        return QuordleBot.quordleBot(RandomQuordleGenerator.selectRandomGuess(),
                RandomQuordleGenerator.selectRandomAnswers());
    }
}


