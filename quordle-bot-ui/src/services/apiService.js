import axios from 'axios';

const http = axios.create({
    baseURL: "http://localhost:8080"
  });

  export default {

    randomBoard() {
      return http.get('/random');
    },

    multiGuessComparator(guessArray, answerArray) {
      return http.get('', {params: {guesses:guessArray.join(','), answers:answerArray.join(',')}});
    }
}