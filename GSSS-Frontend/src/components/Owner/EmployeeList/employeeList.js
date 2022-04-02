import EmployeeDetail from "../../Employee/EmployeeDetail/EmployeeDetail.vue";
import EmployeeCreator from "../EmployeeCreator/EmployeeCreator.vue";

import { getAllEmployees } from "../../../services/employee";

export default {
    name: 'customers',
    data(){
        return {
            employees: [],
            selectedEmployee: -1,
            addMode: false
        }
    },
    created: function(){
        getAllEmployees()
        .then(res => this.employees = res)
        .catch(err => console.log(err));
    },
    methods:{
        selectEmployee: function(i){
            this.selectedEmployee = i;
            this.addMode = false;
        },
        selectLastEmployee: function(){
            this.selectedEmployee = this.employees.length - 1;
        },
        addEmployee: function(){
            this.addMode = true;
        },
        onChange: function(){
            getAllEmployees()
            .then(res => {
                this.employees = res;
                this.selectLastEmployee();
            })
            .catch(err => console.log(err));
            this.addMode = false;
        }
    },
    components:{
        EmployeeDetail,
        EmployeeCreator
    }
}