import { modifyCustomer } from "../../services/customer";

export default {
    name: 'customer-detail',
    data: function(){
        return{
            editMode: false,
            mod: {
                username: '',
                disabled: '',
                address: null
            },
            message: null,
            isSuccess: false
        }
    },
    created: function(){
        this.editMode = false;
    },
    methods:{
        changeMode: function(){
            if(!this.editMode){
                this.mod.email = this.customer.email;
                this.mod.username = this.customer.username;
                this.mod.disabled = this.customer.disabled;
                this.mod.address = {
                    id: this.customer.address.id,
                    fullName: this.customer.address.fullName,
                    streetNumber: this.customer.address.streetNumber,
                    streetName: this.customer.address.streetName,
                    city: this.customer.address.city,
                    postalCode: this.customer.address.postalCode
                };
            }
            else{
                this.editCustomer();
            }
            this.editMode = !this.editMode;
        },
        editCustomer: function(){
            if(this.editMode)
                modifyCustomer(this.mod)
                .then(res => {
                    this.message = "Data succesfully saved!";
                    this.isSuccess = true;
                    setTimeout(() => this.message = null, 5000);
                    this.onChange();
                })
                .catch(err => {
                    this.message = err;
                    this.isSuccess = false;
                    setTimeout(() => this.message = null, 5000);
                });
        }
    },
    props: {
        customer: Object,
        onChange: Function
    },
    watch: {
        customer: function(){
            this.editMode = false;
        }
    }
}