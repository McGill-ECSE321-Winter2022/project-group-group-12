import { createItem, getAllCategories } from "../../../services/item";

export default {
    name: 'customer-creator',
    data: function(){
        return {
            item: {
                name: null,
                description: null,
                imageUrl: null,
                remainingQuantity: null,
                price: null,
                isAvailableForOrder: null,
                isStillAvailable: null,
                selectedCategory: null
            },
            categories: [],
            error: '',
            success: ''
        }
    },    
    created: function(){
        getAllCategories()
        .then(res => this.categories = res)
        .catch(err => console.log(err));
    },
    methods: {
        save: function(){
            if(!this.item.name || !this.item.description || 
                !this.item.imageUrl || !this.item.remainingQuantity || 
                !this.item.price || !this.item.selectedCategory) {
                    this.error = 'Please fill in all fields';
                    setTimeout(() => this.error = null, 3000);
                    return;
            }

            createItem(this.item)
            .then(res => {
                success = 'Item created successfully'
                setTimeout(() => this.success = null, 3000);
            })
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