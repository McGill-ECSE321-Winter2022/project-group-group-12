import ax from "./common";

export const createItem = (item) => new Promise((resolve, reject) => {
        ax.post('/item', null, { params: {
            name: item.name,
            description: item.description,
            imageUrl : item.imageUrl,
            remainingQuantity: item.remainingQuantity,
            price: item.price, 
            isAvailableForOrder: item.isAvailableForOrder,
            isStillAvailable: item.isStillAvailable,
            selectedCategory: item.selectedCategory
        }})
        .then(res => resolve(res))
        .catch(err => reject(err));
});

export const getAllCategories = (itemCategory) => new Promise((resolve, reject) => {
    ax.get('/itemCategories')
    .then(res => resolve(res.data))
    .catch(err => reject(err));
});