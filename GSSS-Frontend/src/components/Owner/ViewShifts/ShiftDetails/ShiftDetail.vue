<template>
    <div>
        <h3>Shift Details</h3>
        <div>ID: {{ shift.id }}</div>
        <div>Date: {{ shift.date }}</div>
        <div>Start Time: {{shift.startTime }}</div>
        <div>End Time: {{ shift.endTime }}</div>
        <div> Employee: {{ employee.email }}</div>
        <button v-on:click="remove()">Delete Shift</button>
    </div>
</template>   

<script src="./shiftDetail.js" />

<style scoped src="./shiftDetail.css">
</style>