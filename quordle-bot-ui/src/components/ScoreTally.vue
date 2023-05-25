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
            <h2 id="computer-score" v-if="$store.getters.answersGuessed"><div id="minus-sign">-</div>{{ Number(this.csNumberSlide).toFixed(0) }}<div id="balancer">-</div></h2>
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
    transform: 'translateY(-10.8vh)',
    backgroundImage: 'conic-gradient(at 50% 100%, #8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg)', 
  })
}

function csOnBeforeEnter(el) {
    gsap.set(el, {
        backgroundImage: 'conic-gradient(at 50% 50%, #8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg)',
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
    margin: 0 auto;
    height: 28vh;
    display: flex;
    align-items: center;
    width: fit-content;
    font-size: 28vh;
    top: calc((100vh - (48px + 12vh + 6px + 94px)) / 2 + 94px - 14vh);
    /* top = (screen height - keyboard and middle-main heights) / 2 + middle-main height - 1/2 font size*/
    color: transparent;
    background: conic-gradient(#8f9cb6 0deg 90deg, #ebc995 90deg 180deg, #9d9a9a 180deg 270deg, #cc7273 270deg 360deg);
    background-clip: text;  
}

#computer-score {
    position: absolute;
    left: 0;
    right: 0;
    margin: 0 auto;
    height: 28vh;
    display: flex;
    align-items: center;
    width: fit-content;
    font-size: 28vh;
    top: calc((100vh - (48px + 12vh + 6px + 94px)) / 2 + 94px - 2vh);
    color: transparent;
    background: conic-gradient(at 60% 50%, #ebc995 0deg 180deg,  #9d9a9a 180deg 360deg);
    background-clip: text;
}

#computer-score #balancer{
    display: contents;
    visibility: hidden;
}

#you-text {
    transform: rotate(-90deg);
    transform-origin: top left;
    position: absolute;
    top: calc((100vh - (48px + 12vh + 6px + 94px)) / 2 + 94px - 1vh);
    left: calc((50vw - 25.3vh));
    font-size: 9vh;
    margin: 0;
    display: flex;
    align-items: flex-start;
    height: 9vh;
}

#computer-text {
    transform: rotate(90deg);
    transform-origin: top left;
    position: absolute;
    top: calc((100vh - (48px + 12vh + 6px + 94px)) / 2 + 94px + 1.7vh);
    left: calc(50vw + 18.2vh);
    font-size: 3.55vh;
    margin: 0;
}

#computer-guesses {
    position: absolute;
    left: 0;
    right: 0;
    margin: 0 auto;
    top: calc((100vh - (48px + 12vh + 6px + 94px)) / 2 + 94px - 25.5vh);
    width: fit-content;
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
    margin: 0 auto;
    top: 70%;
    display: flex;
    align-items: center;
    width: calc(53.1vh + 1.5vw);
    /* approx width of elements + wiggle room */
    max-width: 528px;
    justify-content: space-between;
    background: conic-gradient(#ebc995 0deg 180deg,  #9d9a9a 180deg 360deg);
    background-clip: text;
}

#difference {
    font-size: 4.67vh;
    color: transparent;
    margin: 0;
    height: 4.67vh;
    display: flex;
    align-items: center;
}

#next {
    font-size: 4.1vh;
    border-radius: 3px;
    padding: 0.4vh 0.3vw;
    margin: 0;
    color: transparent;
    border-style: solid;
    border-color: #ebc995;
    border-width: 2px;
    cursor: pointer;
    height: 4.1vh;
    display: flex;
    align-items: center;
    justify-content: center;
    width: calc(4.1vh * 2.7);
    /* font-size * ~ width-height ratio) */
}

#next:hover {
    font-size: 4.28vh;
}

.desc-text-enter {
    opacity: 0;
}

.desc-text-enter-active {
    transition: 1s 6s;
}

@media only screen and (max-width: 912px) {
    #computer-guesses {
        max-width: 280px;
        text-align: center;
    }
    #guess-total {
        height: 24vh;
        font-size: 24vh;
        top: calc((100vh - (48px + 12vh + 6px + 115px)) / 2 + 115px - 12vh);
        /* top = (screen height - keyboard and middle-main heights) / 2 + middle-main height - 1/2 font size*/
    }

    #computer-score {
        height: 24vh;
        font-size: 24vh;
        top: calc((100vh - (48px + 12vh + 6px + 115px)) / 2 + 115px - 2vh);
    }

    #minus-sign, #you-text, #computer-text {
        visibility: hidden;
    }

    #difference-next {
        max-width: 290px;
        flex-wrap: wrap;
        justify-content: center;
        top: 66.5%
    }

    #difference {
        font-size: 2.82vh;
    }

    #next {
        border-image-source: conic-gradient(#ebc995 0deg 180deg, #9d9a9a 180deg 360deg);
        border-image-slice: 1;
    }
}


</style>