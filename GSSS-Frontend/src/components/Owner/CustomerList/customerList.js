import CustomerDetail from "/CustomerDetail/CustomerDetail.vue";
import CustomerCreator from "../../CustomerCreator/CustomerCreator.vue";

import { getAllCustomers } from "../../../services/customer";

export default {
    name: 'customers',
    data(){
        return {
            customers: [],
            selectedCustomer: -1,
            addMode: false
        }
    },
    created: function(){
        getAllCustomers()
        .then(res => this.customers = res)
        .catch(err => console.log(err));
    },
    methods:{
        selectCustomer: function(i){
            this.selectedCustomer = i;
            this.addMode = false;
        },
        selectLastCustomer: function(){
            this.selectedCustomer = this.customers.length - 1;
        },
        addCustomer: function(){
            this.addMode = true;
        },
        onChange: function(){
            getAllCustomers()
            .then(res => {
                this.customers = res;
                this.selectLastCustomer();
            })
            .catch(err => console.log(err));
            this.addMode = false;
        }
    },
    components:{
        CustomerDetail,
        CustomerCreator
    }
}