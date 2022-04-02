import { addShift } from "../../../../services/shift";

export default {
    name: 'shift-creator',
    data: function(){
        return {
            shift: {
                date: '',
                startTime: '',
                endTime: '',
            },
            employees : [],
            selectedEmployeeEmail: '', 
            error: ''
        }
    },
    methods: {
        save: function(){
            this.selectedEmployeeEmail = select.options[select.selectedIndex].value;
            addShift(this.selectedEmployeeEmail, this.shift)
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