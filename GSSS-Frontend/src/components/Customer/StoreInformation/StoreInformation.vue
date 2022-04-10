<!--
Vue class for the system information page.
@author Theo Ghanem
@athour Chris Hatoum
-->

<template>
  <div class="storeInformation-container">
    <h1 id="header">Grocery Store Information</h1>

    <div class="ham">
        <div class="menu" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
        <ul class="selectable-list">
          <li v-on:click="showScreen(1)">About</li>
          <li v-on:click="showScreen(2)">Business Hours & delivery fee</li>
          <li v-on:click="showScreen(3)">Promotions</li>
        </ul>

      </div>
      <div class="vertical-separator"/>
        <div class="details" v-bind:class="menu ? 'menu-open' : 'menu-closed'">

        <div v-if="selectedScreen == 1 "><p>Welcome to the GSSS! Founded by a group of enthusiastic students in 2022, in downtown montreal to provide an easy-to-use and reliable system for your everyday shopping needs!</p>
          <br/>
          <button class="back-button" v-on:click="menu = true">Back</button>
        </div>

        <div v-if="selectedScreen == 2 ">
          <br/>
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
          <br/>
          <br/>
          <p>If you are a citizen of <b>{{currentCity}}</b>, then the delivery is free! <br>
            However, if you do not live in this city then you must pay a delivery fee of : <b>{{currentFee}}</b> </p>

          <br/>
          <button class="back-button" v-on:click="menu = true">Back</button>
        </div>


      <div v-if="selectedScreen == 3 ">
        <p>We don't have any promotions at the time. Come back later!</p>
        <br/>
        <button class="back-button" v-on:click="menu = true">Back</button>
      </div>
    </div>
  </div>
  </div>
</template>

<script src="./storeInformation.js" />

<style scoped src="./storeInformation.css" />
