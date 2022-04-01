<template>
    <div>
        <h3>Create new customer</h3>
        <div>
            <label>Email: </label>
            <input type="text" placeholder="Enter email" v-model="customer.email"/>
            <br/>
            <label>Username: </label>
            <input type="text" placeholder="Enter username" v-model="customer.username"/>
            <br/>
            <label>Password: </label>
            <input type="password" placeholder="Enter password" v-model="customer.password"/>
            <br/>
            <div>Address:</div>
            <div class="address-input"><input type="text" placeholder="Full Name" v-model="customer.address.fullName"/></div>
            <div class="address-input">
                <input type="number" placeholder="Street No" v-model="customer.address.streetNumber"/>, 
                <input type="text" placeholder="Street name" v-model="customer.address.streetName"/>
            </div>
            <div class="address-input">
                <input type="text" placeholder="City" v-model="customer.address.city"/>, 
                <input type="text" placeholder="Postal Code" v-model="customer.address.postalCode"/>
            </div>
        </div>
        <p v-if="error" style="color:red; font-style:italic;">{{ error }}</p>
        <button v-on:click="save()">Save</button>
    </div>
</template>

<script src="./customerCreator.js" />

<style scoped src="./customerCreator.css">
</style>