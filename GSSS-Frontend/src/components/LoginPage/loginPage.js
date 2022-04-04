
import { login } from "../../services/login";

export default{
    name: 'LoginPage',
    data: function() {
        return {
            email: '',
            password: '',
            error: ''
        }
    },
    methods: {
        logIn: function () {
          if(!this.email || !this.password) {
                this.error = 'Please fill in all fields';
                setTimeout(() => this.error = null, 3000);
                return;
        }

            login(this.email, this.password)
            .then(response => {
                localStorage.setItem('email', this.email);
                localStorage.setItem('permission', response.data);
                if(response.data == "Customer") this.$router.go('/')
                else this.$router.go('/')
              })
              .catch((error) => {
                this.error = error
                setTimeout(() => this.error = null, 3000);
              })
              
        }
    }

}