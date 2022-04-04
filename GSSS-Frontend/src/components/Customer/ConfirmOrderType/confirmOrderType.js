// Importing axios and setting up URLs
import axios from 'axios'
const config = require('../../../../config');
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

// Getting the total cost without the fee
this.total = parseInt(localStorage.getItem("cartCost"))

// Getting the email of the logged in customer
var customerEmail = localStorage.email
if(!customerEmail) {
    this.$router.go('login')
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