<!--
Vue class for the system information page.
@author Theo Ghanem
-->



<template>
  <div class="systemInformation-container">
    <table>
      <tr>

    <div>

      <h1>Grocery Store Information</h1>
      <br/>
      <br/>
      <td>
      <div>
          <div> Select weekday:
            <select id="selectWeekday" placeholder="Select day">
              <option value="" disabled selected>Select your day</option>
              <option value="1">All days</option>
              <option value="Monday">Monday</option>
              <option value="Tuesday">Tuesday</option>
              <option value="Wednesday">Wednesday</option>
              <option value="Thursday">Thursday</option>
              <option value="Friday">Friday</option>
              <option value="Saturday">Saturday</option>
              <option value="Sunday">Sunday</option>
            </select>
          </div>
          <br/>

          <p> Opening time:  <input placeholder="ex: 07:00"  type="text" id="startTimeInput" /></p>
          <p> Closing time:  <input placeholder="ex: 18:00" type="text" id="endTimeInput" /></p>
          <button v-on:click="saveBHForWeekday()">  Save Business Hours </button>
          <p v-if="errorBH" style="color:red; font-style:italic;">{{ errorBH }}</p>
          <p v-if="successBH" style="color:green; font-style:italic;">{{ successBH }}</p>

        </div>
        </td>
        <td>
          <br/>
        </td>

        <td>
        <div class="storeInfo-container">
          <p>Store city: <input placeholder="ex: Montreal" type="text" id="cityinput"/></p>
          <br/>
          <p>Out of city fee: <input placeholder="ex: 5$" type="number"  min="0"  max="10" id="feeinput"/> </p>
          <br/>
          <button v-on:click="saveCityAndFee()">  Save store information</button>
          <p v-if="errorStoreInfo" style="color:red; font-style:italic;">{{ errorStoreInfo }}</p>
          <p v-if="successStoreInfo" style="color:green; font-style:italic;">{{ successStoreInfo }}</p>
        </div>
        </td>
    </div>
      </tr>
    </table>
  </div>
</template>

<style scoped>
@import './systemInformation.css';
</style>


<script src="./systemInformation.js" />
