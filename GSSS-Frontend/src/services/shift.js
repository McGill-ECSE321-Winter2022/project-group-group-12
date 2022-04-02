import ax from "./common";

export const addShift = (employeeEmail, shift) => new Promise((resolve, reject) => {
    ax.post('/employee/shift/{email}', null, { params: employeeEmail, shift })
    .then(res => resolve(res))
    .catch(err => reject(err));
});

