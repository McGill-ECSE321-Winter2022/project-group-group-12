<template>
    <div>
        <h3>Create new item</h3>
        <div>
            <label>Name: </label>
            <input type="text" placeholder="Enter name" v-model="item.name"/>
            <br/>
            <label>Description: </label>
            <input type="text" placeholder="Enter description" v-model="item.description"/>
            <br/>
            <label>Image URL: </label>
            <input type="text" placeholder="Enter image url" v-model="item.imageUrl"/>
            <br/>
            <label>Remaining Quantity: </label>
            <input type="text" placeholder="Enter remaining quantity" v-model="item.remainingQuantity"/>
            <br/>
            <label>Price: </label>
            <input type="text" placeholder="Enter price" v-model="item.price"/>
            <br/>
            <label>Availability for order: </label>
            <input style="margin-bottom: 1rem" type="checkbox" v-model="item.isAvailableForOrder" />
            <br/>
            <label>Availability: </label>
            <input style="margin-bottom: 1rem" type="checkbox" v-model="item.isStillAvailable" />
            <br/>
            <label>Category: </label>
            <select id="selectCategory" placeholder="Select category">
            <tr v-for="(category, index) in categories" :key="index">
            <option value=key.name> key.name </option>
            </tr>
            </select>
            <br/>
        </div>
        <p v-if="error" style="color:red; font-style:italic;">{{ error }}</p>
        <button v-on:click="save()">Create</button>
    </div>
</template>

<script src="./itemCreator.js" />

<style scoped src="./itemCreator.css">
</style>