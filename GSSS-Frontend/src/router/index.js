import Vue from 'vue'
import Router from 'vue-router'

// Owner imports
import ViewPurchases from '@/components/Owner/ViewPurchases/ViewPurchases.vue'
import OwnerItemCategory from '@/components/Owner/ItemCategory/OwnerItemCategory.vue'
import ItemCreator from '@/components/Owner/ViewItems/ItemCreator.vue'
import ViewAndEditItems from '@/components/Owner/ViewItems/ViewAndEditItems.vue'
import ShiftCreator from '@/components/Owner/ViewShifts/ShiftCreator/ShiftCreator.vue'
import ShiftList from '@/components/Owner/ViewShifts/ShiftList/ShiftList.vue'
import EmployeeList from '@/components/Owner/EmployeeList/EmployeeList.vue'
// import ShiftCreator from '@/components/Owner/ViewShifts/ShiftCreator/ShiftCreator.vue'

// Employee imports
import EmployeeViewPurchase from '@/components/Employee/Purchase/EmployeeViewPurchase.vue'
import EmployeeViewShift from '@/components/Employee/Shift/EmployeeViewShift.vue'
import StoreInformation2 from '@/components/Employee/StoreInformation/StoreInformation.vue';
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

const NONE = 'None';
const CUSTOMER = 'Customer';
const EMPLOYEE = 'Employee';
const OWNER = 'Owner';

const otherRoutes = [
  {
    path: '/',
    name: 'Hello',
    component: Hello,
    permissions: [NONE, CUSTOMER, EMPLOYEE, OWNER]
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage,
    permissions: [NONE]
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
    component: ViewPurchases,
    permissions: [OWNER]
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
    path: '/owner/createshift',
    name: 'ShiftCreator',
    component: ShiftCreator
  },
  {
    path: '/owner/shifts',
    name: 'ShiftList',
    component: ShiftList
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
  },
  {
    path: '/employee/account',
    name: 'Employee Account',
    component: EmployeeAccount
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
    component: ViewCustomerAccount,
    permissions: [CUSTOMER]
  },
  {
    path: '/customer/storeinformation',
    name: 'Store Information',
    component: StoreInformation
  },
  {
    path: '/customer/orderhistory',
    name: 'Order History',
    component: OrderHistory
  }

];

const routes = [...otherRoutes, ...ownerRoutes, ...employeeRoutes, ...customerRoutes];

const router = new Router({
  mode: 'history',
  routes
});

router.beforeEach(async (to, from, next) => {
  let permission = localStorage.permission || NONE;
  let route = matchRoute(to.name);
  if(route){
    if(route.permissions.includes(permission)) next();
    else next('/');
  }
  else next('/');
});

function matchRoute(routeName){
  let matches = routes.filter(r => r.name == routeName);
  if(matches.length != 1) return null;
  else return matches[0];
}

export default router;
