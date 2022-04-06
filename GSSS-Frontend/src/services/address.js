import ax from "./common";

export const createAddress = (address) => new Promise((resolve, reject) => {
    ax.post('/address', null, { params: address })
    .then(res => resolve(res))
    .catch(err => reject(err.response.data));
});

export const modifyAddress = (modifiedAddress) => new Promise((resolve, reject) => {
    ax.post(`/address/${modifiedAddress.id}`, null, { params: {
        fullName: modifiedAddress.fullName,
        streetNumber: modifiedAddress.streetNumber,
        streetName: modifiedAddress.streetName,
        city: modifiedAddress.city,
        postalCode: modifiedAddress.postalCode
    }})
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});