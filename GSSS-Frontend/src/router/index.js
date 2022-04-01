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
import ViewCustomerAccount from '@/components/Customer/ViewCustomerAccount/ViewCustomerAccount.vue';

Vue.use(Router)

const otherRoutes = [
  {
    path: '/',
    name: 'Hello',
    component: Hello
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
]

const ownerRoutes = [
  {
    path: '/owner/systeminformation',
    name: 'SystemInformation',
    component: SystemInformation
  },
];

const employeeRoutes = [
  {
    path: '/employee/purchases',
    name: 'ViewPurchases',
    component: ViewPurchases
  },
  {
    path: '/employee/customers',
    name: 'CustomerList',
    component: CustomerList
  },
];

const customerRoutes = [
  {
    path: '/customer/shop',
    name: 'ViewAndSelectItems',
    component: ViewAndSelectItems
  },
  {
    path: '/customer/payment',
    name: 'Payment',
    component: Payment
  },
  {
    path: '/customer/account',
    name: 'Customer Account',
    component: ViewCustomerAccount
  }
];

export default new Router({
  routes: [...otherRoutes, ...ownerRoutes, ...employeeRoutes, ...customerRoutes]
})
