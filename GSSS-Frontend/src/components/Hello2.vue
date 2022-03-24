<template>
  <div class="hello">
    <h1> coeur </h1>
    <img src="/images/coeur.png" alt="Un gros coeur">
     <button class="btn" @click="love += 1"> The love count is {{ love }} </button>
  </div>
</template>

<script>
export default {
  name: 'hello2',
  data () {
    return {
      love: 0
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
