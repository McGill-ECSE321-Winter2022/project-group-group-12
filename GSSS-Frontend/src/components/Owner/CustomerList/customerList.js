import CustomerDetail from "../CustomerDetail/CustomerDetail.vue";
import CustomerCreator from "../../CustomerCreator/CustomerCreator.vue";

import { getAllCustomers } from "../../../services/customer";

export default {
    name: 'customers',
    data(){
        return {
            customers: [],
            selectedCustomer: -1,
            addMode: false,
            error: null,
            menu: true
        }
    },
    created: function(){
        getAllCustomers()
        .then(res => this.customers = res)
        .catch(err => {
            this.error = err;
            setTimeout(() => this.error = null, 5000);
        });
    },
    methods:{
        selectCustomer: function(i){
            this.selectedCustomer = i;
            this.addMode = false;
            this.menu = false;
        },
        selectLastCustomer: function(){
            this.selectedCustomer = this.customers.length - 1;
            this.menu = false;
        },
        addCustomer: function(){
            this.addMode = true;
            this.menu = false;
        },
        onChange: function(){
            getAllCustomers()
            .then(res => {
                this.customers = res;
                this.selectLastCustomer();
            })
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 5000);
            });
            this.addMode = false;
        }
    },
    components:{
        CustomerDetail,
        CustomerCreator
    }
}