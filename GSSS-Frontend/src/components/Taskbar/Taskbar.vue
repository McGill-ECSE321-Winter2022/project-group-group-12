<!--
  -- Author: Philippe Sarouphim Hochar
  -->

<template>
    <header>
            <a href="/"><img src="../../assets/Logo_GSSS_notext.png" alt="centered image" height="60px" width="60px"></a>
            <h1><a href="/">GSSS</a></h1>
            <div v-bind:style="taskbar? '--open:auto': '--open:none'">
                <!-- Links if no user is logged in -->
                <nav v-if="permission == -1"  v-on:click="taskbar=false;">
                    <a href="/#/login">Login</a>
                    <a href="/#/signup">Signup</a>
                </nav>

                <!-- Links if a customer is logged in -->
                <nav v-if="permission == 0"  v-on:click="taskbar=false;">
                    <a href="/#/customer/account">Account info</a>
                    <a href="/#/customer/shop">Shop</a>
                    <a href='/#/customer/storeinformation'>Store information</a>
                    <a href='/#/customer/orderhistory'>Order history</a>
                    <select name="list" id="list" v-on:change="goToNewPage()">
                        <option value='/#/' selected>Home</option>
                        <option value="/#/customer/account">Account info</option>
                        <option value="/#/customer/shop">Shop</option>
                        <option value="/#/customer/storeinformation">Store information</option>
                        <option value="/#/customer/orderhistory">Order history</option>
                    </select>
                    <a v-on:click="logout()">Logout</a>
                </nav>

                <!-- Links if an employee is logged in -->
                <nav v-if="permission == 1"  v-on:click="taskbar=false;">
                    <a href="/#/employee/account">Account info</a>
                    <a href="/#/employee/customers">Customers</a>
                    <a href='/#/employee/purchases'>Assigned purchases</a>
                    <a href='/#/employee/view/shifts'>Assigned shifts</a>
                    <a href='/#/customer/storeinformation'>Store information</a>
                    <a v-on:click="logout()">Logout</a>
                </nav>

                <!-- Links if an owner is logged in -->
                <nav v-if="permission == 2" v-on:click="taskbar=false;">
                    <a href="/#/owner/systeminformation">System Information</a>
                    <a href="/#/owner/employees">Employees</a>
                    <a href="/#/owner/purchases">Purchases</a>
                    <a href='/#/owner/itemcategory'>Item categories</a>
                    <a href='/#/owner/viewandedititems'>Items</a>
                    <a href='/#/owner/shifts'>Shifts</a>
                    <a href='/#/employee/customers'>Customers</a>
                    <select name="list" id="list" v-on:change="goToNewPage()">
                        <option value='/' selected>Home</option>
                        <option value="/#/owner/systeminformation">System information</option>
                        <option value="/#/owner/employees">Employees</option>
                        <option value="/#/owner/purchases">Purchases</option>
                        <option value="/#/owner/itemcategory">Item Categories</option>
                        <option value="/#/owner/viewandedititems">Items</option>
                        <option value="/#/owner/shifts">Shifts</option>
                        <option value="/#/owner/customers">Customers</option>
                    </select>
                    <a v-on:click="logout()">Logout</a>
                </nav>

                <button class="taskbar-button" v-on:click="toggleTaskbar()">
                    <img v-if="!taskbar" style="display:block;" src="../../assets/menu-icon.jpg" height="40px">
                    <img v-if="taskbar" style="display:block;" src="../../assets/close-icon.svg" height="30px">
                </button>
            </div>
    </header>
</template>

<script src="./taskbar.js"/>

<style scoped src="./taskbar.css"/>
