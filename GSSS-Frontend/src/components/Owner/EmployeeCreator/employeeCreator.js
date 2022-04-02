import { createEmployee } from "../../../services/employee";

export default {
    name: 'employee-creator',
    data: function(){
        return {
            employee: {
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
            createEmployee(this.employee)
            .then(res => this.onAdd())
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 3000);
            });
        }
    },
    props:{
        onAdd: Function
    }
}