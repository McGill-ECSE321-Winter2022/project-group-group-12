import ax from "./common";
import { createAddress } from "./address";

export const createCustomer = (customer) => new Promise((resolve, reject) => {
    createAddress(customer.address)
    .then(res => {
        ax.post('/customer', null, { params: {
            email: customer.email,
            username: customer.username,
            password: customer.password,
            address: res.data.id
        }})
        .then(res => resolve(res))
        .catch(err > reject(err));
    })
    .catch(err => reject(err));
});

export const getAllCustomers = () => new Promise((resolve, reject) => {
    ax.get('/customers')
    .then(res => resolve(res.data))
    .catch(err => reject(err))
})