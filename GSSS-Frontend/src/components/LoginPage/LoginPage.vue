<template>
    <div class="login-container">
        <div>
            <h1>Login</h1>
            <div>Username:</div>
            <input placeholder="Enter username" type="text" />
            <div>Password:</div>
            <input placeholder="Enter password" type="password" />
            <button>Login</button>
        </div>
    </div>
</template>

<style scoped>
    @import './login.css';
</style>

<script>
export default{
    name: 'LoginPage',
    data(){
        return {

        }
    }
}
</script>
