<template>
  <div>
    <h1>Items</h1>
    <ul class="item-list">
      <tr v-for="(item, index) in items" :key="index">
            <th scope="row">{{ index + 1 }}</th>
            <th scope="row">
              <img :src="item.imageUrl" style="width: 4rem;" />
            </th>
            <td>{{ item.name }}</td>
            <td>{{ item.description }}</td>
            <td>{{ item.imageUrl }}</td>
            <td>{{ item.remainingQuantity }}</td>
            <td>
              {{ item.price }}$/unit
            </td>
            <td>
              <button
                v-on:click="decreaseQ(index)"
                class="btn btn-primary btn-sm"
              >
                -
              </button>
              {{ product.count }}
              <button
                v-on:click="increaseQ(index)"
                class="btn btn-primary btn-sm"
                size="sm"
              >
                +
              </button>
            </td>
          </tr>
  </div>
</template>

<script>

</script>