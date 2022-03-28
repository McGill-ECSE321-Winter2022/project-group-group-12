export default {
    name: 'customer-creator',
    data: function(){
        return {
            email: '',
            username: '',
            password: '',
            disabled: '',
            address: {
                fullName: '',
                streetNumber: '',
                streetName: '',
                city: '',
                postalCode: ''
            }
        }
    },
    methods: {
        save: function(){
            this.onAdd();
        }
    },
    props:{
        onAdd: Function
    }
}