<template>
    <div>
        <h3>Create new shift</h3>
        <div>
            <label>Date: </label>
            <input type="text" placeholder="YYYY-MM-DD" v-model="shift.date"/>
            <br/>
            <label>Start Time: </label>
            <input type="text" placeholder="ex: 7:00:00" v-model="shift.startTime"/>
            <br/>
            <label>End Time: </label>
            <input type="text" placeholder="ex: 18:00:00" v-model="shift.endTime"/>
            <br/>
            <label> Employees </label>
            <select v-model='selectedEmployee'>
                <option v-for="employee in this.employees" :value="employee.email" :key="employee.email"> 
                    {{ employee.email }}
                </option>
            </select>
            <br/>
        </div>
        <button v-on:click="save()">Create</button>
        <div v-if="error" class="error">
            <div>
                {{ error }}
            </div>
        </div>
    </div>
</template>

<script src="./shiftCreator.js" />

<style scoped src="./shiftCreator.css" />