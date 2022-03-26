<template>
    <div>
        <h3>{{ customer.email }}</h3>
        <div>Username: {{ customer.username }}</div>
        <div style="margin-bottom: 1rem">Disabled: {{ customer.disabled }}</div>
        <div>Address: ({{ customer.address.id }})</div>
        <p>{{ customer.address.fullName }}<br/>{{customer.address.streetNumber}}, {{ customer.address.streetName }}<br/>{{ customer.address.city }}, {{ customer.address.postalCode }}</p>
    </div>
</template>

<script src='./customerDetail.js'></script>

<style scoped>
    @import './customerDetail.css';
</style>