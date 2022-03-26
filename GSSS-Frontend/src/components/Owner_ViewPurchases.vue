<!-- Author: Wassim Jabbour -->

<template>
  <div>
    <h1>Purchase dates</h1>
    <ul class="selectable-list">
      <li v-for="(purchase, i) in purchases" :key="purchase.id" v-on:click="selectedPurchase = i">
        {{ purchase.date }}
      </li>
    </ul>
    <p> {{ selectedPurchase }} </p>
  </div>
</template>

<script>

// import axios from 'axios'
// var config = require('../../config')

// var backendConfigurer = function(){
//   switch(process.env.NODE_ENV){
//       case 'development':
//           return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
//       case 'production':
//           return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
//   }
// };

// var backendUrl = backendConfigurer();

// var AXIOS = axios.create({
//   baseURL: backendUrl,
//   headers: { 'Access-Control-Allow-Origin': frontendUrl }
// })

export default {

  name: 'hello',

  data () {
    return {
      purchases: [

        // Mock elements
        {
          id: "Test",
          date: "2022-02-02"
        },
        {
          id: "Test2",
          date: "2022-02-02"
        }
      ],
      selectedPurchase: 0, // The index of the selected customer
      errorPurchase: '',
      response: []
    }
  },

  // created: function() {
  //   // Getting the purchases from the backend
  //   AXIOS.get('/purchases')
  //   .then(response => {
  //     // JSON responses are automatically parsed.
  //     this.purchases = response.data
  //   })
  //   .catch(e => {
  //     this.errorPurchase = e
  //   })
  // },

  methods: {
    
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
