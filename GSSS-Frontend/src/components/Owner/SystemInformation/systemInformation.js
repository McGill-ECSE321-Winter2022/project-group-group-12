
import {getBusinessHours, updateBusinessHours, updateCityAndFee} from "../../../services/systeminfo";
import startTimeInput from "sinon/pkg/sinon";

export default {
  name: 'SystemInformation',
  data() {
    return {
      selectedWeekday: '',
      newStartTime: '',
      newEndTime: '',
      chosenCity: '',
      chosenFee: 0,
      errorBH: '',
      errorStoreInfo: '',
      successBH: '',
      successStoreInfo: '',
      currentStartTime: '',
      currentEndTime: ''
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
              setTimeout(() => this.successBH = null, 5000);
            })
            .catch(e => {
              this.errorBH = 'Failed to update the Business Hours!'
              setTimeout(() => this.errorBH = null, 50000);
            })
        }
      } else {
        updateBusinessHours(this.selectedWeekday, this.newStartTime, this.newEndTime)
          .then(response => {
            this.successBH = 'Successfully updated the Business Hours!'
            setTimeout(() => this.successBH = null, 5000);
          })
          .catch(e => {
            this.errorBH = 'Failed to update the Business Hours!'
            setTimeout(() => this.errorBH = null, 5000);
          })
      }
    },

    getPreviousBH: function () {
      getBusinessHours()
        .then(response => {
        })
        .catch(e => {
          this.errorBH = e
          setTimeout(() => this.errorBH = null, 5000);
        })
      console.log(this.currentStartTime)
      console.log(this.currentEndTime)
    }

  }
}
