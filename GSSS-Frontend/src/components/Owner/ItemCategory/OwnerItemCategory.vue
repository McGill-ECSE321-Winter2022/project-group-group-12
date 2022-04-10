<template>
  <div class="addModifyCategory">
    <h1>Item Categories</h1>
    <ul class="editable-list">
      <li v-for="(category, i) in itemCategories" :key="category.name">
        <p v-if="!category.edit">{{ category.name }}</p>
        <input v-if="category.edit" type="text" v-bind:placeholder="category.name" v-model="editedCategories[i]"/>
        <button v-if="!category.edit" class="image-button" v-on:click="edit(i)"><img style="height: 1.5rem" src="../../../assets/edit-icon.svg" /></button>
        <button v-if="category.edit" class="image-button" v-on:click="cancel(i)"><img style="height: 1.5rem" src="../../../assets/cancel-icon.svg" /></button>
        <button v-if="category.edit" class="image-button" v-on:click="save(i)"><img style="height: 1.5rem" src="../../../assets/save-icon.svg" /></button>
      </li>
    </ul>

    <div class="create-category">
      <h3>New category:</h3>
      <div class="create-category-input">
        <input type="text" placeholder="Category" v-model="newCategory" />
        <button v-on:click="create()">Create</button>
      </div>
    </div>

    <div v-if="error" class="error"><div v-bind:style="`color: ${success ? 'green' : 'red'}`">{{ error }}</div></div>  

  </div>
</template>

<script src="./ownerItemCategory.js" />

<style scoped src="./OwnerItemCategory.css" />
