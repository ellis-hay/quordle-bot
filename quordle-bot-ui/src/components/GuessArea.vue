<template>
  <div class = "guess-area">
      <guess-row v-for="(row, i) in $store.getters.updatedGuesses" :key="row.index" :row="row" :rowIndex="i" :letterColors="guessColors[i]" :wordStatus="$store.state.wordStatus[wordNumber - 1]" :wordIndex="wordNumber - 1"/>
  </div>
</template>

<script>
import GuessRow from './GuessRow.vue'
export default {
    name: "guess-area",
    props: ["word-number"],
    components: {
        GuessRow
    },
    computed: {
        guessColors() {
            return this.$store.state.letterColors[this.wordNumber - 1]
        }
    }
}
</script>

<style>
.guess-area {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
}

</style>