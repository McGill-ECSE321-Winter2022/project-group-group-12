<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <center><img src="../assets/Logo_GSSS.png" alt="centered image" height="300" width="300"> </center>
    <br>
    <h2>Available Links</h2>
    <ul>
      <li><a href="/#/customer/shop"> Main shopping page</a></li>
      <li><a href="/#/owner/purchases" > Owner ViewPurchases </a></li>
      <li><a href="/#/employee/purchases" > Employee ViewPurchases </a></li>
      <li><a href="/#/owner/viewandedititems" > Owner view and edit items </a></li>
      <li><a href="/#/login"  > Login Page</a></li>
      <li><a href="/#/signup"  > Signup Page</a></li>
      <li><a href="/#/employee/customers"  > Customer List</a></li>
      <li><a href="/#/employee/storeinformation" >Store Information employee</a></li>
      <li><a href="/#/owner/systeminformation"  > System Information</a></li>
      <li><a href="/#/customer/payment" >Payment page</a></li>
      <li><a href="/#/customer/account" >Customer Account page</a></li>

      <li><a href="/#/customer/storeinformation" >Store Information customer</a></li>
      <li><a href="/#/customer/orderhistory" >Order History</a></li>
      <li><a href="/#/owner/createshift" >Owner create shift</a></li>


    </ul>
    <div style="margin: 4rem 0;">
      <h2>!!! The following is intended for development purposes only !!!</h2>
      <p>Choose a permission:</p>
      <button v-on:click="changePermission('')" v-bind:style="!permission ? 'background: blue;' : ''">None</button>
      <button v-on:click="changePermission('Customer')" v-bind:style="permission == 'Customer' ? 'background: blue;' : ''">Customer</button>
      <button v-on:click="changePermission('Employee')" v-bind:style="permission == 'Employee' ? 'background: blue;' : ''">Employee</button>
      <button v-on:click="changePermission('Owner')" v-bind:style="permission == 'Owner' ? 'background: blue;' : ''">Owner</button>
    </div>
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
      count: 0,
      permission: localStorage.permission
    }
  },
  methods: {
    changePermission: function(p){
      localStorage.setItem("permission", p);

    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
