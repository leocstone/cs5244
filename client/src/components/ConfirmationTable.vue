<script setup lang="ts">
import { bookImageFileName } from "@/utils";
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
import { asDollarsAndCents } from "@/utils";

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { useBookStore } from "@/stores/BookStore";
import { BookItem, OrderDetails } from "@/types";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails: OrderDetails = orderDetailsStore.orderDetails;

const bookStore = useBookStore();

// A helper function - optional to use
const bookAt = function (orderDetails: OrderDetails, index: number): BookItem {
  return orderDetails.books[index];
};
</script>

<style scoped>
table {
  border: 1px black solid;
  width: fit-content;
  margin-top: 0em;
  margin-bottom: 1em;
}

td {
  display: table-cell;
  padding: 0.6em 0.6em;
  background-color: white;
  vertical-align: middle;
}

tr:nth-child(even) > td {
  background-color: #eeeeee;
}

td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}
</style>

<template>
  <table>
    <tr>
      <td>Item</td>
      <td>Qty.</td>
      <td>Subtotal</td>
    </tr>
    <tr v-for="(item, index) in orderDetails.lineItems" :key="item.bookId">
      <td>
        {{ orderDetails.books[index].title }}
      </td>
      <td>{{ item.quantity }}</td>
      <td>
        {{ asDollarsAndCents(item.quantity * orderDetails.books[index].price) }}
      </td>
    </tr>
    <tr>
      <td>Surcharge</td>
      <td></td>
      <td>{{ asDollarsAndCents(500) }}</td>
    </tr>
    <tr>
      <td>Total</td>
      <td></td>
      <td>{{ asDollarsAndCents(orderDetails.order.amount) }}</td>
    </tr>
  </table>
</template>
