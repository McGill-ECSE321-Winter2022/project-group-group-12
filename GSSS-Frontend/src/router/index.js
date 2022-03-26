import Vue from 'vue'
import Router from 'vue-router'
import Owner_ViewPurchases from '@/components/Owner_ViewPurchases'
import SystemInformation from '@/components/SystemInformation'
import LoginPage from '@/components/LoginPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Owner_ViewPurchases',
      component: Owner_ViewPurchases
    },
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
  ]
})
