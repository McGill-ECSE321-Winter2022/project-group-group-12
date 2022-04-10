import ItemCreator from '@/components/Owner/ViewItems/ItemCreator.vue'

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

    export default {

  name: 'ViewAndSelectItems',

  data () {
    return {
      items: [],
      itemCategories: [],
      error: '',
      success: '',
      name: '',
      selectedItem: -1,
      description: '',
      imageUrl: '',
      remainingQuantity: 0,
      price: 0,
      availableForOrder: true,
      stillAvailable: true,
      itemCategory: '',
      menu: true
    }
  },

  created: function() {
    
    // Getting the items from the backend
    AXIOS.get('/items')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.items = response.data
      
      })
    .catch(e => {
      this.error = e.response.data
      setTimeout(()=>this.error=null, 3000)
    })

    // Getting the item categories from the backend
    AXIOS.get('/itemCategories')
    .then(response => {
      
      // JSON responses are automatically parsed.
      this.itemCategories = response.data
      
      })
    .catch(e => {
      this.error = e.response.data
      setTimeout(()=>this.error=null, 3000)
    })
  },

  // User defined methods
  methods: {
    
    // Modifies the selected item and the default value of all modify fields  
    selectItem: function(i) {
      // Set the menu to disappear
      this.menu = false

      this.selectedItem = i
      this.name = this.items[i].name
      this.description = this.items[i].description
      this.imageUrl = this.items[i].imageUrl
      this.remainingQuantity = this.items[i].remainingQuantity
      this.price = this.items[i].price
      this.availableForOrder = this.items[i].availableForOrder
      this.stillAvailable = this.items[i].stillAvailable
      this.itemCategory = this.items[i].category.name
    },

    modifyItem: function() {
      
      // Input validation
      if(this.remainingQuantity < 0) {
        this.error = "Remaining quantity cannot be negative"
        setTimeout(()=>this.error=null, 3000)
        return
      }
      if(this.price <= 0) {
        this.error = "Price must be strictly positive"
        setTimeout(()=>this.error=null, 3000)
        return
      }

      // Modify the item
      // Getting the items from the backend
      AXIOS.post('/item/' + this.name, null, 
      {
        params: {
          description: this.description,
          imageUrl: this.imageUrl,
          remainingQuantity: this.remainingQuantity,
          price: this.price,
          availableForOrder: this.availableForOrder,
          stillAvailable: this.stillAvailable,
          itemCategory: this.itemCategory
        }
      })
      .then(response => {
          this.items[this.selectedItem] = response.data
          this.selectedItem = -1
          this.success = "Item modified successfully"
          setTimeout(()=>this.success=null, 3000)
        })
      .catch(e => {
        this.error = e.response.data
        setTimeout(()=>this.error=null, 3000)
      })
    }


    },
    
    components: {
      ItemCreator
    }
  }