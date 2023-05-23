<template>
<div id="root">
  <middle-main id="middle-main"/>
  <guess-area v-for="n in zeroOrFour('real')" :key = "'GA' + n" :word-number = "n" :id="'grid' + n"/>
  <dummy-guess-area v-for="n in zeroOrFour('dummy')" :key = "'GA' + n" :word-number = "n" :id="'grid' + n"/>
  <word-list-area v-for="n in 4" :key = "'WLA' + n" :word-number = "n" :id="'word-list-area' + n"/>
  <keyboard id="keyboard"/>
  <score-tally/>
</div>
</template>

<script>
import GuessArea from './components/GuessArea.vue';
import Keyboard from './components/QuordleKeyboard.vue';
import MiddleMain from './components/MiddleMain.vue';
import WordListArea from './components/WordListArea.vue';
import ScoreTally from './components/ScoreTally.vue';
import DummyGuessArea from './components/DummyGuessArea.vue';

export default {
  components: {
    GuessArea,
    Keyboard,
    MiddleMain,
    WordListArea,
    ScoreTally,
    DummyGuessArea
  },
  computed: {
    
  },
  methods: {
    addTypedLetter(event) {
      if (this.$store.state.currentGuessLetters.length < 5  && event.which > 64 && event.which < 91){ //65=A 90=Z
        this.$store.commit('ADD_LETTER', event.key.toUpperCase());
      } else if (event.which == 8) { //Backspace
        this.$store.commit('REMOVE_LETTER');
      } else if (event.which == 13) { //Enter
        event.stopPropagation();
        event.preventDefault();
        this.$store.dispatch('guessWord');
      }
    },
    zeroOrFour(dummyOrReal) {
      if ((dummyOrReal == "real") != this.$store.state.showDummy) {
        return 4;
      } else {
        return 0;
      }
    }
  },
  mounted() {
    let self = this; 
    window.addEventListener('keydown', function(event) {
      if (event.repeat) return;
      self.addTypedLetter(event);
    });
    this.$store.subscribe((mutation, state) => {
      localStorage.setItem('store', JSON.stringify(state));
      localStorage.setItem('computerComparisonMap', JSON.stringify(Array.from(this.$store.state.computerComparisonByHumanRound.entries())))
    });
  },
  created() {
    if (!Object.keys(this.$store.state.currentGameInfo).length) {
      this.$store.dispatch('logGameInfo');
    }
  }
}
</script>

<style>
html {
    font-family: 'Questrial', sans-serif;
}

body {
  height: 100vh;
  background-color: 	#EFE7DA;
  margin: 0;
}

#root{
    height: 100%;
    display: grid;
    grid-template-columns: 1fr 265px 265px 1fr;
    grid-template-rows: 1fr 3.75fr 3.75fr;
    grid-template-areas:
    "ga1 mm mm ga2"
    "ga1 wla1 wla2 ga2"
    "ga3 wla3 wla4 ga4"
    "ga3 keyboard keyboard ga4"
}

#middle-main{
  grid-area: mm;
  align-self: flex-start;
  justify-self: center;
}

#grid1, #word-list-area1{
  background-color: #b11e23;
}

#grid1{
  grid-area: ga1;
}

#word-list-area1{
  grid-area: wla1;
}

#grid2, #word-list-area2{
  background-color: #12357a;
}

#grid2{
  grid-area: ga2;
}

#word-list-area2{
  grid-area: wla2;
}

#grid3, #word-list-area3{
  background-color: #1d1d1b;
}

#grid3{
  grid-area: ga3;
}

#word-list-area3{
  grid-area: wla3;
}

#grid4, #word-list-area4{
  background-color: #e29726;
}

#grid4{
  grid-area: ga4;
}

#word-list-area4{
  grid-area: wla4;
}

#keyboard{
  grid-area: keyboard;
  max-width: 530px;
  display: flex;
  flex-direction: column;
}

@media only screen and (max-width: 820px){
  #root{
    grid-template-columns: 1fr 150px 150px 1fr;
    grid-template-rows: 1fr 3.75fr 3.75fr;
    grid-template-areas:
    "mm mm mm mm"
    "ga1 wla1 wla2 ga2"
    "ga3 wla3 wla4 ga4"
    "keyboard keyboard keyboard keyboard"
  }

  #keyboard {
    justify-self: center;
  }
}

</style>

