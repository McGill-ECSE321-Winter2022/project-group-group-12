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

function requireNone(from, to, next) {
  var permission = localStorage.getItem("permission")
  if(!permission) next()
  else redirectDefault(permission)
}

function requireCustomer(from, to, next) {
  var permission = localStorage.getItem("permission")
  if(permission == "Customer") next()
  else redirectDefault(permission, next)
}

function requireEmployee(from, to, next) {
  var permission = localStorage.getItem("permission")
  if(permission == "Employee") next()
  else redirectDefault(permission, next)
}

function requireOwner(from, to, next) {
  var permission = localStorage.getItem("permission")
  if(permission == "Owner") next()
  else redirectDefault(permission, next)
}

function requireEmployeeOrOwner(from, to, next) {
  var permission = localStorage.getItem("permission")
  if(permission == "Employee" || permission == "Owner") next()
  else redirectDefault(permission, next)
}

function redirectDefault(permission, next) {
  if(permission == "Employee" || permission == "Owner") next({ name: 'Hello' })
  else if (permission == "Customer") next({ name: 'ViewAndSelectItems' })
  else next({ name: 'LoginPage' })
}

export default new Router ({
  routes: [
  {
    path: '/',
    name: 'Hello',
    component: Hello,
    beforeEnter: requireEmployeeOrOwner
  },
  {
    path: '/',
    name: 'LoginPage',
    component: LoginPage,
    beforeEnter: requireNone
  },
  {
    path: '/signup',
    name: 'SignupPage',
    component: SignupPage,
    beforeEnter: requireNone
  },
  {
    path: '/owner/purchases',
    name: 'ViewPurchases',
    component: ViewPurchases,
    beforeEnter: requireOwner
  },
  {
    path: '/owner/systeminformation',
    name: 'SystemInformation',
    component: SystemInformation,
    beforeEnter: requireOwner
  },
  {
    path: '/owner/itemcategory',
    name: 'OwnerItemCategory',
    component: OwnerItemCategory,
    beforeEnter: requireOwner
  },
  {
    path: '/owner/viewandedititems',
    name: 'ViewAndEditItems',
    component: ViewAndEditItems,
    beforeEnter: requireOwner
  },
  {
    path: '/owner/shifts',
    name: 'ShiftList',
    component: ShiftList,
    beforeEnter: requireOwner
  },
  {
    path: '/owner/employees',
    name: 'Employees List',
    component: EmployeeList,
    beforeEnter: requireOwner
  },
  {
    path: '/employee/purchases',
    name: 'EmployeeViewPurchase',
    component: EmployeeViewPurchase,
    beforeEnter: requireEmployee
  },
  {
    path: '/employee/customers',
    name: 'CustomerList',
    component: CustomerList,
    beforeEnter: requireEmployeeOrOwner
  },
  {
    path: '/employee/view/shifts',
    name: 'EmployeeViewShift',
    component: EmployeeViewShift,
    beforeEnter: requireEmployee
  },
  {
    path: '/employee/account',
    name: 'Employee Account',
    component: EmployeeAccount,
    beforeEnter: requireEmployee
  },
  {
    path: '/customer/shop',
    name: 'ViewAndSelectItems',
    component: ViewAndSelectItems,
    beforeEnter: requireCustomer
  },
  {
    path: '/customer/confirmOrderType',
    name: 'ConfirmOrderType',
    component: ConfirmOrderType,
    beforeEnter: requireCustomer
  },
  {
    path: '/customer/payment',
    name: 'Payment',
    component: Payment,
    beforeEnter: requireCustomer
  },
  {
    path: '/customer/account',
    name: 'Customer Account',
    component: ViewCustomerAccount,
    beforeEnter: requireCustomer
  },
  {
    path: '/customer/storeinformation',
    name: 'Store Information',
    component: StoreInformation,
  },
  {
    path: '/customer/orderhistory',
    name: 'Order History',
    component: OrderHistory,
    beforeEnter: requireCustomer
  }
]
})
