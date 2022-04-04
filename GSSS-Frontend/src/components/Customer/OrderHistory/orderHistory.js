
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
  name: 'OrderHistory',
  data () {
    return {
      purchases: [],
      selectedPurchase: -1, // The index of the selected customer
      error: '',
      orderStatus: '',
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: [],
      email: ''
    }
  },
  created: function() {
    this.email = localStorage.getItem("email")
    // Getting the purchases from the backend
    AXIOS.get('/purchasesbycustomer/' + localStorage.getItem("email"))
      .then(response => {
        // JSON responses are automatically parsed.
        this.purchases = response.data
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
        if(this.purchases.length == 0) {
          this.error = "Note: The current customer has not made any online purchases yet"
          setTimeout(()=>this.error=null, 3000)
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
            this.error = e.response.data
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
          this.error = e.response.data
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
          this.error = e.response.data
          setTimeout(() => this.error = null, 3000);
        });
    }
  },
}