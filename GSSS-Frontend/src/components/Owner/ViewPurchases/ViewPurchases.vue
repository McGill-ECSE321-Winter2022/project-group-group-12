<!-- Author: Wassim Jabbour -->

<template>
 <div>
    <h1>Purchases</h1>
    <div class="wrapper">
      <div class="viewpurchases">
        <ul>
          <li v-for="(purchase, i) in purchases" :key="purchase.id" v-on:click="onPurchaseSelect(i)">
            {{ purchase.date }}
          </li>
        </ul>
      </div>

      <div class = "vertical-separator"/>
      <div class = "details">
          <div v-if="this.selectedPurchase!=-1">

            <div> 
              <h2> Selected Purchase information: </h2>
            </div>

            <div> Customer email: {{ purchases[selectedPurchase].customer }}</div>
            <div> Order type: {{ purchases[selectedPurchase].orderType }}</div>
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
            <button v-bind:disabled="!orderStatus" @click="modifyPurchaseStatus()">Modify Order Status</button>

            <h2> Modify the assigned employee: </h2>
            <select class="selector" v-model='selectedEmployee'>
              <option v-for="employee in this.employees" :key="employee.email"> 
                  {{ employee.email }}
              </option>
            </select>
            <button v-bind:disabled="!selectedEmployee" @click="modifyPurchaseEmployee()">Modify Employee</button>

          </div>
        
        </div>

      <div v-if="error" class="error">
        <div>
          {{ error }}
        </div>
      </div>

      <div v-if="success" class="success">
      <div>
        {{ success }}
      </div>
    </div>

    </div>
  </div>
</template>

<script>

// Importing axios and setting up URLs
import axios from 'axios'
var config = require('../../../../config')

const backendUrl = (process.env.NODE_ENV === "production")
      ? `https://${config.build.backendHost}`
      : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
    const frontendUrl = (process.env.NODE_ENV === "production")
      ? `https://${config.build.host}`
      : `http://${config.dev.host}:${config.dev.port}`;
    const AXIOS = axios.create({
      baseURL: backendUrl,
      headers: { 'Access-Control-Allow-Origin': frontendUrl }
    });

export default {

  name: 'ViewPurchases',

  data () {
    return {
      purchases: [],
      employees: [],
      employee: '',
      selectedPurchase: -1, // The index of the selected customer
      error: '',
      success: '',
      orderStatus: '',
      response: [],
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: [],
      selectedEmployee: '',
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
          this.error = e.response.data
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
          this.error = e.response.data
          setTimeout(() => this.error = null, 3000);
        })
      }
      
      })
    .catch(e => {
      this.error = e
      setTimeout(() => this.error = null, 3000);
    })

    AXIOS.get('/employees')
    .then(response => {
      this.employees = response.data
    })
    .catch(e => {
      this.error = e.response.data
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
          this.error = e.response.data
          setTimeout(() => this.error = null, 3000);
        })
      }
    },

    modifyPurchaseStatus: function(){
      // Modifies selected purchase's order status
      AXIOS.post('/purchase/modify/'+this.purchases[this.selectedPurchase].id,
      {},
      {params: {
        purchaseId: this.purchases[this.selectedPurchase].id,
        orderType: this.purchases[this.selectedPurchase].orderType,
        orderStatus: this.orderStatus,
        data: this.purchases[this.selectedPurchase].data,
        employeeEmail: this.purchases[this.selectedPurchase].employee.email,
        },
      })
      .then((response) => {
        this.purchases[this.selectedPurchase] = response.data
        this.selectedPurchase = -1
        this.success = "Purchase status modified successfully"
        setTimeout(()=>this.success=null, 3000)
      })
      .catch(e => {
        this.error = e.response.data
        setTimeout(() => this.error = null, 3000);
      });
    },

    modifyPurchaseEmployee: function(){
      // Modifies selected purchase's order status
      AXIOS.post('/purchase/modify/'+this.purchases[this.selectedPurchase].id, 
      {},
      {params: {
        purchaseId: this.purchases[this.selectedPurchase].id,
        orderType: this.purchases[this.selectedPurchase].orderType,
        orderStatus: this.purchases[this.selectedPurchase].orderStatus,
        data: this.purchases[this.selectedPurchase].data,
        employeeEmail: this.selectedEmployee,
        },
      })
      .then((response) => {
        this.purchases[this.selectedPurchase] = response.data
        this.selectedPurchase = -1
        this.success = "Purchase employee modified successfully"
        setTimeout(()=>this.success=null, 3000)
      })
      .catch(e => {
        this.error = e.response.data
        setTimeout(() => this.error = null, 3000);
      });
    }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src='./ViewPurchases.css'>
</style>
