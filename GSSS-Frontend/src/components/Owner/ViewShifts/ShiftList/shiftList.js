import ShiftDetail from "../ShiftDetails/ShiftDetail.vue";
import ShiftCreator from "../ShiftCreator/ShiftCreator.vue";

import { getAllShifts } from "../../../../services/shift";

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