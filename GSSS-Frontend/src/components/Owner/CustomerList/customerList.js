import CustomerDetail from "./CustomerDetail"

export default {
    name: 'CustomerList',
    data(){
        return {
            customers: [],
            selectedCustomer: 0
        }
    },
    created: function(){
        this.customers = [
            {
                email: 'customer1@email.com',
                username: 'Customer 1',
                disabled: false,
                address: {
                    id: "43fdsag-4q35gsd-325dsassdf",
                    fullName: "Christophe Hatoum",
                    streetName: "Stanley street",
                    streetNumber: 3455,
                    city: "Montreal",
                    postalCode: "H3A 1S3"
                }
            },
            {
                email: 'customer2@email.com',
                username: 'Customer 2',
                disabled: false,
                address: {
                    id: "43fdsag-4q35gsd-325dsassdf",
                    fullName: "Wassim Jabbour",
                    streetName: "Stanley street",
                    streetNumber: 3455,
                    city: "Montreal",
                    postalCode: "H3A 1S3"
                }
            },
            { 
                email: 'customer3@email.com',
                username: 'Customer 3',
                disabled: true,
                address: {
                    id: "43fdsag-4q35gsd-325dsassdf",
                    fullName: "Philippe SH",
                    streetName: "Durocher street",
                    streetNumber: 3460,
                    city: "Montreal",
                    postalCode: "H2X 2E3"
                }
            }
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