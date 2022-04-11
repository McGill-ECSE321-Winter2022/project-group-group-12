import { createAddress, modifyAddress } from "./address.js";
import ax from "./common.js";

export const getAllEmployees = () => new Promise((resolve, reject) => {
    ax.get("/employees")
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});

export const getEmployee = (email) => new Promise((resolve, reject) => {
    ax.get(`/employee/${email}`)
    .then(res => resolve(res.data))
    .catch(err => reject(err.response.data));
});

export const createEmployee = (employee) => new Promise((resolve, reject) => {
    createAddress(employee.address)
    .then(res => {
        employee.address.id = res.data.id;
        ax.post("/employee", employee)
        .then(res2 => {
            console.log(res2)
            resolve(res2)
        })
        .catch(err2 => reject(err2.response.data));
    })
    .catch(err => reject(err.response.data));
});

export const modifyEmployee = (modifiedEmpoyee) => new Promise((resolve, reject) => {
    modifyAddress(modifiedEmpoyee.address)
    .then(res => {
        ax.put("/employee", modifiedEmpoyee)
        .then(res2 => resolve(res2.data))
        .catch(err => reject(err));
    })
    .catch(err => reject(err.response.data));
});

export const modifyPassword = (email, password) => new Promise((resolve, reject) => {
    ax.post(`/employee/password/${email}`, null, { params: { password } })
    .then(res => resolve())
    .catch(err => reject(err.response.data));
});