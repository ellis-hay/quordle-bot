<template>
<body>
  <head-main/>
  <guess-area v-for="n in 4" :key = "n" :word-number = "n" :id="'grid' + n"/>
  <word-list-area/>
  <word-list-area/>
  <word-list-area/>
  <word-list-area/>
  <keyboard/>
</body>
</template>

<script>
import GuessArea from './components/GuessArea.vue';
import Keyboard from './components/QuordleKeyboard.vue';
import HeadMain from './components/HeadMain.vue';
import WordListArea from './components/WordListArea.vue'

export default {
  components: {
    GuessArea,
    Keyboard,
    HeadMain,
    WordListArea
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
    "ga1 top top ga2"
    "ga1 pwla1 pwla2 ga2"
    "ga3 pwla3 pwla4 ga4"
    "ga3 keyboard keyboard ga4"
}
#grid1{
  grid-area: ga1;
  background-color: #b11e23;
}
#grid2{
  grid-area: ga2;
  background-color: #12357a;
}
#grid3{
  grid-area: ga3;
  background-color: #1d1d1b;
}
#grid4{
  grid-area: ga4;
  background-color: #e29726;
}
</style>

