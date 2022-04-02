<template>
    <div>
        <h3>Create new shift</h3>
        <div>
            <label>Date: </label>
            <input type="text" placeholder="MM/DD/YYYY" v-model="shift.date"/>
            <br/>
            <label>Start Time: </label>
            <input type="text" placeholder="ex: 7:00" v-model="shift.startTime"/>
            <br/>
            <label>End Time: </label>
            <input type="text" placeholder="ex: 18:00" v-model="shift.endTime"/>
            <br/>
            <label> Employees </label>
            <select id="selectEmployee" placeholder="Select employee">
            <tr v-for="(employee, index) in employees" :key="index">
            <option value=key.email> key </option>
            </tr>
            </select>
            <br/>
        </div>
        <p v-if="error" style="color:red; font-style:italic;">{{ error }}</p>
        <button v-on:click="save()">Create</button>
    </div>
</template>

<script src="./shiftCreator.js" />

<style scoped src="./shiftCreator.css">
</style>