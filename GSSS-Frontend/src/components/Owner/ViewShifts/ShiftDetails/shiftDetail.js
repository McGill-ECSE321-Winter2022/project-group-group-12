import { getEmployee } from "../../../../services/employee";
import { removeShift, getEmployeeByShift } from "../../../../services/shift";

export default {
    name: 'shift-details',
    data: function(){
        return {
            employee : '',
            error: '',
            employeeError: ''
        }
    },

    created: function() {
        this.getEmployee()
    },

    methods: {
        getEmployee: function() {
            getEmployeeByShift(this.shift.id)
            .then(res => this.employee = res.email)
            .catch(err => {
                this.employeeError = err;
                setTimeout(() => this.employeeError = null, 3000);
            });
        },

        remove: function(){
            removeShift(this.shift.id, this.employee)
            .then(res => {
                this.$router.push( {name: 'ShiftList'} )
            })
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 3000);
            });
        }
    } ,

    props: {
        shift: Object,
        onChange: Function
    },

    watch: {
        shift: function() {
            this.getEmployee()
        }
    }
}