<!--
Author: Wassim Jabbour
About: Page to handle viewing and editing the items as the owner
-->

<!-- CSS uses bootstrap -->
<template>
  <div class="container">
    <div class="row mt-2 justify-content-center">
      <div class="items" v-for="(product, index) in this.items" :key="product.name">
        <div class="card" v-on:click="selectItem(index)" style="width: 10rem;">
          <img :src="product.imageUrl" class="card-img-top" />
          <div class="card-body">
            <h6 class="card-title">
              {{ product.name }} - {{ product.price }}$
            </h6>
          </div>
        </div>
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
    <div class="operations">
      <div v-if="this.selectedItem != -1">
              <h2> Modify: {{ this.name }} </h2>
              <label>Description: </label>
              <input type="text" placeholder="Enter description" v-model="description"/>
              <br/>
              <label>Image URL: </label>
              <input type="text" placeholder="Enter image URL" v-model="imageUrl" />
              <br/>
              <label>Remaining quantity: </label>
              <input type="number" placeholder="Enter quantity" v-model="remainingQuantity" />
              <br/>
              <label>Price (In $): </label>
              <input type="number" placeholder="Enter price" v-model="price" />
              <br/>
              <label>Available for pickup and delivery: </label>
              <input type="checkbox" v-model="availableForOrder" />
              <br/>
              <label>Still available (Not out of supply): </label>
              <input type="checkbox" v-model="stillAvailable" />
              <br/>
              <label>Item category: </label>
              <select v-model="itemCategory">
                <option v-for="ic in itemCategories" :value="ic.name" :key="ic.name">
                  {{ ic.name }}
                </option>
              </select>
              <button v-on:click="modifyItem()">Modify</button>
        </div>
        <div v-if="selectedItem!=-1" class = "vertical-separator" style="margin: 0 3rem"></div>
        <ItemCreator/>
      </div>
  </div>
</template>

<script>

    import ItemCreator from '@/components/Owner/ViewItems/ItemCreator.vue'

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
      itemCategories: [],
      error: '',
      success: '',
      name: '',
      selectedItem: -1,
      description: '',
      imageUrl: '',
      remainingQuantity: 0,
      price: 0,
      availableForOrder: true,
      stillAvailable: true,
      itemCategory: ''
    }
  },

  created: function() {
    
    // Getting the items from the backend
    AXIOS.get('/items')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.items = response.data
      
      })
    .catch(e => {
      this.error = e.response.data
      setTimeout(()=>this.error=null, 3000)
    })

    // Getting the item categories from the backend
    AXIOS.get('/itemCategories')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.itemCategories = response.data
      
      })
    .catch(e => {
      this.error = e.response.data
      setTimeout(()=>this.error=null, 3000)
    })
  },

  // User defined methods
  methods: {
    
    // Modifies the selected item and the default value of all modify fields  
    selectItem: function(i) {
      this.selectedItem = i
      this.name = this.items[i].name
      this.description = this.items[i].description
      this.imageUrl = this.items[i].imageUrl
      this.remainingQuantity = this.items[i].remainingQuantity
      this.price = this.items[i].price
      this.availableForOrder = this.items[i].availableForOrder
      this.stillAvailable = this.items[i].stillAvailable
      this.itemCategory = this.items[i].category.name
    },

    modifyItem: function() {
      
      // Input validation
      if(this.remainingQuantity < 0) {
        this.error = "Remaining quantity cannot be negative"
        setTimeout(()=>this.error=null, 3000)
        return
      }
      if(this.price <= 0) {
        this.error = "Price must be strictly positive"
        setTimeout(()=>this.error=null, 3000)
        return
      }

      // Modify the item
      // Getting the items from the backend
      AXIOS.post('/item/' + this.name, null, 
      {
        params: {
          description: this.description,
          imageUrl: this.imageUrl,
          remainingQuantity: this.remainingQuantity,
          price: this.price,
          availableForOrder: this.availableForOrder,
          stillAvailable: this.stillAvailable,
          itemCategory: this.itemCategory
        }
      })
      .then(response => {
          this.items[this.selectedItem] = response.data
          this.selectedItem = -1
          this.success = "Item modified successfully"
          setTimeout(()=>this.success=null, 3000)
        })
      .catch(e => {
        this.error = e.response.data
        setTimeout(()=>this.error=null, 3000)
      })
    }


    },
    
    components: {
      ItemCreator
    }
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

   .success {
     position: fixed;
     bottom: 0;
     left: 0;
     right: 0;
     color: green;
     margin-bottom: 2rem;
     display: flex;
     justify-content: center;
   }

   .success > div {
     background-color: rgba(0, 0, 0, 0.822);
     padding: 0.5em 1em;
     border-radius: 0.5rem;
   }
   
   .operations {
     display: flex;
     justify-content: center;
   }



</style>
