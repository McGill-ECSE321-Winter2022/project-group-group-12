<template>
    <div>
        <h1>Your info</h1>
        <employee-detail v-if="employee" v-bind:employee="employee" v-bind:onChange="onChange" />
    </div>
</template>

<style scoped src="./employeeAccount.css">
</style>

<script src="./employeeAccount.js"/>