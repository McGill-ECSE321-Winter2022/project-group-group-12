import ShiftDetail from "../ShiftDetails/ShiftDetail.vue";
import ShiftCreator from "../ShiftCreator/ShiftCreator.vue";

import { getAllShifts } from "../../../../services/shift";

export default {
    name: 'shifts',
    data(){
        return {
            shifts: [],
            selectedShift: -1,
            addMode: false
        }
    },
    created: function(){
        getAllShifts()
        .then(res => this.shifts = res)
        .catch(err => console.log(err));
    },
    methods:{
        selectShift: function(i){
            this.selectedShift = i;
            this.addMode = false;
        },
        selectLastShift: function(){
            this.selectedShift = this.shifts.length - 1;
        },
        addShift: function(){
            this.addMode = true;
        },
        onChange: function(){
            getAllShifts()
            .then(res => {
                this.shfits = res;
                this.selectLastShift();
            })
            .catch(err => console.log(err));
            this.addMode = false;
        }
    },
    components:{
        ShiftDetail,
        ShiftCreator
    }
}