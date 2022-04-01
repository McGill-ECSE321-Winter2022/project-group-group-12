<template>
    <div class="login-container">
        <div>
            <h1>Login</h1>
            <div>Email:</div>
            <input v-model="email" placeholder="Enter email" type="text" />
            <div>Password:</div>
            <input v-model="password" placeholder="Enter password" type="password" />
            <button @click="Login(Email, Password)">Login</button>
        </div>
    </div>
</template>

<style scoped>
    @import './login.css';
</style>

<script src="./loginPage.js" />
