<!-- Author: Wassim Jabbour -->

<template>
    <div>
        <h3>{{ purchase.date }}</h3>
        <div>Id: {{ purchase.id }}</div>
        <div style="margin-bottom: 1rem">Customer email:{{ purchase.customer }}</div>
        <div> Order Type: {{ purchase.orderType }}</div>
        <div> Order status: {{ purchase.orderStatus }}</div>
        <div> Date: {{ purchase.date }}</div>
        <div> Time: {{ purchase.time }}</div>
        <div> Employee: {{ purchase.employee.email }} </div>
        <div> Items:
            <ul v-for="(item, quantity) in Array.from(this.purchase.items)" :key="item.name"> 
                <li> {{ item.name }} : {{ quantity }}</li>
            </ul>
        </div>
    </div>
</template>

<script >
    export default {
        name: 'purchase-details',
        props: {
            purchase: Object
        }
    }
</script>

<style scoped>
</style>