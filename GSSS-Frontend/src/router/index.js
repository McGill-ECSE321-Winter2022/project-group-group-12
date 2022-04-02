import Vue from 'vue'
import Router from 'vue-router'

// Owner imports
import ViewPurchases from '@/components/Owner/ViewPurchases/ViewPurchases.vue'
import OwnerItemCategory from '@/components/Owner/ItemCategory/OwnerItemCategory.vue'
import ItemCreator from '@/components/Owner/ViewItems/ItemCreator.vue'
import ViewAndEditItems from '@/components/Owner/ViewItems/ViewAndEditItems.vue'
import EmployeeList from '@/components/Owner/EmployeeList/EmployeeList.vue'
// import ShiftCreator from '@/components/Owner/ViewShifts/ShiftCreator/ShiftCreator.vue'

// Employee imports
import EmployeeViewPurchase from '@/components/Employee/Purchase/EmployeeViewPurchase.vue'
import EmployeeViewShift from '@/components/Employee/Shift/EmployeeViewShift.vue'
import StoreInformation2 from '@/components/Employee/StoreInformation/StoreInformation.vue';

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
    path: '/owner/purchases',
    name: 'ViewPurchases',
    component: ViewPurchases
  },
  {
    path: '/owner/systeminformation',
    name: 'SystemInformation',
    component: SystemInformation
  },
  {
    path: '/owner/itemcategory',
    name: 'OwnerItemCategory',
    component: OwnerItemCategory
  },
  {
    path: '/owner/createitem',
    name: 'ItemCreator',
    component: ItemCreator
  },
  {
    path: '/owner/viewandedititems',
    name: 'ViewAndEditItems',
    component: ViewAndEditItems
  },
  {
    path: '/owner/employees',
    name: 'Employees List',
    component: EmployeeList
  }
  // {
  //   path: '/owner/createshift',
  //   name: 'ShiftCreator',
  //   component: ShiftCreator
  // }
];

const employeeRoutes = [
  {
    path: '/employee/purchases',
    name: 'EmployeeViewPurchase',
    component: EmployeeViewPurchase
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
  {
    path: '/employee/storeinformation',
    name: 'StoreInformation',
    component: StoreInformation2
  }
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
  },
  {
    path: '/customer/storeinformation',
    name: 'Store Information',
    component: StoreInformation
  }

];

export default new Router({

  routes: [...otherRoutes, ...ownerRoutes, ...employeeRoutes, ...customerRoutes]

})
