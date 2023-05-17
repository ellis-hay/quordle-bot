<template>
    <div class = "guess-area">
        <guess-row v-for="(row, i) in $store.state.dummyGameInfo.guesses" :key="row.index" :row="row" :rowIndex="i" :letterColors="guessColors[i]" :wordStatus="$store.state.dummyGameInfo.wordStatus[wordNumber - 1]" :wordIndex="wordNumber - 1"/>
    </div>
</template>

<script>
import GuessRow from './GuessRow.vue'
export default {
    name: "dummy-guess-area",
    props: ["word-number"],
    components: {
        GuessRow
    },
    computed: {
        guessColors() {
            return this.$store.state.dummyGameInfo.letterColors[this.wordNumber - 1]
        }
    }
}
</script>