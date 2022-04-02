import { deleteShift, getEmployeeByShift } from "../../../../services/shift";

export default {
    name: 'shift-details',
    data: function(){
        return {
            employee : '',
            error: '',
            employeeError: ''
        }
    },
    methods: {
        getEmployee: function() {
            getEmployeeByShift(this.shift.shiftId)
            .then(res => employee)
            .catch(err => {
                this.employeeError = err;
                setTimeout(() => this.employeeError = null, 3000);
            });
        },

        remove: function(){
            deleteShift(this.shift)
            .then(res => null)
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 3000);
            });
        }
    }  
}