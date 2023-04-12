<template>
  <div>
        <div class="keyboard-row">
            <button class="key" @click="addLetter($event)">Q</button>
            <button class="key" @click="addLetter($event)">W</button>
            <button class="key" @click="addLetter($event)">E</button>
            <button class="key" @click="addLetter($event)">R</button>
            <button class="key" @click="addLetter($event)">T</button>
            <button class="key" @click="addLetter($event)">Y</button>
            <button class="key" @click="addLetter($event)">U</button>
            <button class="key" @click="addLetter($event)">I</button>
            <button class="key" @click="addLetter($event)">O</button>
            <button class="key" @click="addLetter($event)">P</button>
        </div>
        <div class="keyboard-row">
            <button class="key" @click="addLetter($event)">A</button>
            <button class="key" @click="addLetter($event)">S</button>
            <button class="key" @click="addLetter($event)">D</button>
            <button class="key" @click="addLetter($event)">F</button>
            <button class="key" @click="addLetter($event)">G</button>
            <button class="key" @click="addLetter($event)">H</button>
            <button class="key" @click="addLetter($event)">J</button>
            <button class="key" @click="addLetter($event)">K</button>
            <button class="key" @click="addLetter($event)">L</button>
        </div>
        <div class="keyboard-row">
            <button class="key key-special"  @click="backspace">⌫</button>
            <button class="key" @click="addLetter($event)">Z</button>
            <button class="key" @click="addLetter($event)">X</button>
            <button class="key" @click="addLetter($event)">C</button>
            <button class="key" @click="addLetter($event)">V</button>
            <button class="key" @click="addLetter($event)">B</button>
            <button class="key" @click="addLetter($event)">N</button>
            <button class="key key-special" @click="enter">↵</button>
        </div>
    </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {
    };
  },
  methods: {
    addLetter(event){
      if (this.$store.state.currentGuessLetters.length < 5){
        this.$store.commit("ADD_LETTER", event.target.textContent);
      }
    },
    backspace(){
      this.$store.commit("REMOVE_LETTER");
    },
    enter(){
      if (this.$store.state.guessable.includes(this.$store.state.currentGuessLetters.join(''))) {
        this.$store.commit('ENTER_WORD');
        this.$store.commit('RESET_CURRENT_LETTERS')
      }
    }
  }
};
</script>

<style>
.keyboard-row {
    display: flex;
    justify-content: space-evenly;
    flex-grow: 1;
    height: calc(16px + 4vh);
    margin-top: 2px;
}

.key{
    display: flex;
    background-color: #EFE7DA;
    font-family: 'Questrial', sans-serif;
    font-size: calc(16px + 1.5vh);
    font-weight: 500;
    height: 100%;
    aspect-ratio: 1/1;
    justify-content: center;
    align-items: center;
    border-radius: 2px;
}

.key-special{
    flex: 0 0 14.5% ;
}

.key:hover {
  background-color: #ebdfcc;
}

</style>