// Importing axios and setting up URLs
import axios from 'axios'
var config = require('../../../../config')
const backendUrl = (process.env.NODE_ENV === "production")
  ? `https://${config.build.backendHost}`
  : `http://${config.dev.backendHost}:${config.dev.backendPort}`;
const frontendUrl = (process.env.NODE_ENV === "production")
  ? `https://${config.build.host}`
  : `http://${config.dev.host}:${config.dev.port}`;
const AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

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

createItemCategory: function(){
  if(!this.newItemCategory) {
    this.error = 'Please fill in fields';
    setTimeout(() => this.error = null, 3000);
    return;
  }
  AXIOS.post('/itemCategory/',
  {},
  {
    params: {
      name: this.newItemCategory
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

modifyItemCategory: function(){
  if(!this.oldItemCategory || !this.newItemCategory2) {
    this.error = 'Please fill in fields';
    setTimeout(() => this.error = null, 3000);
    return;
  }
  AXIOS.post('/itemCategory/modify/',
  {},
  {
    params: {
      oldName: this.oldItemCategory,
      newName: this.newItemCategory2
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