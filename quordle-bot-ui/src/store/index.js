import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentGuessIndex: 0,
    guessedAllWords: false,
    bombedOut: false,
    currentGuessLetters: ['A', 'B', 'C', 'D', 'E'],
    guesses: [
      ['','','','',''],
      ['','','','',''],
      ['','','','',''],
      ['','','','',''],
      ['','','','',''],
      ['','','','',''],
      ['','','','',''],
      ['','','','',''],
      ['','','','','']
    ]

  },
  getters: {
    updatedGuesses: state =>{
      const currentLetters = [...state.currentGuessLetters];
      while (currentLetters.length < 5){
        currentLetters.push('');
      }
      if (!state.guessedAllWords && !state.bombedOut){
        state.guesses[state.currentGuessIndex] = currentLetters;
      }
      return state.guesses;
    } 
  },
  mutations: {
    ADD_LETTER(state, letter){
      state.currentGuessLetters.push(letter);
    },
    REMOVE_LETTER(state){
      state.currentGuessLetters.pop();
    },
    RESET_CURRENT_LETTERS(state){
      state.currentGuessLetters = [];
    }
  },
  actions: {
  },
  modules: {
  }
})

