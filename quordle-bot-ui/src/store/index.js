import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentGuessNumber: 1,
    guessed: false,
    bombedOut: false,
    currentGuessLetters: [],
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
