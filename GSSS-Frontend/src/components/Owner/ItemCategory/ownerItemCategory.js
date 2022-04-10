import { getAllCategories, createCategory, modifyCategory, deleteCategory } from "../../../services/itemCategory";

export default {
  name: 'addModifyCategory',
  data () {
    return {
      itemCategories: [],
      editedCategories: [],
      newCategory: '',
      error: '',
      success: false
    }
  },
  created: function () {
    getAllCategories()
    .then(res => this.itemCategories = res)
    .catch(err => {
      this.error = err;
      setTimeout(() => this.error = null, 5000);
    });
  },

  methods: {
    edit: function(i){
      this.itemCategories[i].edit = true;
      this.itemCategories = [...this.itemCategories];
      this.editedCategories[i] = this.itemCategories[i].name;
      this.editedCategories = [...editedCategories];
    },
    cancel: function(i){
      this.itemCategories[i].edit = false;
      this.itemCategories = [...this.itemCategories];
    },
    save: function(i){
      if(this.itemCategories[i].name == this.editedCategories[i]) {
        cancel(i);
        return;
      }
      modifyCategory(this.itemCategories[i].name, this.editedCategories[i])
      .then(res => {
        this.itemCategories[i] = { name: this.editedCategories[i] };
        this.itemCategories = [...this.itemCategories];
        this.success = true;
        this.error = "Category succesfully modified!";
        setTimeout(() => this.error = null, 5000);
      })
      .catch(err => {
        this.success = false;
        this.error = err;
        setTimeout(() => this.error = null, 5000);
      });
    },
    create: function(){
      createCategory(this.newCategory)
      .then(res => {
        this.itemCategories.push({ name: this.newCategory });
        this.itemCategories = [...this.itemCategories];
        this.newCategory = null;
        this.success = true;
        this.error = "Category succesfully created!";
        setTimeout(() => this.error = null, 5000);
      })
      .catch(err => {
        this.success = false;
        this.error = err;
        setTimeout(() => this.error = null, 5000);
      })
    },
    remove: function(i){
      deleteCategory(this.itemCategories[i].name)
      .then(res => {
        this.itemCategories.splice(i, 1);
        this.success = true;
        this.error = "Category succesfully deleted";
        setTimeout(() => this.error = null, 5000);
      })
      .catch(err => {
        this.success = false;
        this.error = err;
        setTimeout(() => this.error = null, 5000);
      });
    }
  }
}