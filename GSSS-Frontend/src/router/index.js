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
import ViewAndSelectItems from '@/components/Customer/ViewAndSelectItems/ViewAndSelectItems.vue'
import ViewCustomerAccount from '@/components/Customer/ViewCustomerAccount/ViewCustomerAccount.vue';
import ConfirmOrderType from '@/components/Customer/ConfirmOrderType/ConfirmOrderType.vue';

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
  {
    path: '/owner/itemcategory',
    name: 'OwnerItemCategory',
    component: OwnerItemCategory
  }
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
  {
    path: '/employee/view/shifts',
    name: 'EmployeeViewShift',
    component: EmployeeViewShift
  },
];

const customerRoutes = [
  {
    path: '/customer/shop',
    name: 'ViewAndSelectItems',
    component: ViewAndSelectItems
  },
  {
    path: '/customer/confirmOrderType',
    name: 'ConfirmOrderType',
    component: ConfirmOrderType
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
