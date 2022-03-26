<template>
    <div class="signup-container">
        <div>
            <h1>Sign Up</h1>
            <div>Full Name:</div>
            <input placeholder="Enter your full name" type="text" />
            <div>Email:</div>
            <input placeholder="Enter email address" type="text" />
            <div>Username:</div>
            <input placeholder="Enter username" type="text" />
            <div>Password:</div>
            <input placeholder="Enter password" type="password" />
            <div>Address:</div>
            <input placeholder="Enter street name" type="text" />
            <input placeholder="Enter street number" type="text" />
            <input placeholder="Enter city" type="text" />
            <input placeholder="Enter postal code" type="text" />

            <button>Sign Up</button>
        </div>
    </div>
</template>

<style scoped>
    @import './signup.css';
</style>

<script>
export default{
    name: 'SignupPage',
    data(){
        return {

        }
    }
}
</script>