<template>
  <div class="container">
    <div class="login-container">
      <div>
        <h1>Login</h1>
        <div>Email:</div>
        <input v-model="email" placeholder="Enter email" type="text"/>
        <div>Password:</div>
        <input v-model="password" placeholder="Enter password" type="password"/>
        <button @click="logIn()">Log In</button>
        <p><a href="/#/signup">Create an account!</a></p>
      </div>
    </div>
    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
  </div>
</template>

<script src="./loginPage.js"/>

<style scoped src='./login.css'/>
