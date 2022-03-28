<template>
    <div>
        <h3>Create new customer</h3>
        <div>
            <label>Email: </label>
            <input type="text" placeholder="Enter email" v-model="email"/>
            <br/>
            <label>Username: </label>
            <input type="text" placeholder="Enter username" v-model="username"/>
            <br/>
            <label>Password: </label>
            <input type="password" placeholder="Enter password" v-model="password"/>
            <br/>
            <label>Disabled: </label>
            <input style="margin-bottom: 1rem" type="checkbox" v-model="disabled" />
            <div>Address:</div>
            <div class="address-input"><input type="text" placeholder="Full Name" v-model="address.fullName"/></div>
            <div class="address-input">
                <input type="number" placeholder="Street No" v-model="address.streetNumber"/>, 
                <input type="text" placeholder="Street name" v-model="address.streetName"/>
            </div>
            <div class="address-input">
                <input type="text" placeholder="City" v-model="address.city"/>, 
                <input type="text" placeholder="Postal Code" v-model="address.postalCode"/>
            </div>
        </div>
        <button v-on:click="save()">Save</button>
    </div>
</template>

<script src="./customerCreator.js" />

<style scoped>
    @import "./customerCreator.css";
</style>