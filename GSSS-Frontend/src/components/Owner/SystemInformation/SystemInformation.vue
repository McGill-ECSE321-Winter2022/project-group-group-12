<!--
Vue class for the system information page.
@author Theo Ghanem
-->

<template>
  <div class="storeInformation-container">
    <h1 id="header">Grocery Store Information</h1>

    <div class="ham">
      <div class="menu" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
        <ul class="selectable-list">
          <li v-on:click="showScreen(1)">Store Business Hours</li>
          <li v-on:click="showScreen(2)">Store city and delivery fee</li>
        </ul>

      </div>
      <div class="vertical-separator"/>
      <div class="details" v-bind:class="menu ? 'menu-open' : 'menu-closed'">


        <div v-if="selectedScreen == 1 ">
          <br/>
          <div class="WeekdayAndBH">
            <button class="back-button" v-on:click="menu = true">Back</button>
            <br/>
            <div>
              Select weekday:
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

            <p> Opening time: <input placeholder="ex: 07:00" type="text" id="startTimeInput"/></p>
            <p> Closing time: <input placeholder="ex: 18:00" type="text" id="endTimeInput"/></p>

            <button v-on:click="saveBHForWeekday()"> Save Business Hours</button>
            <br/>
            <br/>
            <hr>


          </div> <!-- end of Weekday and BH section-->

          <div class="align-content-center">The current Business Hours are as follows:</div>

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
              <td>{{ MonStartTime }}</td>
              <td>{{ TueStartTime }}</td>
              <td>{{ WedStartTime }}</td>
              <td>{{ ThuStartTime }}</td>
              <td>{{ FriStartTime }}</td>
              <td>{{ SatStartTime }}</td>
              <td>{{ SunStartTime }}</td>
            </tr>
            <tr>
              <td><strong>Closing time</strong></td>
              <td>{{ MonCloseTime }}</td>
              <td>{{ TueCloseTime }}</td>
              <td>{{ WedCloseTime }}</td>
              <td>{{ ThuCloseTime }}</td>
              <td>{{ FriCloseTime }}</td>
              <td>{{ SatCloseTime }}</td>
              <td>{{ SunCloseTime }}</td>
            </tr>
            </tbody>
          </table>
          <div v-if="errorBH" class="error">
            <div>
              {{ errorBH }}
            </div>
          </div>

          <div v-if="successBH" class="success">
            <div>
              {{ successBH }}
            </div>
          </div>
        </div>

        <!--        if the second option is selected-->
        <div v-if="selectedScreen == 2 ">
          <br/>
          <button class="back-button" v-on:click="menu = true">Back</button>

          <div class="storeInfo-container">
            <p>Store city: <input placeholder="ex: Montreal" type="text" id="cityinput"/></p>
            <p>Current city: <span style="color:deepskyblue; font-style:italic;">{{ currentCity }}</span></p>
            <br/>
            <p>Out of city fee: <input placeholder="ex: 5" type="number" min="0" max="10" id="feeinput"/></p>
            <p>Current fee: <span style="color:deepskyblue;"> {{ currentFee }}</span>$</p>

            <button v-on:click="saveCityAndFee()"> Save store information</button>
          </div>
          <div v-if="errorStoreInfo" class="error">
            <div>
              {{ errorStoreInfo }}
            </div>
          </div>

          <div v-if="successStoreInfo" class="success">
            <div>
              {{ successStoreInfo }}
            </div>
          </div>

        </div>


      </div>
    </div>
  </div>
</template>

<script src="./systemInformation.js"/>

<style scoped src="./systemInformation.css"/>
