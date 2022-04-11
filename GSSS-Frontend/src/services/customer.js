import ax from "./common";
import { createAddress, modifyAddress } from "./address";

export const getAllCustomers = () => new Promise((resolve, reject) => {
    ax.get('/customers')
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});

export const getCustomer = (email) => new Promise((resolve, reject) => {
    ax.get(`/customer/${email}`)
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
})

export const createCustomer = (customer) => new Promise((resolve, reject) => {
    createAddress(customer.address)
    .then(res => {
        ax.post('/customer', null, { params: {
            email: customer.email,
            username: customer.username,
            password: customer.password,
            address: res.data.id
        }})
        .then(res2 => resolve(res2.data))
        .catch(err2 => reject(err2.response.data));
    })
    .catch(err => reject(err));
});

export const modifyCustomer = (modifiedCustomer) => new Promise((resolve, reject) => {
    modifyAddress(modifiedCustomer.address)
    .then(res => {
        ax.post(`/customer/${modifiedCustomer.email}`, null, { params: {
            username: modifiedCustomer.username,
            address: modifiedCustomer.address.id,
            disabled: modifiedCustomer.disabled
        }})
        .then(res2 => resolve(res2.data))
        .catch(err2 => reject(err2.response.data));
    })
    .catch(err => reject(err));
});

export const modifyPassword = (email, password) => new Promise((resolve, reject) => {
    ax.post(`/customer/password/${email}`, null, { params: { password } })
    .then(res => resolve(res))
    .catch(err => reject(err.response.data));
});