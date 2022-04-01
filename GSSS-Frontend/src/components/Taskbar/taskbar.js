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
            permission: getPermission()
        }
    }
}