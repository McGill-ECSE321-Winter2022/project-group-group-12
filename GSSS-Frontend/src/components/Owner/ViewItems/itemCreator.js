import { createItem, getAllCategories } from "../../../services/item";

export default {
    name: 'customer-creator',
    data: function(){
        return {
            item: {
                name: '',
                description: '',
                imageUrl: '',
                remainingQuantity: '',
                price: '',
                isAvailableForOrder: '',
                isStillAvailable: '',
                selectedCategory: ''
            },
            categories: [],
            error: ''
        }
    },    
    created: function(){
        getAllCategories()
        .then(res => this.categories = res)
        .catch(err => console.log(err));
    },
    methods: {
        save: function(){
            this.item.selectedCategory = select.options[select.selectedIndex].value;
            createItem(this.item)
            .then(res => this.onAdd())
            .catch(err => {
                this.error = err;
                setTimeout(() => this.error = null, 3000);
            });
        }
    },
    props:{
        onAdd: Function
    }
}