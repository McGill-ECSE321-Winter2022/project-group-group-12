<template>
<div class>
  <h5>Your assigned shifts: </h5>

    <div class="employeeviewshifts" v-if="this.shifts.length == 0">
      <p> You don't have any assigned shifts. </p>
    </div>

    <div class="employeeviewshifts" v-if="this.shifts.length != 0">
       <table>
        <th> Shift date</th>
        <th> Shift start time</th>
        <th> Shift end time</th>
      <tr v-for="shift in this.shifts" :key=shift.date>
          <td> {{ shift.date }} </td>
          <td> {{ shift.startTime }} </td>
          <td> {{ shift.endTime }} </td>
      </tr>
      </table>
    </div>

    <div v-if="error" class="error">
      <div>
        {{ error }}
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
    name: 'employeeviewshifts',

    data(){
        return{
            shifts: [],
            error: '',
            response: []
        }
    },

    // Retrieve all shifts from an employee email
    created: function(){
        localStorage.setItem("email", "email@employee.com")
        // Initilizing shifts from backend
        AXIOS.get('/shiftsbyemployee/' + localStorage.email)
        .then(repsonse => {
            // JSON repsonses are automatically parsed.
            this.shifts = response.data
        })
        .catch(e => {
            this.error = e
            setTimeout(()=>this.error=null, 3000)
        })
    }
}
</script>

<style scoped>
    @import './EmployeeViewShift.css';
</style>