<!--
Author: Wassim Jabbour
About: Page to confirm the purchase and select the type of order (Adds the fee)
-->

<!-- CSS uses bootstrap -->
<template>
  <div class="container">
    <div class="row mt-2">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Product</th>
            <th scope="col">Product Name</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
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
              {{ product.price.toFixed(2) }}$/unit
            </td>
            <td>
              {{ product.count }}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="row" style="width:100%">
      <div class="col text-center" style="width:100%; text-align:center">
        <h4>Total cost: {{ total.toFixed(2) }}$</h4>
      </div>
    </div>
    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
    <div style="text-align:center">
      <select  placeholder="Select delivery type" class="select-css" v-model="type">
        <option value="Delivery">Delivery</option>
        <option value="Pickup">Pickup</option>
      </select>
      <br>
      <button v-on:click="payment()" class="payment">
        Proceed to payment
      </button>
    </div>
    <br>
    <div style="text-align:center">
        <p v-if="this.type == 'Delivery' && this.deliveryFee != 0">
            Note: A delivery fee of {{ deliveryFee.toFixed(2) }}$ will apply (Out of city customer)
        </p>
    </div>
  </div>
</template>

<script src="./confirmOrderType.js" />

<style scoped src="./confirmOrderType.css"/>
