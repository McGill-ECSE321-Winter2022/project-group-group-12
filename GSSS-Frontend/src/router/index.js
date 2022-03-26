import Vue from 'vue'
import Router from 'vue-router'
import Owner_ViewPurchases from '@/components/Owner_ViewPurchases'
import SystemInformation from '@/components/SystemInformation'
import LoginPage from '@/components/LoginPage'
import Employee_ViewShift from '@/components/Employee_ViewShift'
import Owner_AddItemCategory from '@/components/Owner_AddItemCategory'
import Employee_ViewPurchase from '@/components/Employee_ViewPurchase'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/owner/viewpurchases',
      name: 'Owner_ViewPurchases',
      component: Owner_ViewPurchases
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/systemInformation',
      name: 'SystemInformation',
      component: SystemInformation
    },
    {
      path: '/employee/view/shifts',
      name: 'Employee_ViewShift',
      component: Employee_ViewShift
    },
    {
      path: '/addmodify/category',
      name: 'Owner_AddItemCategory',
      component: Owner_AddItemCategory
    },
    {
      path: '/employee/view/purchases',
      name: 'Employee_ViewPurchase',
      component: Employee_ViewPurchase
    }
  ]
})
