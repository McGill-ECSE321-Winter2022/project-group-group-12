<template>
  <div>
    <h1>Get Purchase Statistics by date</h1>
    <br>
    <p> Choose a date:</p>
    <date-picker v-model="time1" valueType="format"></date-picker>
  </div>
</template>


<script>
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';

export default {
  components: { DatePicker },
  data() {
    return {
      time1: null

    };
  },
};
</script>

