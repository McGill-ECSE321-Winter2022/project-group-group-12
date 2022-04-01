<template>
  <div>
    <h1>Items</h1>
    <ul class="item-list">
      <li v-for="(item, i) in items" :key="item.name" v-on:click="onItemSelect(i)">
        {{ purchase.date }}
      </li>
    </ul>
    <div class="details">
        <div v-if="this.selectedItem!=-1">
          <div> Name: {{ items[selectedItem].name }}</div>
          <div> Description: {{ items[selectedItem].description }}</div>
          <div> Image URL: {{ items[selectedItem].imageUrl }}</div>
          <div> Remaining Quantity: {{ items[selectedItem].remainingQuantity }}</div>
          <div> Price: {{ items[selectedItem].price }}</div>
          <div> Available For Order: {{ items[selectedItem].availableForOrder }}</div>
          <div> Available: {{ items[selectedItem].stillAvailable }} </div>
          <div> Category: {{ items[selectedItem].category.name }}$</div>
        </div>
    </div>
  </div>
</template>

<script>

</script>