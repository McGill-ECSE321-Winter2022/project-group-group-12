<!-- Author: Wassim Jabbour -->

<template>
  <div>
    <h1>Purchase dates</h1>
    <ul class="selectable-list">
      <li v-for="(purchase, i) in purchases" :key="purchase.id" v-on:click="selectedPurchase = i">
        {{ purchase.date }}
      </li>
    </ul>
    <p> {{ selectedPurchase }} </p>
  </div>
</template>

<script>

// Importing axios and setting up URLs
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {

  name: 'hello',

  data () {
    return {
      purchases: [
        // Mock elements
        // {
        //   id: "Test",
        //   date: "2022-02-02"
        // },
        // {
        //   id: "Test2",
        //   date: "2022-02-02"
        // }
      ],
      selectedPurchase: 0, // The index of the selected customer
      error: '',
      response: []
    }
  },

  created: function() {
    // Getting the purchases from the backend
    AXIOS.get('/purchases')
    .then(response => {

      // JSON responses are automatically parsed.
      this.purchases = response.data

      // Iterating over all purchases and adding their customer's email as a field
      for(purchase in this.purchases) {
        AXIOS.get('/customerByPurchase' + purchase.id)
        .then(response => {
          purchase.customer = response.data.email
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

  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
