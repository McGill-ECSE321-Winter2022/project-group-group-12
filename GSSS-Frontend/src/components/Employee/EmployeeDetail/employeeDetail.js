import { modifyEmployee, modifyPassword } from "../../../services/employee";

export default {
    name: 'employee-detail',
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
                this.mod.email = this.employee.email;
                this.mod.username = this.employee.username;
                this.mod.address = {
                    id: this.employee.address.id,
                    fullName: this.employee.address.fullName,
                    streetNumber: this.employee.address.streetNumber,
                    streetName: this.employee.address.streetName,
                    city: this.employee.address.city,
                    postalCode: this.employee.address.postalCode
                };
            }
            else if(this.mode == 1 && i == 0){
                if(save) this.editEmployee();
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
        editEmployee: function(){
            modifyEmployee(this.mod)
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
            modifyPassword(this.employee.email, this.mod.password)
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
        employee: Object,
        onChange: Function
    },
    watch: {
        employee: function(){
            this.mode = 0;
        }
    }
}