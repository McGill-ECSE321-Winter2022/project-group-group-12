<template>
    <div>
        <h3>{{ employee.email }}</h3>
        <div v-if="mode == 0">
            <div>Username: {{ employee.username }}</div>
            <div style="margin-bottom: 1rem">Disabled: {{ employee.disabled }}</div>
            <div>Address:</div>
            <p>{{ employee.address.fullName }}<br/>{{employee.address.streetNumber}}, {{ employee.address.streetName }}<br/>{{ employee.address.city }}, {{ employee.address.postalCode }}</p>
        </div>
        <div v-if="mode == 1">
            <label>Username: </label>
            <input type="text" placeholder="Enter username" v-model="mod.username"/>
            <br/>
            <label>Disabled: </label>
            <input style="margin-bottom: 1rem" type="checkbox" v-model="mod.disabled" />
            <div>Address:</div>
            <div class="address-input"><input type="text" placeholder="Full Name" v-model="mod.address.fullName"/></div>
            <div class="address-input">
                <input type="number" placeholder="Street No" v-model="mod.address.streetNumber"/>, 
                <input type="text" placeholder="Street name" v-model="mod.address.streetName"/>
            </div>
            <div class="address-input">
                <input type="text" placeholder="City" v-model="mod.address.city"/>, 
                <input type="text" placeholder="Postal Code" v-model="mod.address.postalCode"/>
            </div>
        </div>
        <div v-if="mode == 2">
            <input type="password" placeholder="Enter new password" v-model="mod.password" />
        </div>
        <div v-if="message" v-bind:style="isSuccess ? 'color: green; font-style: italic;' : 'color: red; font-style: italic;'">{{ message }}</div>
        <button v-if="mode == 0">View shifts</button>
        <button v-if="mode == 0">View assigned purchases</button>
        <button v-if="mode == 0" v-on:click="changeMode(1)">Edit info</button>
        <button v-if="mode == 0" v-on:click="changeMode(2)">Edit password</button>
        <button class="save-button" v-if="mode != 0" v-on:click="changeMode(0, false)">Cancel</button>
        <button class="save-button" v-if="mode != 0" v-on:click="changeMode(0, true)">Save</button>
    </div>
</template>

<script src='./employeeDetail.js'></script>

<style scoped src='./employeeDetail.css'>
</style>