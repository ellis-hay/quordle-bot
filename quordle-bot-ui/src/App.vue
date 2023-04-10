<template>
<body>
  <middle-main id="middle-main"/>
  <guess-area v-for="n in 4" :key = "'GA' + n" :word-number = "n" :id="'grid' + n"/>
  <word-list-area v-for="n in 4" :key = "'WLA' + n" :word-number = "n" :id="'word-list-area' + n"/>
  <keyboard id="keyboard"/>
</body>
</template>

<script>
import GuessArea from './components/GuessArea.vue';
import Keyboard from './components/QuordleKeyboard.vue';
import MiddleMain from './components/MiddleMain.vue';
import WordListArea from './components/WordListArea.vue'

export default {
  components: {
    GuessArea,
    Keyboard,
    MiddleMain,
    WordListArea
  },
  methods: {
    addTypedLetter(event) {
      if (this.$store.state.currentGuessLetters.length < 5  && event.which > 64 && event.which < 91){ //65=A 90=Z
        this.$store.commit('ADD_LETTER', event.key.toUpperCase());
      } if (event.which == 8) { //Backspace
        this.$store.commit('REMOVE_LETTER');
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
    });
  }
}
</script>

<style>
html {
    font-family: 'Questrial', sans-serif;
}

body{
    background-color: 	#EFE7DA;
    height: 97vh;
    width: 98vw;
    display: grid;
    grid-template-columns: 1fr 265px 265px 1fr;
    grid-template-rows: 1fr 3.75fr 3.65fr;
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

</style>

