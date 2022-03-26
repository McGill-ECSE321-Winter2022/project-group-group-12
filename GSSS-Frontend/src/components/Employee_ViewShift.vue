<template>
<div class="employeeviewshifts">
  <h5>Your assigned shifts:   
  </h5>

    <table>
      <th> Shift date</th>
      <th> Shift start time</th>
      <th> Shift end time</th>

      <tr v-for="shift in shifts" :key=shift.date>
          <td> {{ shift.date }} </td>
          <td> {{ shift.startTime }} </td>
          <td> {{ shift.endTime }} </td>
      </tr>
     </table> 
     <p>
      <span v-if="errorItemCategory" style="color:red">Error: {{errorItemCategory}} </span>
    </p>
  </div>

</template>

<script src="./shift.js">
</script>

<style scoped>
    @import '../assets/styles/Employee_ViewShift.css';
</style>