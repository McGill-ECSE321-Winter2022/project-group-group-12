<!--
Author: Wassim Jabbour
About: Page to handle selecting items and adding them to the customer's cart
-->

<template>
  <div class="container">
    <div class="row mt-2 justify-content-center">
      <div class="col-2" v-for="product in this.items" :key="product.name">
        <div class="card" style="width: 10rem;">
          <img :src="product.imageUrl" class="card-img-top" />
          <div class="card-body">
            <h6 class="card-title">
              {{ product.name }} - $ {{ product.price }}
            </h6>
            <button
              :disabled="!product.stillAvailable"
              v-on:click="addProduct(product)"
              href="#"
              class="btn  btn-block"
              :class="{
                'btn-primary': !product.cart,
                'btn-success': product.cart,
              }"
            >
              {{ !product.cart ? "Add" : "Added" }}
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="row mt-2">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Product</th>
            <th scope="col">Product Name</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col">Remove</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(product, index) in this.cart" :key="index">
            <th scope="row">{{ index + 1 }}</th>
            <th scope="row">
              <img :src="product.imageUrl" style="width: 4rem;" />
            </th>
            <td>{{ product.name }}</td>
            <td>
              {{ product.price }}
            </td>
            <td>
              <button
                v-on:click="decreaseQ(index)"
                class="btn btn-primary btn-sm"
              >
                -
              </button>
              {{ product.count }}
              <button
                v-on:click="increaseQ(index)"
                class="btn btn-primary btn-sm"
                size="sm"
              >
                +
              </button>
            </td>

            <td>
              <button v-on:click="removeProduct(index)" class="btn btn-danger">
                Remove
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="row">
      <div class="col text-center">
        <h4>TOTAL: {{ total }}</h4>
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
      items: [],
      cart: [],
      error: '',
      total: 0
    }
  },

  created: function() {
    
    // Getting the items from the backend
    AXIOS.get('/items')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.items = response.data

      for(let i = 0; i < this.items.length; i++) {
          items[i].cart = false;
          items[i].count = 0;
      }
      
      })
    .catch(e => {
      this.error = e
    })
  },

  methods: {
    
    addProduct : function(product) {
        if(!product.cart){
            product.count = 1
            product.cart = true
            this.cart.push(product)
        }
    },

    decreaseQ : function(i) {
        if(this.cart[i].count > 1) {
            this.cart[i].count = this.cart[i].count - 1
        }
    },

    increaseQ : function(i) {
        this.cart[i].count = this.cart[i].count + 1
    },

    removeProduct : function(i) {
        this.cart[i].cart = false
        this.cart[i].count = 0
        this.cart.splice(i, 1)
    }
  },


  }
</script>


<style scoped>
   .card {
       color: black
   }

   .table {
       color: white
   }


</style>
