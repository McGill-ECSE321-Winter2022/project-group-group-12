import ax from "./common";

export const addShift = (email, shift) => new Promise((resolve, reject) => {
    ax.post(`/employee/shift/${email}`, shift)
    .then(res => resolve(res))
    .catch(err => reject(err.response.data));
});

export const deleteShift = (shift) => new Promise((resolve, reject) => {
    ax.delete('/shift/delete', {
    params:{
            shiftId: shift
        }
    })
    .then(res => resolve(res))
    .catch(err => reject(err.response.data));
});

export const removeShift = (shift, employee) => new Promise((resolve, reject) => {
    ax.delete('/employee/shift/' + employee + '/' + shift)
    .then(res => {
        deleteShift(shift)
        .then(res2 => resolve())
        .catch(err2 => reject(err2.response.data))
    })
    .catch(err => reject(err.response.data));
});

export const getAllShifts = () => new Promise((resolve, reject) => {
    ax.get('/shifts')
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});

export const getEmployeeByShift = (shiftId) => new Promise((resolve, reject) => {
    ax.get(`/employeebyshift/${shiftId}`)
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});



