<template>
  <div>
      <h1>QuordleBot</h1>
      <div id="games-played" class="stat-box" v-if="$store.state.stats.pastScores.length">
        <h2>Games</h2>
        <p>{{ $store.getters['stats/gamesPlayed'] }}</p>
      </div>
      <div id="score-box-total" class="stat-box" v-if="$store.state.stats.pastScores.length">
        <h2>Total</h2>
        <p>{{ $store.getters['stats/totalScore'] }}</p>
      </div>
      <div id="rolling-avg" class="stat-box" v-if="$store.state.stats.pastScores.length">
        <h2>Average</h2>
        <p>{{ $store.getters['stats/averageScore'] }}</p>
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
#middle-main {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  align-items: flex-end;
}

#middle-main h1 {
  flex-grow: 2;
}

.stat-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  justify-content: space-evenly;
  flex-grow: .5;
}

.stat-box h2 {
  margin: 0;
}

.stat-box p {
  margin: 0;
}

@media only screen and (max-width: 820px){
  #middle-main h1 {
    margin-left: 20px;
  }
}
</style>