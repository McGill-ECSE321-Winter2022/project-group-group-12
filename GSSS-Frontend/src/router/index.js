import Vue from 'vue'
import Router from 'vue-router'

import Owner_ViewPurchases from '@/components/Owner_ViewPurchases'
import SystemInformation from '@/components/SystemInformation'
import LoginPage from '@/components/LoginPage'
import CustomerList from '@/components/CustomerList/CustomerList'
import Payment from '@/components/Payment'
import SignupPage from "@/components/SignupPage";
import PurchaseStatistics from "@/components/PurchaseStatistics";
import Hello from "@/components/Hello";


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
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
      path: '/signup',
      name: 'SignupPage',
      component: SignupPage
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
    },
    {
      path: '/purchaseStats',
      name: 'PurchaseStatistics',
      component: PurchaseStatistics
    }
  ]
})
