import EmployeeDetail from "../EmployeeDetail/EmployeeDetail.vue";
import EmployeeCreator from "../EmployeeCreator/EmployeeCreator.vue";

import { getAllEmployees } from "../../../services/employee";

export default {
    name: 'customers',
    data(){
        return {
            employees: [],
            selectedEmployee: -1,
            addMode: false,
            error: null,
            menu: true
        }
    },
    created: function(){
        getAllEmployees()
        .then(res => this.employees = res)
        .catch(err => {
            this.error = err;
            setTimeout(() => this.error = null, 5000);
        });
    },
    methods:{
        selectEmployee: function(i){
            this.selectedEmployee = i;
            this.addMode = false;
            this.menu = false;
        },
        selectLastEmployee: function(){
            this.selectedEmployee = this.employees.length - 1;
            this.menu = false;
        },
        addEmployee: function(){
            this.addMode = true;
            this.menu = false;
        },
        onChange: function(){
            getAllEmployees()
            .then(res => {
                this.employees = res;
                this.selectLastEmployee();
            })
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 5000);
            });
            this.addMode = false;
        }
    },
    components:{
        EmployeeDetail,
        EmployeeCreator
    }
}