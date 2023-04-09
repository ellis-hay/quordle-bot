<template>
  <div class = "guess-area">
      <guess-row v-for="(row, i) in $store.getters.updatedGuesses" :key="row.index" :row="row" :rowIndex="i"/>
  </div>
</template>

<script>
import GuessRow from './GuessRow.vue'
export default {
    name: "guess-area",
    props: ["word-number"],
    components: {
        GuessRow
    }
}
</script>

<style scoped>
.guess-area {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
}

</style>