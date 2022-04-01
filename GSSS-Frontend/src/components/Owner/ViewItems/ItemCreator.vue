<template>
    <div>
        <h3>Create new item</h3>
        <div>
            <label>Name: </label>
            <input type="text" placeholder="Enter name" v-model="item.name"/>
            <br/>
            <label>Description: </label>
            <input type="text" placeholder="Enter username" v-model="item.description"/>
            <br/>
            <label>image URL: </label>
            <input type="password" placeholder="Enter password" v-model="customer.password"/>
            <br/>
        </div>
        <p v-if="error" style="color:red; font-style:italic;">{{ error }}</p>
        <button v-on:click="save()">Save</button>
    </div>
</template>

<script src="./itemCreator.js" />

<style scoped src="./itemCreator.css">
</style>