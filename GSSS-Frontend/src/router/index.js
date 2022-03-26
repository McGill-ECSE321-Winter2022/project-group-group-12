import Vue from 'vue'
import Router from 'vue-router'
import ViewPurchases from '@/components/Owner/ViewPurchases'
import SystemInformation from '@/components/Owner/SystemInformation/SystemInformation'
import LoginPage from '@/components/LoginPage/LoginPage'
import SignupPage from '@/components/SignupPage/SignupPage'
import CustomerList from '@/components/Owner/CustomerList/CustomerList.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/ownerPurchases',
      name: 'ViewPurchases',
      component: ViewPurchases
    },
    {
      path: '/',
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
    }
  ]
})
