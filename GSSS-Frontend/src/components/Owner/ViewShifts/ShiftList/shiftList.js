import ShiftDetail from "../ShiftDetails/ShiftDetail.vue";
import ShiftCreator from "../ShiftCreator/ShiftCreator.vue";

import { getAllShifts } from "../../../../services/shift";

export default {
    name: 'shifts',
    data(){
        return {
            shifts: [],
            selectedShift: -1,
            addMode: false,
            menu: true
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
            this.menu = false;
        },
        selectLastShift: function(){
            this.selectedShift = this.shifts.length - 1;
            this.menu = false;
        },
        addShift: function(){
            this.addMode = true;
            this.menu = false;
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