<!--
Author: Wassim Jabbour
About: Page to handle selecting items and adding them to the customer's cart
-->

<!-- CSS uses bootstrap -->
<template>
  <div class="container">
    <div class="filter">
      <label>Filter by category:  </label>
      <select v-model="itemCategory" @change="filterItems()">
        <option v-for="ic in itemCategories" :value="ic" :key="ic">
          {{ ic }}
        </option>
      </select>
    </div>
    <div class="allItems">
      <div class="items" v-for="product in this.filteredItems" :key="product.name">
        <div class="card" v-bind:title="product.description" style="width: 10rem;">
          <img :src="product.imageUrl" class="card-img-top" />
          <div class="card-body">
            <h6 class="card-title">
              {{ product.name }} - {{ product.price }}$
            </h6>
            <!-- Can't add an item to cart if it is not available at the moment / just for in person purcases / Isn't in stock -->
            <button
              :disabled="!product.stillAvailable || !product.availableForOrder || product.remainingQuantity <= 0"
              v-on:click="addProduct(product)"
              href="#"
              class="btn  btn-block"
              :class="{
                'btn-primary': !product.cart,
                'btn-success': product.cart,
              }"
            >
              <!-- Under which conditions to describe an item as what -->
              {{ (!product.stillAvailable || !product.availableForOrder || product.remainingQuantity == 0) ? "Unavailable" : (!product.cart ? "Add" : "Added") }}
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="row mt-2">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Product</th>
            <th scope="col">Product Name</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col">Remove</th>
          </tr>
        </thead>
        <tbody>
          <!-- Displaying the cart -->
          <tr v-for="(product, index) in this.cart" :key="index">
            <th scope="row">{{ index + 1 }}</th>
            <th scope="row">
              <img :src="product.imageUrl" style="width: 4rem;" />
            </th>
            <td>{{ product.name }}</td>
            <td>
              {{ product.price }}$/unit
            </td>
            <td>
              <button
                v-on:click="decreaseQ(index)"
                class="btn btn-primary btn-sm"
              >
                -
              </button>
              {{ product.count }}
              <button
                v-on:click="increaseQ(index)"
                class="btn btn-primary btn-sm"
                size="sm"
              >
                +
              </button>
            </td>

            <td>
              <button v-on:click="removeProduct(index)" class="btn btn-danger">
                Remove
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="row" style="width:100%">
      <div style="text-align:center; width:100%" class="col text-center">
        <h4>Total cost: {{ total }}$</h4>
      </div>
    </div>
    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
    <div style="text-align:center">
      <button v-on:click="checkout()" class="checkout">
        Proceed to checkout
      </button>
    </div>
  </div>
</template>

<script src="./viewAndSelectItems.js" />



<style scoped src="./viewAndSelectItems.css"/>
