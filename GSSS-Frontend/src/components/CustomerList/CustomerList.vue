<template>
    <div>
        <h1>Customers</h1>
        <div class="ham">
            <div class="menu">
                <ul class="selectable-list">
                    <li v-for="(customer, i) in customers" :key=customer.email v-on:click="selectCustomer(i)">{{ customer.email }}</li>
                </ul>
            </div>
            <div class="details">
                <customer-detail v-bind:customer="customers[selectedCustomer]"></customer-detail>
            </div>
        </div>
    </div>
</template>

<script src='./customerList.js'></script>

<style scoped>
    @import './customerList.css';
</style>
