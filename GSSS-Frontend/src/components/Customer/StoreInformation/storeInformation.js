
import {getBusinessHours, updateBusinessHours, updateCityAndFee} from "../../../services/systeminfo";
import startTimeInput from "sinon/pkg/sinon";
import {getAllCustomers} from "../../../services/customer";
import CustomerDetail from "../../CustomerDetail/customerDetail";
import CustomerCreator from "../../CustomerCreator/customerCreator";

export default {

  data(){
    return {
      selectedScreen: 0,
    }
  },
    methods:{
    showScreen: function(i){
      this.selectedScreen = i;
    }
  }
}
