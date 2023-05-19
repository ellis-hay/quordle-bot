<template>
    <div class="row-container" :class="{current: $store.state.currentGuessIndex === rowIndex && wordStatus === 'guessing'}">
        <div v-if="$store.state.wordStatus[wordIndex] === 'guessing' ||  wordStatus > rowIndex" class="row" >
            <div class="guess-box" v-for="(letter, i) in row" :key="letter.index" :class="{'green': letterColors[i] === '$', 'yellow': letterColors[i] === '?', 'pointer': clickOrNull === 'click'}" @[clickOrNull]="showComputerGuessesFromIndex">{{letter}}</div>
        </div>
        <div v-else class="row" >
            <div class="guess-box" v-for="i in 5" :key="i">&nbsp;</div>
        </div>
    </div>
  
</template>

<script>
export default {
    name: 'guess-row',
    props: ['row', 'rowIndex', 'letterColors', 'wordIndex', 'wordStatus', 'clickable'],
    computed: {
        clickOrNull() {
            return this.clickable && this.$store.getters.answersGuessed && this.rowIndex !== 0 ? "click" : null;
        }
    },
    methods: {
        showComputerGuessesFromIndex(){
            this.$store.commit('DUMMY_GUESSES_FROM_INDEX', this.rowIndex);
            this.$store.commit('LOG_DUMMY_DIAGRAMS');
            this.$store.commit('TOGGLE_SHOW_DUMMY');
        }
    }
}
</script>

<style >
.row-container{
    height: 5%;
}

.current{
    height: 15%;
    box-sizing: content-box;
}

.row{
    display: flex;
    flex-direction: row;
    justify-content: center;
    height: 100%
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

.green {
    background-color: green !important;
}

.yellow {
    background-color: yellow !important;
}

.pointer {
    cursor: pointer;
}

</style>