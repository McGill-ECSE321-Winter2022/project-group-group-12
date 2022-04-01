<template>
  <div id="app">
    <taskbar />
    <router-view></router-view>
  </div>
</template>

<script>
import Taskbar from "@/components/Taskbar/Taskbar.vue";
export default {
  name: 'app',
  components:{
    Taskbar
  }
}
</script>

<style>
  @import './assets/styles/global.css';
</style>
