<template>
    <div>
        <Transition appear
            name="yourScore"
            @before-enter="ysOnBeforeEnter"
            @enter="ysOnEnter"
            :css="false">
            <h2 id="guess-total" v-if="$store.getters.answersGuessed">{{ Number(this.ysNumberSlide).toFixed(0) }}</h2>
        </Transition>
        <Transition appear
            name="computerScore"
            @before-enter="csOnBeforeEnter"
            @enter="csOnEnter"
            :css="false">
            <h2 id="computer-score" v-if="$store.getters.answersGuessed">-{{ Number(this.csNumberSlide).toFixed(0) }}<div>-</div></h2>
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
    methods: {
        ysOnBeforeEnter,
        ysOnEnter,
        csOnBeforeEnter,
        csOnEnter
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

</style>