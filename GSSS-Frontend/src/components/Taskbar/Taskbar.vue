<!--
  -- Author: Philippe Sarouphim Hochar
  -->

<template>
    <header>
            <a href="/"><img src="../../assets/Logo_GSSS_notext.png" alt="centered image" height="60px" width="60px"></a>
            <h1><a href="/">GSSS</a></h1>
            <div>
                <!-- Links if no user is logged in -->
                <nav v-if="permission == -1">
                    <a href="/#/login">Login</a>
                    <a href="/#/signup">Signup</a>
                </nav>

                <!-- Links if a customer is logged in -->
                <nav v-if="permission == 0">
                    <a href="/#/customer/account">Account info</a>
                    <a href="/#/customer/shop">Shop</a>
                    <a href='/#/customer/storeinformation'>Store information</a>
                    <a href='/#/customer/orderhistory'>Order history</a>
                    <a v-on:click="logout()">Logout</a>
                </nav>

                <!-- Links if an employee is logged in -->
                <nav v-if="permission == 1">
                    <a href="/#/employee/account">Account info</a>
                    <a href="/#/employee/customers">Customers</a>
                    <a href='/#/employee/purchases'>Assigned purchases</a>
                    <a href='/#/employee/view/shifts'>Assigned shifts</a>
                    <a href='/#/customer/storeinformation'>Store information</a>
                    <select name="list" id="list" v-on:change="goToNewPage()">
                        <option value='/' selected>Home</option>
                        <option value="/#/employee/customers">Account info</option>
                        <option value="/#/employee/customers">Customers</option>
                        <option value="/#/employee/purchases">Assigned purchases</option>
                        <option value="/#/employee/view/shifts">Assigned shifts</option>
                        <option value="/#/customer/storeinformation">Store information</option>
                    </select>
                    <a v-on:click="logout()">Logout</a>
                </nav>

                <!-- Links if an owner is logged in -->
                <nav v-if="permission == 2">
                    <a href="/#/owner/systeminformation">System Information</a>
                    <a href="/#/owner/employees">Employees</a>
                    <a href="/#/owner/purchases">Purchases</a>
                    <a href='/#/owner/itemcategory'>Item categories</a>
                    <a href='/#/owner/viewandedititems'>Items</a>
                    <a href='/#/owner/shifts'>Shifts</a>
                    <a href='/#/employee/customers'>Customers</a>
                    <a v-on:click="logout()">Logout</a>
                </nav>
            </div>
    </header>
</template>

<script src="./taskbar.js"/>

<style scoped src="./taskbar.css"/>
