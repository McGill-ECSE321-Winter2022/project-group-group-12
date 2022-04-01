
//Javascript Method to work with axios for the System information page.
//Accesses the backend
//author Theo Ghanem


import ax from "./common";

export const updateCityAndFee = (city, fee) => new Promise((resolve, reject) => {
  ax.post('/info', null, { params: {city,fee} })
    .then(res => resolve(res))
    .catch(err => reject(err));
});


export const updateBusinessHours = (weekday, starttime, endtime) => new Promise((resolve, reject) => {
  ax.post('/businesshour', null, { params: {weekday,starttime, endtime} })
    .then(res => resolve(res))
    .catch(err => reject(err));
});


export const getBusinessHours = (selectedWeekday) => new Promise((resolve, reject) => {
  ax.get(`/businesshour/${selectedWeekday}`)
    .then(res => resolve(
  {
        startTimeFromBackend: res.data.startTime,
        endTimeFromBackend: res.data.endTime
        }
      ))
    .catch(err => reject(err))
});


export const getCityAndFee = () => new Promise((resolve, reject) => {
  ax.get('/owner')
    .then(res => resolve(
      {
            currentCityFromBackend: res.data.storeCity,
            // console.log(res.data)
            outOfCityFeeFromBackend: res.data.outOfTownDeliveryFee
            }
    ))
    .catch(err => reject(err))

});




