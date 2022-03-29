<template>
<div class="employeeviewpurchases">
  <h5>Your assigned purchases:
  </h5>

    <table>
      <th>Order type</th>
      <th>Order status</th>
      <th>Date</th>
      <th>Time</th>

      <tr v-for="purchase in purchases" :key=purchase.id>
          <td> {{ purchase.orderType }} </td>
          <td> {{ purchase.orderStatus }} </td>
          <td> {{ purchase.date }} </td>
          <td> {{ purchase.time }} </td>
      </tr>
     </table> 
     <p>
      <span v-if="errorPurchase" style="color:red">Error: {{errorPurchase}} </span>
    </p>
  </div>

</template>

<script src="./purchase.js">
</script>

<style scoped>
    @import './EmployeeViewPurchase.css';
</style>