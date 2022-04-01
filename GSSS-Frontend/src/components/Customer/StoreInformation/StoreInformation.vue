<!--
Vue class for the system information page.
@author Theo Ghanem
-->

<template>
  <div class="storeInformation-container">
    <h1 id="header">Grocery Store Information</h1>

    <div class="ham">
      <div class="menu">
        <ul class="selectable-list">
          <li v-on:click="showScreen(1)">About</li>
          <li v-on:click="showScreen(2)">Business Hours</li>
          <li v-on:click="showScreen(3)">Promotions</li>
        </ul>

      </div>
      <div class="vertical-separator"/>
      <div class="details">
        <div v-if="selectedScreen == 1 ">Weclome to the GSSS! Founded by a group of enthusiastic students in 2022, in downtown montreal to provide an easy-to-use and reliable system for your everyday shopping needs!</div>
        <div v-if="selectedScreen == 2 ">
          <br/>
          <div class="align-content-center">Current Business Hours</div>

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

        </div>


      <div v-if="selectedScreen == 3 ">We don't have any promotions at the time. Come back later!</div>
    </div>
  </div>
  </div>
</template>


<style scoped>
@import './storeInformation.css';
</style>

<script src="./storeInformation.js" />
