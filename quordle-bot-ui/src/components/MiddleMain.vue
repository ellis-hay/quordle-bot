<template>
  <div>
      <h1>QuordleBot</h1>
      <div id="games=played" class="stat-box">
        <h2>Games Played</h2>
        <p>{{ $store.getters['stats/gamesPlayed'] }}</p>
      </div>
      <div id="score-box-total" class="stat-box">
        <h2>Total</h2>
        <p>{{ $store.getters['stats/totalScore'] }}</p>
      </div>
      <div id="rolling-avg" class="stat-box">
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

</style>