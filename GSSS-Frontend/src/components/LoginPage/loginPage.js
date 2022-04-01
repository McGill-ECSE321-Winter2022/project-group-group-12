
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
        logIn: function (email, password) {
            login(email,password)
            .then(response => {
                localStorage.email = email;
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