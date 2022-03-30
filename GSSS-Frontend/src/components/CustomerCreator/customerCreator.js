import { createCustomer } from "../../services/customer";

export default {
    name: 'customer-creator',
    data: function(){
        return {
            customer: {
                email: '',
                username: '',
                password: '',
                address: {
                    fullName: '',
                    streetNumber: '',
                    streetName: '',
                    city: '',
                    postalCode: ''
                }
            },
            error: ''
        }
    },
    methods: {
        save: function(){
            createCustomer(this.customer)
            .then(res => this.onAdd())
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 5000);
            });
        }
    },
    props:{
        onAdd: Function
    }
}