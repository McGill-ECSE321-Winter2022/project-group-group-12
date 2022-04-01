
import { login } from "../../services/login";

export default{
    name: 'LoginPage',
    data: function() {
        return {
            email: '',
            password: '',
            errorMsg: ''
        }
    },
    methods: {
        logIn: function () {
            login(this.email, this.password)
            .then(response => {
                localStorage.setItem('email', this.email);
                localStorage.setItem('permission', res.data);
                if (response.equals("Customer")) {
                  self.$router.push({ name: "ViewAndSelectItems" });
                } else if (response.equals("Employee")){

                } else if (response.equals("Owner")) {
                  self.$router.push({ name: "SystemInformation" });
                }
              })
              .catch((error) => self.errorMsg = "Invalid username or password.")
              
        }
    }

}