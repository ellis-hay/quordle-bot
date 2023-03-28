# Quordle Bot

## I. Introduction and Technologies Used
  
At the heart of this application is an algorithm meant to solve Quordle puzzles in as few guesses as possible.  For those unaware of what Quordle is, Quordle is like Wordle, but with four words to guess instead of just 1- and for those who missed the Wordle craze in early 2022, Wordle is a game where you try to guess a 5-letter word in 6 guesses.  If a letter is in the correct spot, it appears green, yellow means it's in the word, but in the wrong location, and gray means it's not in the word*.   Quordle takes this concept and expands it to four words and nine guesses.

This application is written in Java and uses the Spring Boot framework to provide a RESTful API.  Additionally, I am working on a front end in HTML/CSS which would allow users to play a game against the computer in which they are given a starting guess and try to guess the four answer words in fewer guesses than it takes the computer.  Unlike in Quordle or Wordle however, the user is provided a list of possible words for each answer slot.  That way, the user can focus on trying to choose the most strategically helpful words.  Quordle and Wordle have roughly 2300 words that could be answers, but there's another 10000 that are guessable, but won't ever be the answer.  The computer can easily pare down the list of possible words with the information provided by each guess, so giving a human that same pared-down list helps level the playing field.
	
*with a caveat that if a letter appears multiple times in a guess, it is only green or yellow if the letter appears multiple times in the answer

## II. Background
In mid-January I wrapped up coding my first project, a Monopoly game in Python.  It was a bit of a bear, but I learned a lot from it, in part because I didn't take short cuts.  I included all the rules because my goal was to find ways to make everything work as it would in real life, even down to the obscure "What happens when a player goes bankrupt to multiple other players?"  All that being said, for my next project, I decided to do something less rigid.  I started working on a Quordle Bot in mid-January in part after reading an article about making a basic Wordle guesser and because I knew there was an official WordleBot, so I thought I could riff off that idea.  I first wrote it in Python, being that was the only language I knew then, and it was relatively slow, at least when iterating over hundreds of puzzles.  Later, I wrote it in Java with some tweaks which was faster by orders of magnitude.  To go through 577 puzzles (2309 / 4) for all 2309 words took 55 minutes compared to an estimated 4 days in Python.

At the base of Quordle Bot, and I imagine any Wordle solver, is a method that compares a guess to the correct answer, so if you guess PUPIL and the word is APPLY, you get 🟨⬛🟩⬛🟩, or whatever character or integer is used to represent green, yellow and blank.  Additionally, there must be a method to find words that match that guess and diagram, so if all the words were possible before guessing PUPIL, this method shows that only APPLE and APPLY are possibilities if the diagram looks like 🟨⬛🟩⬛🟩.  From there is the jumping-off point where the algorithm that chooses the next word begins.

In both the Python version and the initial Java version, this algorithm worked by looking through the list of possible words for each unguessed answer, totaling the appearances of letters in a dictionary/map, then weighing those totals based on different logical assumptions.  This worked reasonably well, but for all its improvement in time, the Java version scored worse than the Python one, averaging 21.81 guesses to 21.37 guesses, and even with tweaks both showed obvious flaws.  To address that, I made a new version, with guesses based mainly on their likelihood to decrease the word possibilities left, and that is about where the Git commit history picks up.  This approach relies on a map within a map- essentially a complete weighted graph -which connects every word to every other word via a diagram.  It is slower than the previous algorithm, as it requires a nested loop, but that extra time isn't noticeable with just a single puzzle and the speed trade-off is certainly acceptable as it averages 20.85 guesses across the same puzzle data set as referenced earlier.  Additionally, I used multithreading to speed up the creation of the word-diagram map that's created when the program is run.

## III. How to Use
To run the Quordle Bot, you have two main options:

(A) Go to the QuordleBot class and run the main method.  The following should print to the console:

	[start, deign, begin, murky, wordy, event, gamer] [3, 5, 6, 7]*

Inside the main method, you'll see that the quordleBot() method is called and given a first guess ("start") and an array of four answers {"begin", "wordy", "event", "gamer"}.  The int[] that prints corresponds to the number of guesses it takes for the computer to guess each word.  These words can be replaced with other words, so long as those words are in src/main/Wordlists/wordle_words.txt*.  There is also additional information about a Quordle puzzle that can be gleaned from methods found in the GameLog class.

(B) Consume the API by going to:

	http://localhost:8080/random 
which gives a game with a random start and answer words, or use search parameters à la:

	http://localhost:8080?guess=brown&answers=black&answers=green&answers=azure&answers=white
*Exact log may change as tweaks are made to the algorithm.
**This is a list of 2309 words that could be answers that I took from a Wordle solver project on GitHub, which I imagine the authors got directly from the Wordle source code.  There is also a quordle_words.txt file in the Wordlists folder, which I found in the JavaScript files of Quordle.com and differs very slightly from the Wordle list.  The Wordle list is seemingly unsorted, which was preferable to the alphabetized Quordle list.