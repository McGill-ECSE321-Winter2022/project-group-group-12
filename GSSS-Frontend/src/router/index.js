import Vue from 'vue'
import Router from 'vue-router'
import ViewPurchases from '@/components/Owner/ViewPurchases/ViewPurchases.vue'
import SystemInformation from '@/components/Owner/SystemInformation/SystemInformation.vue'
import LoginPage from '@/components/LoginPage/LoginPage.vue'
import SignupPage from '@/components/SignupPage/SignupPage.vue'
import CustomerList from '@/components/Owner/CustomerList/CustomerList.vue'
import Hello from '@/components/Hello.vue'
import Payment from '@/components/Customer/Payment/Payment.vue'
import ViewAndSelectItems from '@/components/Customer/ViewAndSelectItems/ViewAndSelectItems.vue'

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
      path: '/customerselectitems',
      name: 'ViewAndSelectItems',
      component: ViewAndSelectItems
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
    }
  ]
})
