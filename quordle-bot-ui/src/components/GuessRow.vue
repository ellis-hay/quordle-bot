<template>
  <div class="row" :class="{current: $store.state.currentGuessNumber === rowNumber}">
      <div class="guess-box" v-for="letter in row" :key="letter.index" :letter="letter">{{letter}}</div>
  </div>
</template>

<script>
export default {
    name: 'guess-row',
    props: ['row', 'rowNumber']
}
</script>

<style scoped>
.row{
    display: flex;
    flex-direction: row;
    justify-content: center;
    height: 5%;
}
.guess-box {
    display: flex;
    height: 100%;
    margin: .1rem;
    /* width:  calc(16px + 3.5vh); */
    border-radius: 3px;
    justify-content: center;
    align-items: center; 
}

.current{
    height: 15%;
    box-sizing: content-box;
}
.guess-box{
    aspect-ratio: 3/1;
}

.current .guess-box{
    aspect-ratio: 1/1;
}

#grid1 .guess-box {
    background-color: #c15354;
}

#grid2 .guess-box {
    background-color: #6f80a2;
}

#grid3 .guess-box {
    background-color: #827e7e;
}

#grid4 .guess-box {
    background-color: #e9c389;
}

</style>