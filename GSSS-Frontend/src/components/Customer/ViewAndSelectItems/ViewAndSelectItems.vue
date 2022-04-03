<!--
Author: Wassim Jabbour
About: Page to handle selecting items and adding them to the customer's cart
-->

<!-- CSS uses bootstrap -->
<template>
  <div class="container">
    <div class="filter">
      <label>Filter by category:  </label>
      <select v-model="itemCategory" @change="filterItems()">
        <option v-for="ic in itemCategories" :value="ic" :key="ic">
          {{ ic }}
        </option>
      </select>
    </div>
    <div class="row mt-2 justify-content-center">
      <div class="items" v-for="product in this.filteredItems" :key="product.name">
        <div class="card" v-bind:title="product.description" style="width: 10rem;">
          <img :src="product.imageUrl" class="card-img-top" />
          <div class="card-body">
            <h6 class="card-title">
              {{ product.name }} - {{ product.price }}$
            </h6>
            <!-- Can't add an item to cart if it is not available at the moment / just for in person purcases / Isn't in stock -->
            <button
              :disabled="!product.stillAvailable || !product.availableForOrder || product.remainingQuantity <= 0"
              v-on:click="addProduct(product)"
              href="#"
              class="btn  btn-block"
              :class="{
                'btn-primary': !product.cart,
                'btn-success': product.cart,
              }"
            >
              <!-- Under which conditions to describe an item as what -->
              {{ (!product.stillAvailable || !product.availableForOrder || product.remainingQuantity == 0) ? "Unavailable" : (!product.cart ? "Add" : "Added") }}
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
        <h4>Total cost: {{ total }}$</h4>
      </div>
    </div>
    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
    <div style="text-align:center">
      <button v-on:click="checkout()" class="checkout">
        Proceed to checkout
      </button>
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

  name: 'ViewAndSelectItems',

  data () {
    return {
      items: [],
      cart: [],
      error: '',
      total: 0,
      itemCategories: [],
      itemCategory: '',
      filteredItems: []
    }
  },

  created: function() {
    
    // Getting the items from the backend
    AXIOS.get('/items')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.items = response.data

      for(let i = 0; i < this.items.length; i++) {
          this.items[i].cart = false
          this.items[i].count = 0
      }

      // Setting the filtered items to be all by default (Cloning)
      this.filteredItems = [...this.items]

      if(this.items.length == 0) {
        this.error = "Note: There are no items in the system at the current time."
        setTimeout(()=>this.error=null, 3000)
      }
      
      })
    .catch(e => {
      this.error = e
      setTimeout(()=>this.error=null, 3000)
    })

    // Getting the item categories from the backend
    AXIOS.get('/itemCategories')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.itemCategories = response.data

      // Replacing each item category by its name
      for(let i = 0; i < this.itemCategories.length; i++) {
        this.itemCategories[i] = this.itemCategories[i].name
      }

      // Adding an "all category"
      this.itemCategories.push("All")
      
      // Setting the default category to be all
      this.itemCategory = "All"

      })
    .catch(e => {
      this.error = e
      setTimeout(()=>this.error=null, 3000)
    })
  },

  // User defined methods
  methods: {
    
    // To add a product to the cart
    addProduct : function(product) {
        if(!product.cart){
            product.count = 1
            product.cart = true
            this.cart.push(product)
            this.total = this.total + product.price
        }
    },

    // To decrease the selected quantity of a product
    decreaseQ : function(i) {
        if(this.cart[i].count > 1) {
            this.cart[i].count = this.cart[i].count - 1
            this.total = this.total - this.cart[i].price
            this.cart = [...this.cart] // Cloning the array to force it to update
        }
    },

    // To increase the selected quantity of a product
    increaseQ : function(i) {
      if(this.cart[i].count < this.cart[i].remainingQuantity) {
        this.cart[i].count = this.cart[i].count + 1
        this.total = this.total + this.cart[i].price
        this.cart = [...this.cart] // Cloning the array to force it to update
      }
      else {
        this.error = "Error: There is not enough remaining stock to add more items"
        setTimeout(()=>this.error=null, 3000)
      }
    },

    // To remove a product
    removeProduct : function(i) {
        this.cart[i].cart = false
        this.total = this.total - this.cart[i].price*this.cart[i].count
        this.cart[i].count = 0
        this.cart.splice(i, 1)
    },

    // To proceed to checkout
    checkout : function() {
        if(this.cart.length > 0) {
          localStorage.setItem("cart", JSON.stringify(this.cart));
          localStorage.setItem("cartCost", this.total)
          this.$router.push("/customer/confirmOrderType")
        }
        else {
          this.error = "Error: No items selected!"
          setTimeout(()=>this.error=null, 3000)
        }
    },

    // To filter items by category
    filterItems : function() {
        
        // If no filter is chosen
        if(this.itemCategory == "All") {
          this.filteredItems = [...this.items]
          return
        }

        // If a filter is chosen

        // Empty the filtered items array
        this.filteredItems = []

        // Add all items with the required category to the filteredItems
        for(let i = 0; i < this.items.length; i++) {
          if(this.items[i].category.name == this.itemCategory) {
            this.filteredItems.push(this.items[i])
          }
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

   .checkout {
     margin-top: 1.5em;
   }


</style>
