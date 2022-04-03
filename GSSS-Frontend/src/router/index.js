import Vue from 'vue'
import Router from 'vue-router'

// Owner imports
import ViewPurchases from '@/components/Owner/ViewPurchases/ViewPurchases.vue'
import OwnerItemCategory from '@/components/Owner/ItemCategory/OwnerItemCategory.vue'
import ViewAndEditItems from '@/components/Owner/ViewItems/ViewAndEditItems.vue'
import ShiftList from '@/components/Owner/ViewShifts/ShiftList/ShiftList.vue'
import EmployeeList from '@/components/Owner/EmployeeList/EmployeeList.vue'

// Employee imports
import EmployeeViewPurchase from '@/components/Employee/Purchase/EmployeeViewPurchase.vue'
import EmployeeViewShift from '@/components/Employee/Shift/EmployeeViewShift.vue'
import EmployeeAccount from '@/components/Employee/EmployeeAccount/EmployeeAccount.vue'

// System imports
import SystemInformation from '@/components/Owner/SystemInformation/SystemInformation.vue'
import LoginPage from '@/components/LoginPage/LoginPage.vue'
import SignupPage from '@/components/SignupPage/SignupPage.vue'
import Payment from '@/components/Customer/Payment/Payment.vue'

// Customer imports
import CustomerList from '@/components/Owner/CustomerList/CustomerList.vue'
import Hello from "@/components/Hello";
import ViewAndSelectItems from '@/components/Customer/ViewAndSelectItems/ViewAndSelectItems.vue'
import ViewCustomerAccount from '@/components/Customer/ViewCustomerAccount/ViewCustomerAccount.vue';
import ConfirmOrderType from '@/components/Customer/ConfirmOrderType/ConfirmOrderType.vue';
import StoreInformation from '@/components/Customer/StoreInformation/StoreInformation.vue';
import OrderHistory from '@/components/Customer/OrderHistory/OrderHistory.vue';

Vue.use(Router)

export default new Router ({
  mode: 'history',
  routes: [
  // {
  //   path: '/',
  //   name: 'Hello',
  //   component: Hello,
  //   // permissions: [EMPLOYEE, OWNER]
  // },
  {
    path: '/',
    name: 'LoginPage',
    component: LoginPage,
    // permissions: [NONE]
  },
  {
    path: '/signup',
    name: 'SignupPage',
    component: SignupPage,
    // permissions: [NONE]
  },
  {
    path: '/owner/purchases',
    name: 'ViewPurchases',
    component: ViewPurchases,
    // permissions: [OWNER]
  },
  {
    path: '/owner/systeminformation',
    name: 'SystemInformation',
    component: SystemInformation,
    // permissions: [OWNER]
  },
  {
    path: '/owner/itemcategory',
    name: 'OwnerItemCategory',
    component: OwnerItemCategory,
    // permissions: [OWNER]
  },
  {
    path: '/owner/viewandedititems',
    name: 'ViewAndEditItems',
    component: ViewAndEditItems,
    // permissions: [OWNER]
  },
  {
    path: '/owner/shifts',
    name: 'ShiftList',
    component: ShiftList,
    // permissions: [OWNER]
  },
  {
    path: '/owner/employees',
    name: 'Employees List',
    component: EmployeeList,
    // permissions: [OWNER]
  },
  {
    path: '/employee/purchases',
    name: 'EmployeeViewPurchase',
    component: EmployeeViewPurchase,
    // permissions: [EMPLOYEE]
  },
  {
    path: '/employee/customers',
    name: 'CustomerList',
    component: CustomerList,
    // permissions: [EMPLOYEE, OWNER]
  },
  {
    path: '/employee/view/shifts',
    name: 'EmployeeViewShift',
    component: EmployeeViewShift,
    // permissions: [EMPLOYEE]
  },
  {
    path: '/employee/account',
    name: 'Employee Account',
    component: EmployeeAccount,
    // permissions: [EMPLOYEE]
  },
  {
    path: '/customer/shop',
    name: 'ViewAndSelectItems',
    component: ViewAndSelectItems,
    // permissions: [CUSTOMER]
  },
  {
    path: '/customer/confirmOrderType',
    name: 'ConfirmOrderType',
    component: ConfirmOrderType,
    // permissions: [CUSTOMER]
  },
  {
    path: '/customer/payment',
    name: 'Payment',
    component: Payment,
    // permissions: [CUSTOMER]
  },
  {
    path: '/customer/account',
    name: 'Customer Account',
    component: ViewCustomerAccount,
    // permissions: [CUSTOMER]
  },
  {
    path: '/customer/storeinformation',
    name: 'Store Information',
    component: StoreInformation,
    // permissions: [NONE, CUSTOMER, EMPLOYEE, OWNER]
  },
  {
    path: '/customer/orderhistory',
    name: 'Order History',
    component: OrderHistory,
    // permissions: [CUSTOMER]
  }
]
})
