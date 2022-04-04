<template>
    <div>
        <h1>Employees</h1>
        <div class="ham">
            <div class="menu">
                <ul class="selectable-list">
                    <li v-for="(employee, i) in employees" :key=employee.email v-on:click="selectEmployee(i)">{{ employee.email }}</li>
                </ul>
                <button v-on:click="addEmployee()">Add employee</button>
                <div v-if="error" style="color: red; font-style: italic;">{{ error }}</div>
            </div>
            <div class="vertical-separator"/>
            <div class="details">
                <employee-detail v-if="!addMode && selectedEmployee >= 0" v-bind:onChange="onChange" v-bind:employee="employees[selectedEmployee]" />
                <employee-creator v-if="addMode" v-bind:onAdd="onChange" />
            </div>
        </div>
    </div>
</template>

<script src="./employeeList.js" />

<style scoped src="./employeeList.css" />