<template>
    <div>
        <h1>Shifts</h1>
        <div class="ham">
            <div class="menu" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
                <ul class="selectable-list">
                    <li v-for="(shift, i) in shifts" :key=shift.date v-on:click="selectShift(i)">{{ shift.date }}</li>
                </ul>
                <button v-on:click="addShift()">Add shift</button>
            </div>
            <div class="vertical-separator"/>
            <div class="details" v-bind:class="menu ? 'menu-open' : 'menu-closed'">
                <button class="back-button" v-on:click="menu = true">Back</button>
                <shift-detail v-if="!addMode && selectedShift >= 0" v-bind:onChange="onChange" v-bind:shift="shifts[selectedShift]"></shift-detail>
                <shift-creator v-if="addMode" v-bind:onAdd="onChange" />
            </div>
        </div>
    </div>
</template>

<script src='./shiftList.js' />

<style scoped src='./shiftList.css' />