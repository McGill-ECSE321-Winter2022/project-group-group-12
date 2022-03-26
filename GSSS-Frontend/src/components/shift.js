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

function ShiftDto(date, startTime, endTime){
    this.date = date
    this.startTime = startTime
    this.endTime = endTime
}

// var AXIOS = axios.create({
//     baseURL: backendUrl,
//     headers: { 'Access-Control-Allow-Origin': frontendUrl }
// })

export default{
    name: 'employeeviewshifts',
    data(){
        return{
            shifts: [],
            errorShift: '',
            response: []
        }
    },
    // Retrieve all shifts from an employee email
    created: function(employeeEmail){
        const s1 = new ShiftDto('2022-03-09', '9:00', '18:00')
        const s2 = new ShiftDto('2022-03-27', '7:00', '12:00')
        this.shifts = [s1, s2]
        // // Initilizing shifts from backend
        // AXIOS.get('/shiftsbyemployee/'.concat(employeeEmail))
        // .then(repsonse => {
        //     // JSON repsonses are automatically parsed.
        //     this.shifts = response.data
        // })
        // .catch(e => {
        //     this.errorShift = e
        // })
    }
}