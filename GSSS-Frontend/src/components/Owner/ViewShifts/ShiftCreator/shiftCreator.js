import { addShift } from "../../../../services/shift";

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
            selectedEmployee: '', 
            error: ''
        }
    },
    methods: {
        save: function(){
            if(!this.shift.date || !this.shift.startTime || 
                !this.shift.endTime) {
                    this.error = 'Please fill in all fields';
                    setTimeout(() => this.error = null, 3000);
                    return;
            }

            addShift(this.selectedEmployee, this.shift)
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