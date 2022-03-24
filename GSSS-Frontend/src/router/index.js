import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Hello2 from '@/components/Hello2'
import LoginPage from '@/components/LoginPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/image',
      name: 'Hello2',
      component: Hello2
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    }
  ]
})
