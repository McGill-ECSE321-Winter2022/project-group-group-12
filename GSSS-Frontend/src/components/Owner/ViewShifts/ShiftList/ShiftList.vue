<template>
    <div>
        <h1>Shifts</h1>
        <div class="ham">
            <div class="menu">
                <ul class="selectable-list">
                    <li v-for="(shift, i) in shifts" :key=shift.startTime v-on:click="selectCustomer(i)">{{ shift.startTime }}</li>
                </ul>
                <button v-on:click="addShift()">Add shift</button>
            </div>
            <div class="vertical-separator"/>
            <div class="details">
                <shift-detail v-if="!addMode && selectedShift >= 0" v-bind:onChange="onChange" v-bind:shift="shifts[selectedShift]"></shift-detail>
                <shift-creator v-if="addMode" v-bind:onAdd="onChange" />
            </div>
        </div>
    </div>
</template>

<script src='./shiftList.js'></script>

<style scoped src='./shiftList.css'>
</style>