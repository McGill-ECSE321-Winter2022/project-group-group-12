<!--
Author: Wassim Jabbour
About: Page to handle selecting items and adding them to the customer's cart
-->

<!-- CSS uses bootstrap -->
<template>
  <div class="container">
    <div class="row mt-2">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Product</th>
            <th scope="col">Product Name</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
          </tr>
        </thead>
        <tbody>
          <!-- Displaying the cart -->
          <tr v-for="(product, index) in this.cart" :key="index">
            <th scope="row">{{ index + 1 }}</th>
            <th scope="row">
              <img :src="product.imageUrl" style="width: 4rem;" />
            </th>
            <td>{{ product.name }}</td>
            <td>
              {{ product.price }}$/unit
            </td>
            <td>
              {{ product.count }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="row">
      <div class="col text-center">
        <h4>Total cost: {{ total }}$</h4>
      </div>
    </div>
    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
    <div style="text-align:center">
      <button v-on:click="payment()" class="payment">
        Proceed to payment
      </button>
    </div>
    <select id="selectDeliveryType" placeholder="Select delivery type">
        <option value="delivery">Delivery</option>
        <option value="pickup">Pickup</option>
    </select>
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
      cart: [],
      storeCity: '',
      outOfCityFee: 0,
      error: '',
      total: 0,
      delivery: undefined // If true delivery, if false pickup
    }
  },

  created: function() {
    
    // Getting the system information from the backend
    AXIOS.get('/owner')
    .then(response => {
      this.storeCity = response.data.storeCity
      this.outOfCityFee = response.data.outOfTownDeliveryFee
      })
    .catch(e => {
      this.error = e
      setTimeout(()=>this.error=null, 3000)
    })

    // Getting the cart from local storage
    this.cart = localStorage.cart
    if(!this.cart) {
        router.push("/customer/shop")
    }

    // Computing the total cost without the fee
    for(product in this.cart) {
        this.total += product.price * product.count
    }
  },


  // User defined methods
  methods: {
    
    // To proceed to checkout
    payment : function() {
        if(this.delivery) {
            localStorage.setItem('cost', this.total)
            router.push("/customer/payment")
        }
        else {
            this.error = "Please choose delivery or pickup"
            setTimeout(()=>this.error=null, 3000)
        }
    }

  },


  }
</script>



<style scoped>

  /* User written styling of the file */
   .card {
       color: black;
       margin-bottom: 1rem;
       margin-left: 0.5rem;
       margin-right: 0.5rem;
   }

   .table {
      color: white;
   }

   .error {
     position: fixed;
     bottom: 0;
     left: 0;
     right: 0;
     color: red;
     margin-bottom: 2rem;
     display: flex;
     justify-content: center;
   }

   .error > div {
     background-color: rgba(0, 0, 0, 0.822);
     padding: 0.5em 1em;
     border-radius: 0.5rem;
   }

   .payment {
     margin-top: 1.5em;
   }


</style>
