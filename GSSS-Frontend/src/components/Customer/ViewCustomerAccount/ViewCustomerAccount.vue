<template>
  <div>
    <h1>Your info</h1>
    <customer-detail v-if="customer" v-bind:customer="customer" v-bind:onChange="onChange"/>
  </div>
</template>


<script src="./viewCustomerAccount.js"/>

<style scoped src="./viewCustomerAccount.css"/>
