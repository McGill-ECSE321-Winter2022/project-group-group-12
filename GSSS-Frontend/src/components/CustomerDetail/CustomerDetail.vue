<template>
    <div>
        <h3>{{ customer.email }}</h3>
        <div v-if="!editMode">
            <div>Username: {{ customer.username }}</div>
            <div style="margin-bottom: 1rem">Disabled: {{ customer.disabled }}</div>
            <div>Address: ({{ customer.address.id }})</div>
            <p>{{ customer.address.fullName }}<br/>{{customer.address.streetNumber}}, {{ customer.address.streetName }}<br/>{{ customer.address.city }}, {{ customer.address.postalCode }}</p>
        </div>
        <div v-if="editMode">
            <label>Username: </label>
            <input type="text" placeholder="Enter username" v-model="mod.username"/>
            <br/>
            <label>Disabled: </label>
            <input style="margin-bottom: 1rem" type="checkbox" v-model="mod.disabled" />
            <div>Address: ({{ customer.address.id }})</div>
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
        <div v-if="message" v-bind:style="isSuccess ? 'color: green; font-style: italic;' : 'color: red; font-style: italic;'">{{ message }}</div>
        <button>View purchases</button>
        <button v-on:click="changeMode()">{{ this.editMode ? "Save" : "Edit" }}</button>
    </div>
</template>

<script src='./customerDetail.js'></script>

<style scoped>
    @import './customerDetail.css';
</style>