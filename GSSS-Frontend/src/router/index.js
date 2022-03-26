import Vue from 'vue'
import Router from 'vue-router'

import Owner_ViewPurchases from '@/components/Owner_ViewPurchases'
import SystemInformation from '@/components/SystemInformation'
import LoginPage from '@/components/LoginPage'
import CustomerList from '@/components/CustomerList/CustomerList'
import Payment from '@/components/Payment'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/ownerPurchases',
      name: 'Owner_ViewPurchases',
      component: Owner_ViewPurchases
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/customers',
      name: 'CustomerList',
      component: CustomerList
    },
    {
      path: '/systemInformation',
      name: 'SystemInformation',
      component: SystemInformation
    },
    {
      path: '/payment',
      name: 'Payment',
      component: Payment
    }
  ]
})
