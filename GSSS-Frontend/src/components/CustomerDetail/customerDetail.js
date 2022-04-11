import { modifyCustomer, modifyPassword } from "../../services/customer";

export default {
    name: 'customer-detail',
    data: function(){
        return{
            mode: 0,
            mod: {
                username: '',
                address: null,
                disabled: false
            },
            message: null,
            isSuccess: false
        }
    },
    created: function(){
        this.editMode = false;
    },
    methods:{
        changeMode: function(i, save){
            if(this.mode == 0 && i == 1){
                this.mod.email = this.customer.email;
                this.mod.username = this.customer.username;
                this.mod.address = {
                    id: this.customer.address.id,
                    fullName: this.customer.address.fullName,
                    streetNumber: this.customer.address.streetNumber,
                    streetName: this.customer.address.streetName,
                    city: this.customer.address.city,
                    postalCode: this.customer.address.postalCode
                };
            }
            else if(this.mode == 1 && i == 0){
                if(save) this.editCustomer();
            } else if(this.mode == 0 & i == 2){
                this.mod.password = '';
            } else if(this.mode == 2 && i == 0){
                if(save) this.editPassword();
            } else {
                this.changeMode(0);
                this.changeMode(i);
                return;
            }
            this.mode = i;
        },
        editCustomer: function(){
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
        },
        editPassword: function(){
            modifyPassword(this.customer.email, this.mod.password)
            .then(res => {
                this.message = "Password succesfully changed!";
                this.isSuccess = true;
                setTimeout(() => this.message = null, 5000);
                this.onChange();
            })
            .catch(err => {
                this.message = err;
                this.isSuccess = false;
                setTimeout(() => this.message = null, 5000);
            })
        }
    },
    props: {
        customer: Object,
        onChange: Function
    },
    watch: {
        customer: function(){
            this.mode = 0;
        }
    }
}