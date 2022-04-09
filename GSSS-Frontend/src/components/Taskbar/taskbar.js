import { logOut } from "../../services/login";

function getPermission(){
    if(localStorage.permission) return permissions[localStorage.permission];
    return -1;
}

// function getOpt(period) {
//     if (period.value = "Accountinfo") { document.getElementById("abc").href="/#/employee/account"; }
//     else if (period.value = "Customers") { document.getElementById("abc").href="/#/employee/customers"; }
//     else { document.getElementById("abc").href="/#/employee/purchases"; } 
//     }


const permissions = {
    None: -1,
    Customer: 0,
    Employee: 1,
    Owner: 2
}

export default {
    name: 'taskbar',
    data: function(){
        return {
            permission: -1
        }
    },

    created: function() {
        this.permission = getPermission();
    },

    methods: {
        logout: function(){
            logOut();
            this.$router.push( { name: 'LoginPage' } );
        },
        goToNewPage: function()
        {
            var url = document.getElementById('list').value;
            if(url != 'none') {
            window.location = url;
        }
    }
    },

    watch: {
        '$route'() {
          this.permission = getPermission();
        }
    }
}