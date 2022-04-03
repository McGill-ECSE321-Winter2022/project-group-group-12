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

<style scoped>
    @import './OwnerItemCategory.css';
</style>

<script>
// Importing axios and setting up URLs
import axios from 'axios'
var config = require('../../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })

function ItemCategoryDto(name){
  this.name = name
}

export default {
  name: 'addModifyCategory',
  data () {
    return {
      itemCategories: [],
      newItemCategory: '',
      newItemCategory2: '',
      oldItemCategory: '',
      error: '',
      success: '',
      response: [],
    }
  },
  created: function (itemCategoryName) { 
    AXIOS.get('/itemCategories/')
    .then(response => {
      this.itemCategories = response.data
    }).catch(e => {
    this.error = e.response.data
    setTimeout(() => this.error = null, 3000);
    })
  },

  methods: {
    onSelect: function(i) {
      // Set the selected purchase to be the one at index i
      this.oldItemCategory = this.itemCategories[i].name
    },

    createItemCategory: function(name){
      AXIOS.post('/itemCategory/',
      {},
      {
        params: {
          name: name
        },
      }
      )
      .then((response) => {
        this.error = ""
        this.newItemCategory = ""
        this.itemCategories.push(response.data)        
        this.success = "Item category created successfully"
        setTimeout(()=>this.success=null, 3000)
      })
      .catch(e => {
        this.error = e.response.data
        setTimeout(() => this.error = null, 3000);
      })
    },

    modifyItemCategory: function(categoryOldName, categorynewName){
      AXIOS.post('/itemCategory/modify/',
      {},
      {
        params: {
          oldName: categoryOldName,
          newName: categorynewName
        }
      })
      .then(response => {
        this.oldItemCategory = ''
        this.newItemCategory2 = ''
        location.reload(true);
      })
      .catch(e => {
        this.error = e.response.data
        setTimeout(() => this.error = null, 3000);
      })
    },

  }
}
</script>