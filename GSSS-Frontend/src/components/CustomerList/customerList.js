import CustomerDetail from "./CustomerDetail"

var selectedCustomer = 0;

export default {
    name: 'customer-list',
    data(){
        return {
            customers: [],
            selectedCustomer: 0
        }
    },
    created: function(){
        this.customers = [
            { email: 'customer1@email.com', username: 'Customer 1', disabled: false },
            { email: 'customer2@email.com', username: 'Customer 2', disabled: false },
            { email: 'customer3@email.com', username: 'Customer 3', disabled: true }
        ]
    },
    methods:{
        selectCustomer: function(i){
            this.selectedCustomer = i;
        }
    },
    components:{
        CustomerDetail
    }
}