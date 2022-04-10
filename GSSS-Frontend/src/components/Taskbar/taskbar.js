import { logOut } from "../../services/login";

function getPermission(){
    if(localStorage.permission) return permissions[localStorage.permission];
    return -1;
}

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
            permission: -1,
            taskbar: false
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

        toggleTaskbar: function() {
            this.taskbar = !this.taskbar;
        }
    },

    watch: {
        '$route'() {
          this.permission = getPermission();
        }
    }
}