<template>
    <div>
        <h3>{{ customer.email }}</h3>
        <div>Username: {{ customer.username }}</div>
        <div>Disabled: {{ customer.disabled }}</div>
    </div>
</template>

<script src='./customerDetail.js'></script>

<style scoped>
    @import './customerDetail.css';
</style>