<template>
    <div>
        <Transition appear
            name="desc-text">
            <h3 id="computer-guesses" v-if="$store.getters.answersGuessed" @click="toggleGuessesShown">{{ this.$store.state.showDummy? "See Your Guesses": "See Computer's Guesses" }}</h3>
        </Transition>
        <Transition appear
            name="yourScore"
            @before-enter="ysOnBeforeEnter"
            @enter="ysOnEnter"
            @leave="resetYsNumberSlide"
            :css="false">
            <h2 id="guess-total" v-if="$store.getters.answersGuessed">{{ Number(this.ysNumberSlide).toFixed(0) }}</h2>
        </Transition>
        <Transition appear
            name="desc-text">
            <h3 id="you-text" v-if="$store.getters.answersGuessed">YOU</h3>
        </Transition>
        <Transition appear
            name="computerScore"
            @before-enter="csOnBeforeEnter"
            @enter="csOnEnter"
            :css="false">
            <h2 id="computer-score" v-if="$store.getters.answersGuessed">-{{ Number(this.csNumberSlide).toFixed(0) }}<div>-</div></h2>
        </Transition>
        <Transition appear
            name="desc-text">
            <h3 id="computer-text" v-if="$store.getters.answersGuessed">COMPUTER</h3>
        </Transition>
        <Transition appear
            name="desc-text">
            <div id="difference-next" v-if="$store.getters.answersGuessed">
                <h3 id="difference" >DIFFERENCE: {{scoreDifference > 0 ? '+' : ''}}{{ scoreDifference }}</h3>
                <h3 id="next" @click="$store.dispatch('onNext')">NEXT</h3>
            </div>
        </Transition>
    </div>
</template>

<script>
import gsap from 'gsap';
export default {
    data() {
        return {
            ysNumberSlide: 0,
            csNumberSlide: this.$store.getters.totalGuesses
        }
    },
    computed: {
        scoreDifference() {
            return this.$store.getters.totalGuesses - this.$store.state.currentGameInfo.totalGuesses
        }
    },
    methods: {
        ysOnBeforeEnter,
        ysOnEnter,
        csOnBeforeEnter,
        csOnEnter,
        resetYsNumberSlide() {
            this.ysNumberSlide = 0;
        },
        toggleGuessesShown() {
            if (!this.$store.state.showDummy){
                this.$store.commit('DUMMY_COMPUTER_GUESSES');
                this.$store.commit('LOG_DUMMY_DIAGRAMS');
            }
            this.$store.commit('TOGGLE_SHOW_DUMMY');
        }
    },
}

function ysOnBeforeEnter(el) {
  gsap.set(el, {
    scaleX: 0.9,
    scaleY: 0.9,
    opacity: 0,
  });
}

function ysOnEnter(el, done) {
    const timeLine = gsap.timeline({delay: 2.35});
  timeLine.to(el, {
    delay: .03,
    duration: 2,
    scaleX: 1,
    scaleY: 1,
    opacity: 1,
    onComplete: done,
  })
  gsap.to(this, {delay: 2.35, duration: 2, ysNumberSlide: this.$store.getters.totalGuesses})
  timeLine.to(el, {
    delay: 1,
    duration: .7,
    y: -105,
    backgroundImage: 'conic-gradient(at 50% 100%, #8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg)', 
  })
}

function csOnBeforeEnter(el) {
    gsap.set(el, {
        backgroundImage: 'conic-gradient(at 50% 49%, #8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg)',
        y: -105,
        opacity: 0
    })
}

function csOnEnter(el, done) {
    gsap.to(el, {
    delay: 5.38,
    duration: 1,
    y: 0,
    opacity: 1,
    onComplete: done,
    backgroundImage: 'conic-gradient(at 50% 50%, #ebc995 0deg 180deg,  #9d9a9a 180deg 360deg)'
  })
  gsap.to(this, {delay: 5.35, duration: 1.35, ease: "power1.out", csNumberSlide: this.$store.state.currentGameInfo.totalGuesses})

}
    

</script>

<style>
#guess-total {
    position: absolute;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    width: fit-content;
    font-size: 28vh;
    top: 7vh;
    color: transparent;
    background: conic-gradient(at 50% 49%, #8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg);
    background-clip: text;  
}

#computer-score {
    position: absolute;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    width: fit-content;
    font-size: 28vh;
    top: 18vh;
    color: transparent;
    background: conic-gradient(at 60% 50%, #ebc995 0deg 180deg,  #9d9a9a 180deg 360deg);
    background-clip: text;
}

#computer-score div{
    display: contents;
    visibility: hidden;
}

#you-text {
    transform: rotate(-90deg);
    position: absolute;
    top: 22%;
    left: 30%;
    font-size: 6vw;
}

#computer-text {
    transform: rotate(90deg);
    position: absolute;
    top: 52%;
    left: 55%;
    font-size: 2.3vw;
}

#computer-guesses {
    position: absolute;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    top: 19%;
    width: fit-content;
    justify-content: space-between;
    background: conic-gradient(#8f9cb6 0deg 180deg,  #cc7273 180deg 360deg);
    -webkit-background-clip: text;
    background-clip: text;
    font-size: 3vh;
    color: transparent;
    cursor: pointer;
    z-index: 1;
}

#difference-next {
    position: absolute;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    top: 66%;
    display: flex;
    align-items: center;
    width: 33vw;
    justify-content: space-between;
    background: conic-gradient(#ebc995 0deg 180deg,  #9d9a9a 180deg 360deg);
    background-clip: text;
}

#difference {
    font-size: 3vw;
    color: transparent;
}

#next {
    font-size: 2.65vw;
    border-radius: .2vw;
    padding: .2vh .3vw;
    color: transparent;
    border-style: solid;
    border-color: #ebc995;
    border-width: .15vw;
    cursor: pointer;
}

#next:hover {
    font-size: 2.75vw;
    border-radius: .2vw;
    padding: .149vh 0.1663vw;
}

.desc-text-enter {
    opacity: 0;
}

.desc-text-enter-active {
    transition: 1s 6s;
}

</style>