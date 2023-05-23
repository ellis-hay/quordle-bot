<template>
  <div>
        <div class="keyboard-row">
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('Q')}">Q</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('W')}">W</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('E')}">E</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('R')}">R</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('T')}">T</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('Y')}">Y</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('U')}">U</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('I')}">I</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('O')}">O</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('P')}">P</button>
        </div>
        <div class="keyboard-row">
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('A')}">A</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('S')}">S</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('D')}">D</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('F')}">F</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('G')}">G</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('H')}">H</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('J')}">J</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('K')}">K</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('L')}">L</button>
        </div>
        <div class="keyboard-row">
            <button class="key key-special"  @click="backspace">⌫</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('Z')}">Z</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('X')}">X</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('C')}">C</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('V')}">V</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('B')}">B</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('N')}">N</button>
            <button class="key" @click="addLetter($event)" :style="{background: quadrantColors.get('M')}">M</button>
            <button class="key key-special" @click="enter">↵</button>
        </div>
    </div>
</template>

<script>
export default {
  name: "quordle-keyboard",
  computed: {
    quadrantColors(){
      const quadGradientMap = new Map();
      this.$store.getters.keyColors.forEach((array, letter) => {
        let cssGradient;
        if (array.every(quadrant => quadrant == "none")){
          cssGradient = "#c9c3b6"
        } else {
          array.push(array[2]);
          array.splice(2, 1);
          cssGradient = "conic-gradient(from 270deg, ";
          array.forEach((quadrant, i) => {
            if (quadrant == "green") {
              cssGradient += "green ";
            } else if (quadrant == "yellow") {
              cssGradient += "yellow ";
            } else {
              cssGradient += "#EFE7DA ";
            }
            cssGradient += `${(90 * i)}deg ${(90 * i + 90)}deg`;
            if (i < 3) {
              cssGradient += ', ';
            }
          })
          cssGradient += ")";
        }
        quadGradientMap.set(letter, cssGradient)    
      })
      return quadGradientMap;
    }
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
      this.$store.dispatch('guessWord');
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
    height: 99%;
    aspect-ratio: 1/1.03;
    justify-content: center;
    align-items: center;
    border-radius: 2px;
    cursor: pointer;
}

.key-special{
    flex: 0 0 14.5% ;
}

.key:hover {
  background-color: #ebdfcc;
}

@media only screen and (max-width: 820px){

}

</style>