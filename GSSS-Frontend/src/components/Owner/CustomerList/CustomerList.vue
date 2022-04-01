<template>
    <div>
        <h1>Customers</h1>
        <div class="ham">
            <div class="menu">
                <ul class="selectable-list">
                    <li v-for="(customer, i) in customers" :key=customer.email v-on:click="selectCustomer(i)">{{ customer.email }}</li>
                </ul>
                <button v-on:click="addCustomer()">Add customer</button>
            </div>
            <div class="vertical-separator"/>
            <div class="details">
                <customer-detail v-if="!addMode && selectedCustomer >= 0" v-bind:onChange="onChange" v-bind:customer="customers[selectedCustomer]"></customer-detail>
                <customer-creator v-if="addMode" v-bind:onAdd="onChange" />
            </div>
        </div>
    </div>
</template>

<script src='./customerList.js'></script>

<style scoped>
    @import './customerList.css';
</style>
