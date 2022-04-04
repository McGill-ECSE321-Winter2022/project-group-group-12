<!--
Author: Wassim Jabbour
About: Page to handle viewing and editing the items as the owner
-->

<!-- CSS uses bootstrap -->
<template>
  <div class="container">
    <div class="allItems">
      <div class="items" v-for="(product, index) in this.items" :key="product.name">
        <div class="card" v-on:click="selectItem(index)" style="width: 10rem;">
          <img :src="product.imageUrl" class="card-img-top" />
          <div class="card-body">
            <h6 class="card-title">
              {{ product.name }} - {{ product.price }}$
            </h6>
          </div>
        </div>
      </div>
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
    <div class="operations">
      <div v-if="this.selectedItem != -1">
              <h2> Modify: {{ this.name }} </h2>
              <label>Description: </label>
              <input type="text" placeholder="Enter description" v-model="description"/>
              <br/>
              <label>Image URL: </label>
              <input type="text" placeholder="Enter image URL" v-model="imageUrl" />
              <br/>
              <label>Remaining quantity: </label>
              <input type="number" placeholder="Enter quantity" v-model="remainingQuantity" />
              <br/>
              <label>Price (In $): </label>
              <input type="number" placeholder="Enter price" v-model="price" />
              <br/>
              <label>Available for pickup and delivery: </label>
              <input type="checkbox" v-model="availableForOrder" />
              <br/>
              <label>Still available (Not out of supply): </label>
              <input type="checkbox" v-model="stillAvailable" />
              <br/>
              <label>Item category: </label>
              <select v-model="itemCategory">
                <option v-for="ic in itemCategories" :value="ic.name" :key="ic.name">
                  {{ ic.name }}
                </option>
              </select>
              <button v-on:click="modifyItem()">Modify</button>
        </div>
        <div v-if="selectedItem!=-1" class = "vertical-separator" style="margin: 0 3rem"></div>
        <ItemCreator/>
      </div>
  </div>
</template>

<script src="./viewAndEditItems.js" />

<style scoped src="./viewAndEditItems.css" />
