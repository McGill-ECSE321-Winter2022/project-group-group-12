import { createCustomer } from "../../services/customer";

export default {
    name: 'customer-creator',
    data: function(){
        return {
            customer: {
                email: null,
                username: null,
                password: null,
                address: {
                    fullName: null,
                    streetNumber: null,
                    streetName: null,
                    city: null,
                    postalCode: null
                }
            },
            error: '',
            success: ''
        }
    },
    methods: {
        save: function(){
            if(!this.customer.email || !this.customer.username || 
                !this.customer.password || !this.customer.address.fullName || 
                !this.customer.address.streetNumber || !this.customer.address.streetName || !this.customer.address.city || !this.customer.address.postalCode) {
                    this.error = 'Please fill in all fields';
                    setTimeout(() => this.error = null, 3000);
                    return;
            }
            createCustomer(this.customer)
            .then(res => {
                this.$router.push({ name: 'LoginPage'})
            })
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
