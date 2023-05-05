<template>
  <div class = "word-list-area" >
      <p class="words-remaining">{{wordQuantityRemaining}} possibilit{{wordQuantityRemaining !== 1 ? 'ies' :'y'}} remaining:</p>
        <div class = "possibilities-column">
          <div class="possibilities-row" v-for="i in Math.floor(wordQuantityRemaining/2)" :key="i">
            <p class="word left-word">{{$store.getters.getWordsRemaining[wordNumber - 1][2*(i-1)]}}</p>
            <p class="word right-word">{{$store.getters.getWordsRemaining[wordNumber - 1][2*(i-1)+1]}}</p>
          </div>
          <div class="odd-possibility-row">
            <p class="word odd-word-out" :class="{'singleton-word': wordQuantityRemaining === 1}" v-if="wordQuantityRemaining % 2 === 1">{{$store.getters.getWordsRemaining[wordNumber - 1][wordQuantityRemaining - 1]}}</p>
          </div>
        </div>
  </div>
</template>

<script>
export default {
  name: "word-list-area",
  props: ["wordNumber"],
  computed: {
    wordQuantityRemaining(){
      return this.$store.getters.getWordsRemaining[this.wordNumber - 1].length;
    }
  }
}
</script>

<style>
.word-list-area{
  display: flex;
  flex-direction: column;
}

.words-remaining{
  align-self: center;
}

.possibilities-column {
  flex-grow: 1;
  height: 28vh;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
}

.possibilities-row {
  height: 80px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-self: center;
  align-items: center;
  justify-content: space-around;
}

.odd-possibility-row {
  height: 80px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
}

#word-list-area1 p {
  background-color: #cc7273;
}

#word-list-area2 p {
  background-color: #8f9cb6;
}

#word-list-area3 p {
  background-color: #9d9a9a;
}

#word-list-area4 p {
  background-color: #ebc995;
}

.word {
  flex-basis: 40%;
  display: flex;
  border-radius: 10px;
  height: 50px;
  align-items: center;
  justify-content: center;
}

.odd-word-out {
  margin-left: 5%;
}

</style>