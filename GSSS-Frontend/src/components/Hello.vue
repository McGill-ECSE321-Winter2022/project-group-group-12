<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <br>
    <h2>Available Links</h2>
    <ul>
      <li><a href="http://127.0.0.1:8087/#/ownerPurchases" > Owner ViewPurchases </a></li>
      <li><a href="http://127.0.0.1:8087/#/login"  > Login Page</a></li>
      <li><a href="http://127.0.0.1:8087/#/signup"  > Signup Page</a></li>
      <li><a href="http://127.0.0.1:8087/#/customers"  > Customer List</a></li>
      <li><a href="http://127.0.0.1:8087/#/systemInformation"  > System Information</a></li>
      <li><a href="http://127.0.0.1:8087/#/payment" >Payment page</a></li>
      <li><a href="http://127.0.0.1:8087/#/purchaseStats"  > Purchase Statistics</a></li>

    </ul>
    <h2>Github</h2>
    <li><a href="https://github.com/McGill-ECSE321-Winter2022/project-group-group-12" target="_blank">Our github page</a></li>
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome to the best Grocery Store Website out there !',
      count: 0
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
