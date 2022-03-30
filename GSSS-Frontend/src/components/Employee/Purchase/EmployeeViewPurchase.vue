<template>
<div class="employeeviewpurchases">
  <h5>Your assigned purchases:
  </h5>

    <table>
      <th>Order type</th>
      <th>Order status</th>
      <th>Date</th>
      <th>Time</th>

      <tr v-for="purchase in purchases" :key=purchase.id>
          <td> {{ purchase.orderType }} </td>
          <td> {{ purchase.orderStatus }} </td>
          <td> {{ purchase.date }} </td>
          <td> {{ purchase.time }} </td>
      </tr>
     </table> 
     <p>
      <span v-if="errorPurchase" style="color:red">Error: {{errorPurchase}} </span>
    </p>
  </div>

</template>

<script>

// Importing axios and setting up URLs
import axios from 'axios'
var config = require('../../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {

  name: 'EmployeeViewPurchases',

  data () {
    return {
      purchases: [],
      selectedPurchase: -1, // The index of the selected purchase
      error: '',
      response: [],
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: []
    }
  },

  created: function() {

    // Getting the purchases from the backend
    AXIOS.get('purchasesbyemployee/' + employeeEmail)
    .then(response => {
      // JSON responses are automatically parsed.
      this.purchases = response.data

      // Iterating over all purchases and adding their customer's email as a field
      for(let i = 0; i < this.purchases.length; i++) {
         AXIOS.get('purchasesbyemployee/' + employeeEmail)
        .then(response => {
          this.purchases[i] = response.data.email
        })
        .catch(e => {
          this.error = e
        })
      }

      // Iterating over all purchases and adding their cost as a field
      for(let i = 0; i < this.purchases.length; i++) {
        AXIOS.get('/purchase/cost/' + this.purchases[i].id)
        .then(response => {
          this.purchases[i].cost = response.data
        })
        .catch(e => {
          this.error = e
        })
      }
      
      })
    .catch(e => {
      this.error = e
    })
  },

  methods: {
    
    onPurchaseSelect: function(i) {
      
      // Set the selected purchase to be the one at index i
      this.selectedPurchase = i


      // Refresh the selectedPurchase item lists
      this.selectedPurchaseItems = []
      this.selectedPurchaseQuantities = []
      this.selectedPurchaseItemsPrices = []
      
      for (const [item, quantity] of Object.entries(this.purchases[i].items)) {
        this.selectedPurchaseItems.push(item)
        this.selectedPurchaseQuantities.push(quantity)
      }

      for (let i = 0; i < this.selectedPurchaseItems.length; i++) {
        AXIOS.get('/item/' + this.selectedPurchaseItems[i])
        .then(response => {
          this.selectedPurchaseItemsPrices.push(response.data.price)
        })
        .catch(e => {
          this.error = e
        })
      }


    }

  },


  }
</script>

<style scoped>
    @import './EmployeeViewPurchase.css';
</style>