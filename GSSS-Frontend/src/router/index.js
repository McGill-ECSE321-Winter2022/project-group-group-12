import Vue from 'vue'
import Router from 'vue-router'

// Owner imports
import ViewPurchases from '@/components/Owner/ViewPurchases/ViewPurchases'
import OwnerItemCategory from '@/components/Owner/ItemCategory/OwnerItemCategory'

// Employee imports
import EmployeeViewPurchase from '@/components/Employee/Purchase/EmployeeViewPurchase'
import EmployeeViewShift from '@/components/Employee/Shift/EmployeeViewShift'

// System imports
import SystemInformation from '@/components/Owner/SystemInformation/SystemInformation'
import LoginPage from '@/components/LoginPage/LoginPage'
import SignupPage from '@/components/SignupPage/SignupPage'
import Payment from '@/components/Customer/Payment/Payment'

// Customer imports
import CustomerList from '@/components/Owner/CustomerList/CustomerList.vue'
import PurchaseStatistics from "@/components/Owner/PurchaseStatistics";
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
    },
    {
      path: '/employeepurchases',
      name: 'EmployeeViewPurchase',
      component: EmployeeViewPurchase
    },
    {
      path: '/employee/view/shifts',
      name: 'EmployeeViewShift',
      component: EmployeeViewShift
    },
    {
      path: '/owneritemcategory',
      name: 'OwnerItemCategory',
      component: OwnerItemCategory
    }
  ]
})
