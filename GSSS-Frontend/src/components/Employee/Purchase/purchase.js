
// Importing components
import PurchaseDetails from './PurchaseDetails'

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
      purchases: [],
      selectedPurchase: 0, // The index of the selected customer
      error: '',
      response: []
    }
  },

  created: function() {

    
    // Getting the purchases from the backend
    AXIOS.get('purchasesbyemployee/{employeeEmail}')
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
    onPurchaseSelect: function(i) {
      
      // Set the selected purchase to be the one at index i
      this.selectedPurchase = i
      // Refresh the current purchase information

    }
  },

  components:{
      PurchaseDetails
  }
}