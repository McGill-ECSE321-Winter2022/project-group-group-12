<template>
    <div>
        <h3>Create new employee</h3>
        <div>
            <label>Email: </label>
            <input type="text" placeholder="Enter email" v-model="employee.email"/>
            <br/>
            <label>Username: </label>
            <input type="text" placeholder="Enter username" v-model="employee.username"/>
            <br/>
            <label>Password: </label>
            <input type="password" placeholder="Enter password" v-model="employee.password"/>
            <br/>
            <div>Address:</div>
            <div class="address-input"><input type="text" placeholder="Full Name" v-model="employee.address.fullName"/></div>
            <div class="address-input">
                <input type="number" placeholder="Street No" v-model="employee.address.streetNumber"/>, 
                <input type="text" placeholder="Street name" v-model="employee.address.streetName"/>
            </div>
            <div class="address-input">
                <input type="text" placeholder="City" v-model="employee.address.city"/>, 
                <input type="text" placeholder="Postal Code" v-model="employee.address.postalCode"/>
            </div>
        </div>
        <p v-if="error" style="color:red; font-style:italic;">{{ error }}</p>
        <button v-on:click="save()">Save</button>
    </div>
</template>

<script src="./employeeCreator.js" />

<style scoped src="./employeeCreator.css">
</style>