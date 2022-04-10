import ax from "./common.js";

export const getAllCategories = () => new Promise((resolve, reject) => {
    ax.get('/itemCategories/')
    .then(res => resolve(res.data))
    .catch(err => err.response.data);
});

export const createCategory = (category) => new Promise((resolve, reject) => {
    ax.post('/itemCategory', null, { params: { name: category } })
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});

export const modifyCategory = (oldCategory, newCategory) => new Promise((resolve, reject) => {
    ax.post('/itemCategory/modify', null, { params: { oldName: oldCategory, newName: newCategory } })
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});

export const deleteCategory = (category) => new Promise((resolve, reject) => {
    ax.delete(`/itemCategory/${category}`)
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});