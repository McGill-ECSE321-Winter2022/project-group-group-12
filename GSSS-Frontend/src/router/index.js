import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginPage from '@/components/LoginPage'
import CustomerList from '@/components/CustomerList/CustomerList'

Vue.use(Router)

export default new Router({
  routes: [
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
      path: '/customers',
      name: 'CustomerList',
      component: CustomerList
    }
  ]
})
