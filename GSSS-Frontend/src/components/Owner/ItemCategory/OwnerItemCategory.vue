<template>
  <div class="addModifyCategory">
    <h4>Add a new item category</h4>
    <input type="text" v-model="newItemCategory" placeholder="Item category name">
    <button v-bind:disabled="!newItemCategory" @click="createItemCategory()">Create Category</button>
    <h4>Modify an item category</h4>
    <input type="text" v-model="oldItemCategory" placeholder="Old Name">
    <input type="text" v-model="newItemCategory2" placeholder="New name">
    <button v-bind:disabled="!oldItemCategory" @click="modifyItemCategory()">Modify Category</button>

    <div class = "categories">
      <p>All item categories in the system</p>
      <ul v-for="(itemcategory, i) in itemCategories" :key="itemcategory.name" v-on:click="onSelect(i)">
          <li>{{ itemcategory.name }}</li>
      </ul>
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
</template>

<script src="./ownerItemCategory.js" />

<style scoped src="./OwnerItemCategory.css" />