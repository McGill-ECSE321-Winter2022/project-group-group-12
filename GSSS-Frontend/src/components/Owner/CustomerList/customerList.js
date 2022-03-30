import CustomerDetail from "../../CustomerDetail/CustomerDetail";
import CustomerCreator from "../../CustomerCreator/CustomerCreator";

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
        addCustomer: function(){
            this.addMode = true;
        },
        onAdd: function(){
            // refresh list
            this.addMode = false;
        }
    },
    components:{
        CustomerDetail,
        CustomerCreator
    }
}