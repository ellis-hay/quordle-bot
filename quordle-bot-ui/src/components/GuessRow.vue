<template>
  <div class="row" :class="{current: $store.state.currentGuess === rowNumber}">
      <div class="guess-box"></div>
      <div class="guess-box"></div>
      <div class="guess-box"></div>
      <div class="guess-box"></div>
      <div class="guess-box"></div>
  </div>
</template>

<script>
export default {
    name: 'guess-row',
    props: ['rowNumber']
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


</style>