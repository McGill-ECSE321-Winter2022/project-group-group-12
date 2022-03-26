<template>
  <div class="systemInformation-container">
    <div>
      <h1>Grocery Software System Information</h1>
      <div>Business Hours:</div>
      <input placeholder="07:00 - 18:00" type="text" />
      <div>City:</div>
      <input placeholder="Montreal" type="text" />
      <div>Out of city fee:</div>
      <input placeholder="5$" type="number" />
    </div>
  </div>
</template>

<style scoped>
@import '../assets/styles/systemInformation.css';
</style>

<script>
export default{
  name: 'SystemInformation',
  data(){
    return {

    }
  }
}
</script>
