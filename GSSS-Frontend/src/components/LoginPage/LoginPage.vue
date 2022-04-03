<template>
    <div class="container">
        <div class="login-container">
            <div>
                <h1>Login</h1>
                <div>Email:</div>
                <input v-model="email" placeholder="Enter email" type="text" />
                <div>Password:</div>
                <input v-model="password" placeholder="Enter password" type="password" />
                <button @click="logIn()">Log In</button>
            </div>
        </div>
        <div v-if="error" class="error">
            <div>
                {{ error }}
            </div>
        </div>
    </div>
</template>

<style scoped>
    
input{
  margin-bottom: 1rem;
  display: block;
}

.login-container{
  display: flex;
  justify-content: center;
}

.login-container > div,
.login-container > div > *{
  width: fit-content;
} 

.error {
    position: fixed;
    bottom: 00;
    left: 0;
    right: 0;
    color: red;
    margin-bottom: 2rem;
    display: flex;
    justify-content: center;
  }

  .error > div {
    background-color: rgba(0, 0, 0, 0.822);
    padding: 0.5em 1em;
    border-radius: 0.5rem;
  }

  .success {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    color: green;
    margin-bottom: 2rem;
    display: flex;
    justify-content: center;
  }

  .success > div {
    background-color: rgba(0, 0, 0, 0.822);
    padding: 0.5em 1em;
    border-radius: 0.5rem;
  }
  
</style>

<script src="./loginPage.js" />
