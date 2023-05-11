<template>
<div id="root">
  <middle-main id="middle-main"/>
  <guess-area v-for="n in 4" :key = "'GA' + n" :word-number = "n" :id="'grid' + n"/>
  <word-list-area v-for="n in 4" :key = "'WLA' + n" :word-number = "n" :id="'word-list-area' + n"/>
  <keyboard id="keyboard"/>
  <Transition
    @before-enter="onBeforeEnter"
    @enter="onEnter"
    :css="false">
    <h2 id="guess-total" v-if="$store.getters.answersGuessed">{{ this.numberSlide.toFixed(0) }}</h2>
  </Transition>
</div>
</template>

<script>
import gsap from 'gsap';

import GuessArea from './components/GuessArea.vue';
import Keyboard from './components/QuordleKeyboard.vue';
import MiddleMain from './components/MiddleMain.vue';
import WordListArea from './components/WordListArea.vue';

export default {
  components: {
    GuessArea,
    Keyboard,
    MiddleMain,
    WordListArea
  },
  data() {
    return {
      numberSlide: 0
    }
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
    onBeforeEnter,
    onEnter
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
  },
  created() {
    if (!Object.keys(this.$store.state.currentGameInfo).length) {
      this.$store.dispatch('logGameInfo');
    }
  }
}

function onBeforeEnter(el) {
  gsap.set(el, {
    scaleX: 0.9,
    scaleY: 0.9,
    opacity: 0,
  });
}

function onEnter(el, done) {
  gsap.to(el, {
    delay: 2.38,
    duration: 2,
    scaleX: 1,
    scaleY: 1,
    opacity: 1,
    onComplete: done,
  })
  gsap.to(this, { delay: 2.35, duration: 2, numberSlide: this.$store.getters.totalGuesses})
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

#guess-total {
  position: absolute;
  left: 0;
  right: 0;
  margin-left: auto;
  margin-right: auto;
  width: fit-content;
  font-size: 28vh;
  top: 7vh;
  color: transparent;
  background: conic-gradient(at 50% 49%, #8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg);
  background-clip: text;  
}

</style>

