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

  name: 'ViewPurchases',

  data () {
    return {
      purchases: [],
      employees: [],
      employee: '',
      selectedPurchase: -1, // The index of the selected customer
      error: '',
      success: '',
      orderStatus: '',
      response: [],
      selectedPurchaseItems: [],
      selectedPurchaseQuantities: [],
      selectedPurchaseItemsPrices: [],
      selectedEmployee: '',
    }
  },

  created: function() {
    // Getting the purchases from the backend
    AXIOS.get('/purchases')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.purchases = response.data

      // Iterating over all purchases and adding their customer's email as a field
      for(let i = 0; i < this.purchases.length; i++) {
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
      this.error = e
      setTimeout(() => this.error = null, 3000);
    })

    AXIOS.get('/employees')
    .then(response => {
      this.employees = response.data
    })
    .catch(e => {
      this.error = e.response.data
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

    modifyPurchaseStatus: function(){
      if(!this.orderStatus) {
        this.error = 'Please fill in fields';
        setTimeout(() => this.error = null, 3000);
        return;
      }
      // Modifies selected purchase's order status
      AXIOS.post('/purchase/modify/'+this.purchases[this.selectedPurchase].id,
      this.purchases[this.selectedPurchase].items,
      {params: {
        purchaseId: this.purchases[this.selectedPurchase].id,
        orderType: this.purchases[this.selectedPurchase].orderType,
        orderStatus: this.orderStatus,
        employeeEmail: this.purchases[this.selectedPurchase].employee.email,
        },
      })
      .then((response) => {
        this.$router.go()
      })
      .catch(e => {
        this.error = e.response.data
        setTimeout(() => this.error = null, 3000);
      });
    },

    modifyPurchaseEmployee: function(){
      if(!this.selectedEmployee) {
        this.error = 'Please fill in fields';
        setTimeout(() => this.error = null, 3000);
        return;
      }
      // Modifies selected purchase's order status
      AXIOS.post('/purchase/modify/'+this.purchases[this.selectedPurchase].id, 
      this.purchases[this.selectedPurchase].items,
      {params: {
        purchaseId: this.purchases[this.selectedPurchase].id,
        orderType: this.purchases[this.selectedPurchase].orderType,
        orderStatus: this.purchases[this.selectedPurchase].orderStatus,
        employeeEmail: this.selectedEmployee,
        },
      })
      .then((response) => {
        this.$router.go()
      })
      .catch(e => {
        this.error = e.response.data
        setTimeout(() => this.error = null, 3000);
      });
    }
  },
}