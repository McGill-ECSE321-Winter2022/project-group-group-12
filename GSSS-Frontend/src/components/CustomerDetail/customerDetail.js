export default {
    name: 'customer-detail',
    data: function(){
        return{
            editMode: false,
            mod: {
                username: '',
                disabled: '',
                address: null
            }
        }
    },
    created: function(){
        this.editMode = false;
    },
    methods:{
        changeMode: function(){
            if(!this.editMode){
                this.mod.username = this.customer.username;
                this.mod.disabled = this.customer.disabled;
                this.mod.address = {
                    fullName: this.customer.address.fullName,
                    streetNumber: this.customer.address.streetNumber,
                    streetName: this.customer.address.streetName,
                    city: this.customer.address.city,
                    postalCode: this.customer.address.postalCode
                };
            }
            else{

            }
            this.editMode = !this.editMode;
        }
    },
    props: {
        customer: Object
    },
    watch: {
        customer: function(){
            this.editMode = false;
        }
    }
}