import ax from "./common";

export const login = (email, password) => new Promise((resolve, reject) => {
    ax.post('/account/login', null, { params: {email,password} })
      .then(res => resolve(res))
      .catch(err => reject(err.response.data));
  });

export const logOut = () => {
  localStorage.removeItem('email');
  localStorage.removeItem('permission');
}