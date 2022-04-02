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
            <select v-model='item.selectedCategory'>
                <option v-for="category in this.categories" :key="category.name"> 
                    {{ category.name }}
                </option>
            </select>
            <br/>
        </div>
        <div v-if="error" class="error">
            <div>
                {{ error }}
            </div>
        </div>
        <div v-if="success" class="success">
            <div>
                {{ success }}
            </div>
        </div>
        <button v-on:click="save()">Create</button>
    </div>
</template>

<script src="./itemCreator.js" />

<style scoped src="./itemCreator.css">
</style>