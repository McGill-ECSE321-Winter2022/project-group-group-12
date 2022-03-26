import Vue from 'vue'
import Router from 'vue-router'
import ViewPurchases from '@/components/Owner/ViewPurchases'
import SystemInformation from '@/components/Owner/SystemInformation/SystemInformation'
import LoginPage from '@/components/LoginPage/LoginPage'
import SignupPage from '@/components/SignupPage/SignupPage'
import CustomerList from '@/components/Owner/CustomerList/CustomerList.vue'
import PurchaseStatistics from "@/components/Owner/PurchaseStatistics";
import Hello from "@/components/Hello";
import Payment from '@/components/Customer/Payment'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/ownerpurchases',
      name: 'ViewPurchases',
      component: ViewPurchases
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
      path: '/systeminformation',
      name: 'SystemInformation',
      component: SystemInformation
    },
    {
      path: '/payment',
      name: 'Payment',
      component: Payment
    },
    {
      path: '/purchasestats',
      name: 'PurchaseStatistics',
      component: PurchaseStatistics
    }
  ]
})
