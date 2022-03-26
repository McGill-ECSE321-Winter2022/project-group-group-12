import Vue from 'vue'
import Router from 'vue-router'
<<<<<<< HEAD
import Owner_ViewPurchases from '@/components/Owner_ViewPurchases'
=======
import Hello from '@/components/Hello'
import SystemInformation from '@/components/SystemInformation'
import LoginPage from '@/components/LoginPage'
>>>>>>> 30fcd07eb266ee8914127f462c1f4491c0cc7fc9

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Owner_ViewPurchases',
      component: Owner_ViewPurchases
    },
<<<<<<< HEAD
=======
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/systemInformation',
      name: 'SystemInformation',
      component: SystemInformation
    }
>>>>>>> 30fcd07eb266ee8914127f462c1f4491c0cc7fc9
  ]
})
