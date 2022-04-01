<!--
  -- Author: Philippe Sarouphim Hochar
  -->

<template>
    <header>
            <a href="/#/"><h1>GSSS</h1></a>
            <div>
                <!-- Links if no user is logged in -->
                <nav v-if="permission == -1">
                    <a href="/#/login">Login</a>
                    <a href="/#/signup">Signup</a>
                </nav>

                <!-- Links if a customer is logged in -->
                <nav v-if="permission == 0">
                    <a href="/#/customer/shop">Browse shop</a>
                    <a href="/#/customer/account">Account info</a>
                    <a href>Logout</a>
                </nav>

                <!-- Links if an employee is logged in -->
                <nav v-if="permission == 1">
                    <a href="/#/employee/customers">Customers</a>
                    <a href>Logout</a>
                </nav>

                <!-- Links if an owner is logged in -->
                <nav v-if="permission == 2">
                    <a href>Logout</a>
                </nav>
            </div>
    </header>
</template>

<style scoped>
    @import "./taskbar.css";
</style>

<script src="./taskbar.js"/>