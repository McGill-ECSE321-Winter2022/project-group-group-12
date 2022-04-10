<template>
<div class>
  <h5>Your assigned shifts: </h5>

    <div class="employeeviewshifts" v-if="this.shifts.length == 0">
      <p> You don't have any assigned shifts. </p>
    </div>

    <div class="employeeviewshifts" v-if="this.shifts.length != 0">
       <table>
        <th> Shift date</th>
        <th> Shift start time</th>
        <th> Shift end time</th>
      <tr v-for="shift in this.shifts" :key=shift.date>
          <td style="text-align:center"> {{ shift.date }} </td>
          <td style="text-align:center"> {{ shift.startTime.slice(0, -3) }} </td>
          <td style="text-align:center"> {{ shift.endTime.slice(0, -3) }} </td>
      </tr>
      </table>
    </div>

    <div v-if="error" class="error">
      <div>
        {{ error }}
      </div>
    </div>
    <div v-if="success" class="success">
        <div>
          {{ success }}
        </div>
      </div>
  </div>

</template>

<script src="./employeeViewShift.js"/>

<style scoped src="./EmployeeViewShift.css" />
