import ax from "./common";

export const createAddress = (address) => new Promise((resolve, reject) => {
    ax.post('/address', null, { params: address })
    .then(res => resolve(res))
    .catch(err => reject(err));
});