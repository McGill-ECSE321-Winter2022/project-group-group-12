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

  name: 'EmployeeViewPurchases',

  data () {
    return {
      purchases: [],
      selectedPurchase: -1, // The index of the selected purchase
      error: '',
      success: '',
      response: [],
      orderStatus: '',
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: [],
      menu: true
    }
  },

  created: function() {
    // Getting the purchases from the backend using the email of the employye logged in
    AXIOS.get('/purchasesbyemployee/'.concat(localStorage.email))
    .then(response => {
      // JSON responses are automatically parsed.
      this.purchases = response.data

      if(this.purchases.length == 0) {
          this.error = "Note: The current employee has not yet been assigned any purchases"
          setTimeout(()=>this.error=null, 3000)
      }

      // Iterating over all purchases and adding their customer's email as a field
      for(let i = 0; i < this.purchases.length; i++) {
        console.log(this.purchases[i].name)
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
      this.error = e.response.data
      setTimeout(() => this.error = null, 3000);
    })
  },

  methods: {
    
    onPurchaseSelect: function(i) {
      
      // Set the menu to disappear
      this.menu = false
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
          this.error = e
          setTimeout(() => this.error = null, 3000);
        })
      }
    },

    modifyPurchaseStatus: function(){
      if(!this.orderStatus) {
        this.error = 'Please fill in fields';
        setTimeout(() => this.error = null, 3000);
        return;
      }
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
        this.$router.go() // Refresh the page
      })
      .catch(e => {
        this.error = e
        setTimeout(() => this.error = null, 3000);
      });
    },
  }
};