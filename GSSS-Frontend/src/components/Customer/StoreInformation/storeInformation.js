//Javascript for the Store information page.
//author Theo Ghanem


import {getBusinessHours, getCityAndFee} from "../../../services/systeminfo";

export default {

  data(){
    return {
      selectedScreen: 0,

      currentCity: 'No current city',
      currentFee: 0,
      MonStartTime:'--:--',
      MonCloseTime:'--:--',
      TueStartTime:'--:--',
      TueCloseTime:'--:--',
      WedStartTime:'--:--',
      WedCloseTime:'--:--',
      ThuStartTime:'--:--',
      ThuCloseTime:'--:--',
      FriStartTime:'--:--',
      FriCloseTime:'--:--',
      SatStartTime:'--:--',
      SatCloseTime:'--:--',
      SunStartTime:'--:--',
      SunCloseTime:'--:--',
    }
  },

  created: function(){
    //get the business hours of the particular weekday
    getBusinessHours('Monday')
      .then(res  => {
        this.MonStartTime =res.startTimeFromBackend.slice(0, -3) //(the .slice(0. -3) is to remove the seconds from the time)
        this.MonCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })

    getBusinessHours('Tuesday')
      .then(res  => {
        this.TueStartTime =res.startTimeFromBackend.slice(0, -3)
        this.TueCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })

    getBusinessHours('Wednesday')
      .then(res  => {
        this.WedStartTime =res.startTimeFromBackend.slice(0, -3)
        this.WedCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })

    getBusinessHours('Thursday')
      .then(res  => {
        this.ThuStartTime =res.startTimeFromBackend.slice(0, -3)
        this.ThuCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })

    getBusinessHours('Friday')
      .then(res  => {
        this.FriStartTime =res.startTimeFromBackend.slice(0, -3)
        this.FriCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })

    getBusinessHours('Saturday')
      .then(res  => {
        this.SatStartTime =res.startTimeFromBackend.slice(0, -3)
        this.SatCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })

    getBusinessHours('Sunday')
      .then(res  => {
        this.SunStartTime =res.startTimeFromBackend.slice(0, -3)
        this.SunCloseTime =res.endTimeFromBackend.slice(0, -3)
      })
      .catch(e => {
      })


    //Get current Store City and out of town delivery fee
    getCityAndFee()
      .then(res  => {
        this.currentCity =res.currentCityFromBackend
        this.currentFee =res.outOfCityFeeFromBackend
      })
      .catch(e => {
      })

  },
  methods:{
    showScreen: function(i){
      this.selectedScreen = i;
    }
  }
}
