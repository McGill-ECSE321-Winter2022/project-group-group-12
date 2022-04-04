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
      this.error = e.response.data
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
      this.error = e.response.data
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
          this.$router.push("/#/customer/confirmOrderType")
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