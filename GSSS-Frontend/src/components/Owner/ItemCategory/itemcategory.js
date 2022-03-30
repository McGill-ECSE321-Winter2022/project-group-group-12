// Importing axios and setting up URLs
import axios from 'axios'
var config = require('../../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })

export default {
    name: 'addModifyCategory',
    data () {
      return {
        itemCategories: [],
        errorItemCategory: '',
        response: []
      }
    },
      methods: {
        createItemCategory: function (itemCategoryName) {
          
          // Create a new item category and add it to the list of item categories
          var ic = new ItemCategoryDto(itemCategoryName)
          this.itemCategories.push(ic)
          // Reset the name field for new item categories
          this.newItemCategory = ''
        }
      }
  }