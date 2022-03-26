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
            <button v-bind:disabled="!newItemCategory" @click="createItemCategory(newItemCategory)">Create Item Category</button>
          </td>

          <td>
            <input type="text" v-model="oldItemCategory" placeholder="Old Name">
          </td>

          <td>
            <input type="text" v-model="newItemCategory" placeholder="New name">
          </td>
          <td>
            <button v-bind:disabled="!oldItemCategory" @click="modifyItemCategory(oldItemCategory, newItemCategory)">Modify Item Category</button>
          </td>
      </tr>
      <tr >
        <td></td>
        <td colspan="2" class="space">All item categories in the system:</td>

      </tr>

      <tr v-for="itemcategory in itemCategories" :key=itemcategory.name>
            <td></td>
            <td> {{ itemcategory.name }} </td>
      </tr>

    </table>
    <p>
      <span v-if="errorItemCategory" style="color:red">Error: {{errorItemCategory}} </span>
    </p>  

    </div>

    

  
</template>

<style scoped>
    @import '../assets/styles/Owner_AddItemCategory.css';
</style>

<script src="./itemcategory.js">
</script>