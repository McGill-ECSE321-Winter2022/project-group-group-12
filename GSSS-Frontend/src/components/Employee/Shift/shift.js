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
            errorShift: '',
            response: []
        }
    },

    // Retrieve all shifts from an employee email
    created: function(){
        

        // Initilizing shifts from backend
        AXIOS.get('/shiftsbyemployee/' + employeeEmail)
        .then(repsonse => {
            // JSON repsonses are automatically parsed.
            this.shifts = response.data
        })
        .catch(e => {
            this.errorShift = e
        })
    }
}