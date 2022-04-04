<!--
Author: Wassim Jabbour
About: Page to confirm the purchase and select the type of order (Adds the fee)
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
      <select  placeholder="Select delivery type" class="select-css" v-model="type">
        <option value="Delivery">Delivery</option>
        <option value="Pickup">Pickup</option>
      </select>
      <br>
      <button v-on:click="payment()" class="payment">
        Proceed to payment
      </button>
    </div>
    <br>
    <div style="text-align:center">
        <p v-if="this.type == 'Delivery' && this.deliveryFee != 0">
            Note: A delivery fee of {{ deliveryFee }}$ will apply (Out of city customer)
        </p>
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

  name: 'ConfirmOrderType',

  data () {
    return {
      cart: [],
      deliveryFee: 0,
      error: '',
      total: 0,
      type: '', // If true delivery, if false pickup
    }
  },

  created: function() {
    
    // Initializing the delivery type
    this.type = "Delivery"

    // Getting the cart from local storage
    this.cart = JSON.parse(localStorage.getItem("cart") || "[]");
    if(!this.cart) {
        this.$router.push("/#/customer/shop")
    }

    // Getting the total cost without the fee
    this.total = parseInt(localStorage.getItem("cartCost"))

    // Getting the email of the logged in customer
    var customerEmail = localStorage.email
    if(!customerEmail) {
        this.$router.push("/login")
    }

    // Getting the delivery fee from the backend
    AXIOS.get('/deliveryfee/' + customerEmail)
    .then(response => {
      this.deliveryFee = response.data
      })
    .catch(e => {
      this.error = e.response.data
      setTimeout(()=>this.error=null, 3000)
    })

  },


  // User defined methods
  methods: {
    
    // To proceed to checkout
    payment : function() {

        // Store the total cost with the fee in a variable
        this.total = this.total + this.deliveryFee
        localStorage.setItem('cost', this.total)

        // Add the purchase to the database
        var itemMap = new Map()

        for(var i = 0; i < this.cart.length; i++) {
          itemMap.set(this.cart[i].name, this.cart[i].count)
        }

        // Convert map to JSON which can be passed in request
        var convertedMap = Object.assign({}, ...Array.from(itemMap.entries()).map(([k, v]) =>({[k]: v}) ))

        AXIOS.post('/purchasewithcustomer', convertedMap, 
        { 
          params: {
            email: localStorage.getItem("email"),
            ordertype: this.type,
            orderstatus: "BeingPrepared" 
          } 
        })
        .then(response => {
          // Route to new page
          this.$router.go("/customer/payment")
        })
        .catch(e => {
          this.error = e.response.data
          setTimeout(()=>this.error=null, 3000)
        })
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
     margin-top: 3em;
   }

   .select-css {
    margin-top: 1.5em;
	font-size: 16px;
	font-family: sans-serif;
	font-weight: 700;
	color: #444;
	line-height: 1.3;
	padding: .6em 1.4em .5em .8em;
	width: fit-content;
	max-width: 100%;
	box-sizing: border-box;
	border: 1px solid #aaa;
	box-shadow: 0 1px 0 1px rgba(0,0,0,.04);
	border-radius: .5em;
	-moz-appearance: none;
	-webkit-appearance: none;
	appearance: none;
	background-color: #fff;
	background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22%23007CB2%22%20d%3D%22M287%2069.4a17.6%2017.6%200%200%200-13-5.4H18.4c-5%200-9.3%201.8-12.9%205.4A17.6%2017.6%200%200%200%200%2082.2c0%205%201.8%209.3%205.4%2012.9l128%20127.9c3.6%203.6%207.8%205.4%2012.8%205.4s9.2-1.8%2012.8-5.4L287%2095c3.5-3.5%205.4-7.8%205.4-12.8%200-5-1.9-9.2-5.5-12.8z%22%2F%3E%3C%2Fsvg%3E'),
	linear-gradient(to bottom, #ffffff 0%,#e5e5e5 100%);
	background-repeat: no-repeat, repeat;
	background-position: right .7em top 50%, 0 0;
	background-size: .65em auto, 100%;
    }
    .select-css::-ms-expand {
	    display: none;
    }
    .select-css:hover {
        border-color: #888;
    }
    .select-css:focus {
        border-color: #aaa;
        box-shadow: 0 0 1px 3px rgba(59, 153, 252, .7);
        box-shadow: 0 0 0 3px -moz-mac-focusring;
        color: #222;
        outline: none;
    }
    .select-css option {
        font-weight:normal;
    }


</style>
