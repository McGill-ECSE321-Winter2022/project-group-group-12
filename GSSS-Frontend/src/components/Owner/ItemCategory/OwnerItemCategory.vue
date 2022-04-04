<template>
  <div class="addModifyCategory">
    <table>
      <tr>
        <td>Add a new item category</td>
        <td></td>
        <td>Modify an item category</td>
      </tr>
      <tr>
          <td>
            <input type="text" v-model="newItemCategory" placeholder="Item category name">
          </td>

          <td width=27%>
            <button v-bind:disabled="!newItemCategory" @click="createItemCategory(newItemCategory)">Create Category</button>
          </td>

          <td>
            <input type="text" v-model="oldItemCategory" placeholder="Old Name">
          </td>

          <td>
            <input type="text" v-model="newItemCategory2" placeholder="New name">
          </td>

          <td width=27%>
            <div>
              <button v-bind:disabled="!oldItemCategory" @click="modifyItemCategory(oldItemCategory, newItemCategory2)">Modify Category</button>
            </div>
          </td>
      </tr>
      <tr >
        <td></td>
        <td colspan="2" class="space">All item categories in the system:</td>
      </tr>

      <div class = "categories">
        <tr v-for="(itemcategory, i) in itemCategories" :key="itemcategory.name" v-on:click="onSelect(i)">
              <td></td>
              <td> - {{ itemcategory.name }} </td>
        </tr>
      </div>
    </table>

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