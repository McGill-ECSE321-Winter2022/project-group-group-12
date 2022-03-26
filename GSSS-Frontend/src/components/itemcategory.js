function ItemCategoryDto (name){
    this.name = name
}

export default {
    name: 'addModifyCategory',
    data () {
      return {
        itemCategories: [],
        newItemCategory: '',
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