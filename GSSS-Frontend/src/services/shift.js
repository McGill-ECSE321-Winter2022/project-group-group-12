import ax from "./common";

export const addShift = (employeeEmail, shift) => new Promise((resolve, reject) => {
    ax.post('/employee/shift/{email}', null, { params: employeeEmail, shift })
    .then(res => resolve(res))
    .catch(err => reject(err));
});

export const deleteShift = (shift) => new Promise((resolve, reject) => {
    ax.delete('/shift/delete', null, { params: shift })
    .then(res => resolve(res))
    .catch(err => reject(err));
});

export const getAllShifts = () => new Promise((resolve, reject) => {
    ax.get('/shifts')
    .then(res => resolve(res.data))
    .catch(err => reject(err));
});

export const getEmployeeByShift = () => new Promise((resolve, reject) => {
    ax.get('/employeebyshift')
    .then(res => resolve(res.data))
    .catch(err => reject(err));
});



