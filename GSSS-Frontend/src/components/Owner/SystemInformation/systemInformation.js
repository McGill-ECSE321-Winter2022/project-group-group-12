
import {getBusinessHours, updateBusinessHours, updateCityAndFee} from "../../../services/systeminfo";

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

  methods: {

    saveCityAndFee: function () {
      this.chosenCity = document.getElementById('cityinput').value
      this.chosenFee = document.getElementById('feeinput').value
      updateCityAndFee(this.chosenCity, this.chosenFee)
        .then(response => {
          this.successStoreInfo = 'Successfully updated store information!'
          setTimeout(() => this.successStoreInfo = null, 5000);
          this.currentCity=this.chosenCity;
          this.currentFee = this.chosenFee;
        })
        .catch(e => {
          this.errorStoreInfo = 'Failed to update the store information!'
          setTimeout(() => this.errorStoreInfo = null, 5000);
        })
    },

    saveBHForWeekday: function () {
      const select = document.getElementById('selectWeekday');
      this.selectedWeekday = select.options[select.selectedIndex].value;
      this.newStartTime = document.getElementById('startTimeInput').value
      this.newEndTime = document.getElementById('endTimeInput').value
      if (this.selectedWeekday == 1) {
        for (let i = 2; i < 9; i++) {
          this.selectedWeekday = select.options[i].value;
          console.log(this.selectedWeekday)
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
              this.errorBH = 'Failed to update the Business Hours!'
              setTimeout(() => this.errorBH = null, 5000);
            })
        }
      } else {
        updateBusinessHours(this.selectedWeekday, this.newStartTime, this.newEndTime)
          .then(response => {
            this.successBH = 'Successfully updated the Business Hours!'
            setTimeout(() => this.successBH = null, 5000);
            if(this.selectedWeekday==='Monday'){
              this.MonStartTime=this.newStartTime;
              this.MonCloseTime=this.newEndTime;
            }
            if(this.selectedWeekday==='Tuesday'){
              this.TueStartTime=this.newStartTime;
              this.TueCloseTime=this.newEndTime;
            }
            if(this.selectedWeekday==='Wednesday'){
              this.WedStartTime=this.newStartTime;
              this.WedCloseTime=this.newEndTime;
            }
            if(this.selectedWeekday==='Thursday'){
              this.ThuStartTime=this.newStartTime;
              this.ThuCloseTime=this.newEndTime;
            }
            if(this.selectedWeekday==='Friday'){
              this.FriStartTime=this.newStartTime;
              this.FriCloseTime=this.newEndTime;
            }
            if(this.selectedWeekday==='Saturday'){
              this.SatStartTime=this.newStartTime;
              this.SatCloseTime=this.newEndTime;
            }
            if(this.selectedWeekday==='Sunday'){
              this.SunStartTime=this.newStartTime;
              this.SunCloseTime=this.newEndTime;
            }

          })
          .catch(e => {
            this.errorBH = 'Failed to update the Business Hours!'
            setTimeout(() => this.errorBH = null, 5000);
          })
      }
    }

  }
}