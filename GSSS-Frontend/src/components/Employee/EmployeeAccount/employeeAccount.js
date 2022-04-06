import EmployeeDetail from "../EmployeeDetail/EmployeeDetail.vue";
import { getEmployee } from "../../../services/employee";

export default {
    name: 'employee-account',
    data: function(){
        return {
            employee: null
        }
    },
    created: function(){
        getEmployee(localStorage.email)
        .then(res => this.employee = res)
        .catch(err => console.log(err));
    },
    methods:{
        onChange: function(){
            getEmployee(localStorage.email)
            .then(res => this.employee = res)
            .catch(err => console.log(err));
        }
    },
    components:{
        EmployeeDetail
    }
}