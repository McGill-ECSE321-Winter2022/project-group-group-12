<!--
Author: Wassim Jabbour
About: Page to handle viewing and editing the items as the owner
-->

<!-- CSS uses bootstrap -->

<template>
  <div>
    <h1>Items</h1>

    <p v-if="this.items.length == 0">
      No items were found in the system.
    </p>

    <div class="ham" v-if="this.items.length != 0">
      <div class="menu" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
        <ul class = "selectable-list">
          <li v-for="(item, i) in this.items" :key="item.name" v-on:click="selectItem(i)">
            {{ item.name }}
          </li>
        </ul>
        <button v-on:click="createItem()">Create Item</button>
      </div>
      

      <div class = "vertical-separator"/>
      <div class="details" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
        <div v-if="this.selectedItem != -1">
            <button class="back-button" v-on:click="menu = true">Back</button>
              <div v-if="!addMode" v-bind:onChange="onChange">
                <h2> Modify: {{ this.name }} </h2>
                
                <div>
                    <div class="items">
                      <div class="card" style="width: 10rem;">
                        <img :src="this.imageUrl" class="card-img-top" />
                        <div class="card-body">
                          <h6 class="card-title">
                            {{ this.name }} - {{ this.price.toFixed(2) }}$
                          </h6>
                        </div>
                     </div>
                    </div>
                </div>
                
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
              
              <div v-if="addMode" v-bind:onAdd="onChange">
                <ItemCreator/>
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

    </div>
  </div>
  </div>
</template>

<script src="./viewAndEditItems.js" />

<style scoped src="./viewAndEditItems.css" />
