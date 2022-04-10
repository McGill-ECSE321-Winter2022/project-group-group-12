import CustomerDetail from "../../CustomerDetail/CustomerDetail.vue";
import {getCustomer} from "../../../services/customer";

export default {
  name: 'customer-view-account',
  data: function () {
    return {
      customer: null
    }
  },
  created: function () {
    getCustomer(localStorage.email)
      .then(res => this.customer = res)
      .catch(err => console.log(err));
  },
  methods: {
    onChange: function () {
      getCustomer(localStorage.email)
        .then(res => this.customer = res)
        .catch(err => console.log(err));
    }
  },
  components: {
    CustomerDetail
  }
}
