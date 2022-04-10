import { addShift } from "../../../../services/shift";
import { getAllEmployees } from "../../../../services/employee";

export default {
    name: 'shift-creator',
    data: function(){
        return {
            shift: {
                date: null,
                startTime: null,
                endTime: null,
            },
            employees : [],
            selectedEmployee: null, 
            error: ''
        }
    },
    created: function(){
        getAllEmployees()
        .then(res => this.employees = res)
        .catch(err => console.log(err));
    },
    methods: {
        save: function(){
            if(!this.shift.date || !this.shift.startTime || 
                !this.shift.endTime || !this.selectedEmployee) {
                    this.error = 'Please fill in all fields';
                    setTimeout(() => this.error = null, 3000);
                    return;
            }

            addShift(this.selectedEmployee, this.shift)
            .then(res => {
                this.onAdd();
                this.$router.go()
             })
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