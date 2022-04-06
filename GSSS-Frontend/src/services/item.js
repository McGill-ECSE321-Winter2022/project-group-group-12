import ax from "./common";

export const createItem = (item) => new Promise((resolve, reject) => {
        ax.post('/item', null, { params: {
            name: item.name,
            description: item.description,
            imageUrl : item.imageUrl,
            remainingQuantity: item.remainingQuantity,
            price: item.price, 
            availableForOrder: item.isAvailableForOrder,
            stillAvailable: item.isStillAvailable,
            itemCategory: item.selectedCategory
        }})
        .then(res => resolve(res))
        .catch(err => reject(err.response.data));
});

export const getAllCategories = (itemCategory) => new Promise((resolve, reject) => {
    ax.get('/itemCategories')
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});