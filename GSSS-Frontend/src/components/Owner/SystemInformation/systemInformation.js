
//Javascript for the System information page.
//author Theo Ghanem


import {getBusinessHours, getCityAndFee, updateBusinessHours, updateCityAndFee} from "../../../services/systeminfo";

export default {
  name: 'SystemInformation',
  data() {
    return {
      selectedWeekday: '',
      newStartTime: '',
      newEndTime: '',
      currentStartTime: '',
      currentEndTime: '',

      chosenCity: '',
      currentCity: 'No current city',
      chosenFee: 0,
      currentFee: 0,

      errorBH: '',
      errorStoreInfo: '',
      successBH: '',
      successStoreInfo: '',

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
  //This will be the first thing to run on the page
  //It will get the current Business Hours and populate the table on the bottom of the page
  created: function(){
    //get the business hours of the particular weekday
      getBusinessHours('Monday')
        .then(res  => {
        this.MonStartTime =res.startTimeFromBackend.slice(0, -3) //(the .slice(0. -3) is to remove the seconds from the time)
        this.MonCloseTime =res.endTimeFromBackend.slice(0, -3)
        })
        .catch(e => {
          // this.MonStartTime='--:--'
          // this.MonCloseTime='--:--'
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

  methods: {

    //Method that will be called when we press on the button to save the new city and out of city delivery fee
    saveCityAndFee: function () {
      this.chosenCity = document.getElementById('cityinput').value //get the city inputted by the owner
      this.chosenFee = document.getElementById('feeinput').value   //get the fee inputted by the owner

      updateCityAndFee(this.chosenCity, this.chosenFee) //calls the backend to change the city and fee
        .then(response => {
          this.successStoreInfo = 'Successfully updated store information!'
          setTimeout(() => this.successStoreInfo = null, 5000);
          this.currentCity=this.chosenCity;
          this.currentFee = this.chosenFee;
        })
        .catch(e => {
          this.errorStoreInfo = "Error: Invalid input"
          setTimeout(() => this.errorStoreInfo = null, 5000);
        })
    },

    //Method that will be called when we press on the button to save new Business Hours
    saveBHForWeekday: function () {
      const select = document.getElementById('selectWeekday');
      this.selectedWeekday = select.options[select.selectedIndex].value;
      this.newStartTime = document.getElementById('startTimeInput').value
      this.newEndTime = document.getElementById('endTimeInput').value

      //If All days are selected
      if (this.selectedWeekday == 1) {
        for (let i = 2; i < 9; i++) {
          this.selectedWeekday = select.options[i].value;
          updateBusinessHours(this.selectedWeekday, this.newStartTime, this.newEndTime)
            .then(response => {
              this.successBH = 'Successfully updated the Business Hours!'
              setTimeout(() => this.successBH = null, 5000)

                this.MonStartTime=
                this.TueStartTime=
                this.WedStartTime=
                this.ThuStartTime=
                this.FriStartTime=
                this.SatStartTime=
                this.SunStartTime = this.newStartTime;

                this.MonCloseTime=
                this.TueCloseTime=
                this.WedCloseTime=
                this.ThuCloseTime=
                this.FriCloseTime=
                this.SatCloseTime=
                this.SunCloseTime= this.newEndTime;

            })
            .catch(e => {
              this.errorBH = "Error: Invalid input"
              setTimeout(() => this.errorBH = null, 5000);
            })
        }

        //Else, a single weekday is selected
      } else {
        updateBusinessHours(this.selectedWeekday, this.newStartTime, this.newEndTime)
          .then(response => {
            this.successBH = 'Successfully updated the Business Hours!'
            setTimeout(() => this.successBH = null, 5000);

            if(this.selectedWeekday==='Monday'){
                getBusinessHours(this.selectedWeekday)
                  .then(res  => {
                  this.MonStartTime =res.startTimeFromBackend.slice(0, -3) //(the .slice(0. -3) is to remove the seconds from the time)
                  this.MonCloseTime =res.endTimeFromBackend.slice(0, -3)
                  })
                  .catch(e => {
                  })
            }

            if(this.selectedWeekday==='Tuesday'){
              getBusinessHours(this.selectedWeekday)
                .then(res  => {
                this.TueStartTime =res.startTimeFromBackend.slice(0, -3)
                this.TueCloseTime =res.endTimeFromBackend.slice(0, -3)
                })
                .catch(e => {
                })
            }

            if(this.selectedWeekday==='Wednesday'){
              getBusinessHours(this.selectedWeekday)
                .then(res  => {
                this.WedStartTime =res.startTimeFromBackend.slice(0, -3)
                this.WedCloseTime =res.endTimeFromBackend.slice(0, -3)
                })
                .catch(e => {
                })
            }

            if(this.selectedWeekday==='Thursday'){
              getBusinessHours(this.selectedWeekday)
                .then(res  => {
                this.ThuStartTime =res.startTimeFromBackend.slice(0, -3)
                this.ThuCloseTime =res.endTimeFromBackend.slice(0, -3)
                })
                .catch(e => {
                })
            }

            if(this.selectedWeekday==='Friday'){
              getBusinessHours(this.selectedWeekday)
                .then(res  => {
                this.FriStartTime =res.startTimeFromBackend.slice(0, -3)
                this.FriCloseTime =res.endTimeFromBackend.slice(0, -3)
                })
                .catch(e => {
                })
            }

            if(this.selectedWeekday==='Saturday'){
              getBusinessHours(this.selectedWeekday)
                .then(res  => {
                this.SatStartTime =res.startTimeFromBackend.slice(0, -3)
                this.SatCloseTime =res.endTimeFromBackend.slice(0, -3)
                })
                .catch(e => {
                })
            }

            if(this.selectedWeekday==='Sunday'){
              getBusinessHours(this.selectedWeekday)
                .then(res  => {
                this.SunStartTime =res.startTimeFromBackend.slice(0, -3)
                this.SunCloseTime =res.endTimeFromBackend.slice(0, -3)
                })
                .catch(e => {
                })
            }

          })
          .catch(e => {
            this.errorBH = "Error: Invalid input"
            setTimeout(() => this.errorBH = null, 5000);
          })
      }
    }
  }
}
