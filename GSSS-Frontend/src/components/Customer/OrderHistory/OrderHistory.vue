

<template>
  <div>
    <h1>Order History</h1>
    <div class="wrapper">
      <div class="viewpurchases">
        <ul>
          <li  v-for="(purchase, i) in purchases" :key="purchase.id" v-on:click="onPurchaseSelect(i)">
            {{ purchase.date }}
          </li>
        </ul>
      </div>

      <div class = "vertical-separator"/>
      <div class="details">
        <div v-if="this.selectedPurchase!=-1">

          <div>
            <h2> Selected Purchase information: </h2>
          </div>

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
          <h2> Modify purchase order status: </h2>
          <select class="selector" name="Order Status" id="orderStatus" v-model="orderStatus">
            <option value="BeingPrepared">Being Prepared</option>
            <option value="OutForDelivery">Out for Delivery</option>
            <option value="Completed">Completed</option>
          </select>
          <button v-bind:disabled="!orderStatus" @click="modifyPurchaseStatus(orderStatus)">Modify Order Status</button>
        </div>
      </div>
      <div v-if="error" class="error">
        <div>
          {{ error }}
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

  name: 'OrderHistory',

  data () {
    return {
      purchases: [],
      selectedPurchase: -1, // The index of the selected customer
      error: '',
      orderStatus: '',
      response: [],
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: [],
      permission: localStorage.permission
    }
  },

  created: function() {

    // Getting the purchases from the backend

    AXIOS.get('/purchasesbycustomer/')
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
              setTimeout(() => this.error = null, 3000);
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
              setTimeout(() => this.error = null, 3000);
            })
        }

      })
      .catch(e => {
        this.error = e
        setTimeout(() => this.error = null, 3000);
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
            setTimeout(() => this.error = null, 3000);
          })
      }
    },

    modifyPurchaseStatus: function(orderStatus){
      // Modifies selected purchase's order status
      AXIOS.post('/purchase/modify/' + this.selectedPurchase.id,
        {},
        {params: {
            purchaseId: selectedPurchase.id,
            orderType: selectedPurchase.orderType,
            orderStatus: orderStatus,
            data: selectedPurchaseItems,
            employeeEmail: selectedPurchase.employeeEmail,
          },
        })
        .then((response) => {
          this.selectedPurchase.orderStatus = response.data.orderStatus
          location.reload(true);
        })
        .catch(e => {
          this.error = e
          setTimeout(() => this.error = null, 3000);
        });
    },

    modifyPurchaseEmployee: function(newEmployeeEmail){
      // Modifies selected purchase's order status
      AXIOS.post('/purchase/modify/' + this.selectedPurchase.id,
        {},
        {params: {
            purchaseId: selectedPurchase.id,
            orderType: selectedPurchase.orderType,
            orderStatus: selectedPurchase.orderStatus,
            data: selectedPurchaseItems,
            employeeEmail: newEmployeeEmail,
          },
        })
        .then((response) => {
          this.selectedPurchase.employeeEmail = response.data.employeeEmail
          location.reload(true);
        })
        .catch(e => {
          this.error = e
          setTimeout(() => this.error = null, 3000);
        });
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src='./orderHistory.css'>
</style>
