<template>
    <div>
        <h1>Customers</h1>
        <div class="ham">
            <div class="menu" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
                <ul class="selectable-list">
                    <li v-for="(customer, i) in customers" :key=customer.email v-on:click="selectCustomer(i)">{{ customer.email }}</li>
                </ul>
                <button v-on:click="addCustomer()">Add customer</button>
                <div v-if="error" style="color: red; font-style: italic;">{{ error }}</div>
            </div>
            <div class="vertical-separator"/>
            <div class="details" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
                <button class="back-button" v-on:click="menu = true">Back</button>
                <customer-detail v-if="!addMode && selectedCustomer >= 0" v-bind:onChange="onChange" v-bind:customer="customers[selectedCustomer]"></customer-detail>
                <customer-creator v-if="addMode" v-bind:onAdd="onChange" />
            </div>
        </div>
    </div>
</template>

<script src='./customerList.js' />

<style scoped src='./customerList.css' />
