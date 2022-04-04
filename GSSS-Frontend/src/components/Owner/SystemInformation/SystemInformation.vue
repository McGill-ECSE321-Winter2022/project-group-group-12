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
          <div> 
            <p>Select weekday: </p>
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
<!--          <p v-if="errorBH" style="color:red; font-style:italic;">{{ errorBH }}</p>-->
        <div v-if="errorBH" class="error">
          <div>
            {{ errorBH }}
          </div>
        </div>
<!--          <p v-if="successBH" style="color:green; font-style:italic;">{{ successBH }}</p>-->
<!--        success message-->
        <div v-if="successBH" class="success">
          <div>
            {{ successBH }}
          </div>
        </div>

        </div>
        </td>
        <td>
          <br/>
        </td>

        <td>
        <div class="storeInfo-container">
          <p>Store city: <input placeholder="ex: Montreal" type="text" id="cityinput"/></p>
          <p>Current city: <span style="color:deepskyblue; font-style:italic;">{{currentCity}}</span></p>
          <br/>
          <p>Out of city fee: <input placeholder="ex: 5$" type="number"  min="0"  max="10" id="feeinput"/> </p>
          <p>Current fee:  <span style="color:deepskyblue;"> {{currentFee}}</span></p>

          <button v-on:click="saveCityAndFee()">  Save store information</button>
<!--          <p v-if="errorStoreInfo" style="color:red; font-style:italic;">{{ errorStoreInfo }}</p>-->
<!--          error message-->
          <div v-if="errorStoreInfo" class="error">
            <div>
              {{ errorStoreInfo }}
            </div>
          </div>
<!--          <p v-if="successStoreInfo" style="color:green; font-style:italic;">{{ successStoreInfo }}</p>-->
<!--          success message-->
          <div v-if="successStoreInfo" class="success">
            <div>
              {{ successStoreInfo }}
            </div>
          </div>

        </div>
        </td>
    </div>
        <br/>
        Current Business Hours
        <br/>
        <table class="currentBH">
          <thead>
          <tr>
            <th></th>
            <th>Mon</th>
            <th>Tue</th>
            <th>Wed</th>
            <th>Thu</th>
            <th>Fri</th>
            <th>Sat</th>
            <th>Sun</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td><strong>Opening time</strong></td>
            <td>{{MonStartTime}}</td>
            <td>{{TueStartTime}}</td>
            <td>{{WedStartTime}}</td>
            <td>{{ThuStartTime}}</td>
            <td>{{FriStartTime}}</td>
            <td>{{SatStartTime}}</td>
            <td>{{SunStartTime}}</td>
          </tr>
          <tr>
            <td><strong>Closing time</strong></td>
            <td>{{MonCloseTime}}</td>
            <td>{{TueCloseTime}}</td>
            <td>{{WedCloseTime}}</td>
            <td>{{ThuCloseTime}}</td>
            <td>{{FriCloseTime}}</td>
            <td>{{SatCloseTime}}</td>
            <td>{{SunCloseTime}}</td>
          </tr>
          </tbody>
        </table>
      </tr>
    </table>

  </div>

</template>

<script src="./systemInformation.js" />

<style scoped src="./systemInformation.css" />
