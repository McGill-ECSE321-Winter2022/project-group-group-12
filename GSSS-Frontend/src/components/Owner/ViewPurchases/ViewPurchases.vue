<!-- Author: Wassim Jabbour -->

<template>
  <div class="wrapper">
    <h1>Purchases</h1>
    <ul class="purchase-list">
      <li v-for="(purchase, i) in purchases" :key="purchase.id" v-on:click="onPurchaseSelect(i)">
        {{ purchase.date }}
      </li>
    </ul>
    <div class="details">
        <div v-if="this.selectedPurchase!=-1">
          <h3>{{ purchases[selectedPurchase].date }}</h3>
          <div> Id: {{ purchases[selectedPurchase].id }}</div>
          <div> Customer email: {{ purchases[selectedPurchase].customer }}</div>
          <div> Order Type: {{ purchases[selectedPurchase].orderType }}</div>
          <div> Order status: {{ purchases[selectedPurchase].orderStatus }}</div>
          <div> Date: {{ purchases[selectedPurchase].date }}</div>
          <div> Time: {{ purchases[selectedPurchase].time }}</div>
          <div> Employee: {{ purchases[selectedPurchase].employee.email }} </div>
          <div> Cost: {{ purchases[selectedPurchase].cost }}$</div>
          <div> Items:
              <ul v-for="(n, index) in this.selectedPurchaseItems.length" :key="index"> 
                  <li> {{ selectedPurchaseItems[index] }} : {{ selectedPurchaseQuantities[index] }} ( {{ selectedPurchaseItemsPrices[index] }}$ / unit )</li>
              </ul>
          </div>
        </div>
    </div>
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

  name: 'ViewPurchases',

  data () {
    return {
      purchases: [],
      selectedPurchase: -1, // The index of the selected customer
      error: '',
      response: [],
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: []
    }
  },

  created: function() {

    // Getting the purchases from the backend
    AXIOS.get('/purchases')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.purchases = response.data

      // Iterating over all purchases and adding their customer's email as a field
      for(let i = 0; i < this.purchases.length; i++) {
        AXIOS.get('/customerByPurchase/' + this.purchases[i].id)
        .then(response => {
          this.purchases[i].customer = response.data.email
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @import './ViewPurchases.css';
</style>
