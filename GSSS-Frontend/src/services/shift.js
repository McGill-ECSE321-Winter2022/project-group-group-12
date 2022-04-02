import ax from "./common";

export const addShift = (email, shift) => new Promise((resolve, reject) => {
    ax.post(`/employee/shift/${email}`, null, { params: shift })
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

export const getEmployeeByShift = (shiftId) => new Promise((resolve, reject) => {
    ax.get(`/employeebyshift/${shiftId}`)
    .then(res => resolve(res.data))
    .catch(err => reject(err));
});



