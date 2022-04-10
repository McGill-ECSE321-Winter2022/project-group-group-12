<template>
  <div class="container">
    <div class="signup-container">
      <div>
        <h1>Sign Up</h1>
        <div>Full Name:</div>
        <input placeholder="full name" type="text" v-model="customer.address.fullName"/>
        <div>Email:</div>
        <input placeholder="email address" type="text" v-model="customer.email"/>
        <div>Username:</div>
        <input placeholder="username" type="text" v-model="customer.username"/>
        <div>Password:</div>
        <input placeholder="password" type="password" v-model="customer.password"/>
        <div>Address:</div>
        <input placeholder="street number" type="text" v-model="customer.address.streetNumber"/>
        <input placeholder="street name" type="text" v-model="customer.address.streetName"/>
        <input placeholder="city" type="text" v-model="customer.address.city"/>
        <input placeholder="postal code" type="text" v-model="customer.address.postalCode"/>
        <button v-on:click="save()">Sign Up</button>
        <p><a href="/#/login">Already have an account?</a></p>
      </div>
    </div>
    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
  </div>
</template>

<script src="./signUp.js"/>

<style scoped src='./signup.css'/>
