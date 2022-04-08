

<template>
  <div>
    <h2>Order History of {{email}}</h2>
    <div class="wrapper">
      <div class="viewpurchases">
        <ul class = "selectable-list">
          <li  v-for="(purchase, i) in purchases" :key="purchase.id" v-on:click="onPurchaseSelect(i)">
            {{ purchase.date }}
          </li>
        </ul>
      </div>

      <div class = "vertical-separator"/>
      <div class="details">
        <div v-if="this.selectedPurchase!=-1">

          <div>
            <h5> Selected Purchase information: </h5>
          </div>

          <div> Order Type: {{ purchases[selectedPurchase].orderType }}</div>
          <div> Order status: {{ purchases[selectedPurchase].orderStatus }}</div>
          <div> Date: {{ purchases[selectedPurchase].date }}</div>
          <div> Time: {{ purchases[selectedPurchase].time }}</div>
          <div> Employee: {{ purchases[selectedPurchase].employee.email }} </div>
          <div> Cost: {{ purchases[selectedPurchase].cost.toFixed(2) }}$</div>
          <div> Items:
            <ul v-for="(n, index) in this.selectedPurchaseItems.length" :key="index">
              <li> {{ selectedPurchaseItems[index] }} : {{ selectedPurchaseQuantities[index] }} ( {{ selectedPurchaseItemsPrices[index].toFixed(2) }}$ / unit )</li>
            </ul>
          </div>
        </div>
      </div>
      <div v-if="error" class="error">
        <div>
          {{ error }}
        </div>
      </div>

    </div>
  </div>
</template>

<script src="./orderHistory.js" />

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src='./orderHistory.css' />
