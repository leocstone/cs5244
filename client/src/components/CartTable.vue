<script setup lang="ts">
import { useCartStore } from "@/stores/CartStore";
import { asDollarsAndCents } from "@/main";

const cartStore = useCartStore();
import { BookItem } from "@/types";
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.jpg`;
};

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: aliceblue;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading > * {
  background-color: #333;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 3;
}

.heading-price {
  grid-column: 3 / 5;
  text-align: right;
}

.heading-subtotal {
  text-align: right;
  grid-column: -2 / -1;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.cart-book-price {
  padding-left: 1em;
  text-align: right;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

/* Increment/decrement buttons */

/* TODO Consider using icon buttons for your increment and decrement buttons. */
/* TODO Modify the CSS here to suit your own design. */
/* TODO In particular, you may or may not have custom properties */
/* TODO primary-color and primary-color-dark defined in your global CSS file. */
.icon-button {
  border: none;
  cursor: pointer;
}

.inc-button {
  font-size: 1.5rem;
  color: var(--primary-color);
  border: none;
  background: none;
  padding: 0 0.25em 0.25em;
  margin: 0 0.25em 0.25em;
  cursor: pointer;
}

.inc-button:hover {
  color: var(--primary-color-dark);
}

.dec-button {
  font-size: 1.5rem;
  color: #ccc;
  border: none;
  background: none;
  padding: 0 0.25em 0.25em;
  margin: 0 0.25em 0.25em;
  cursor: pointer;
}

.dec-button:hover {
  color: #aaa;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}

.cart-book-total {
  margin-left: auto;
  padding-left: 1em;
}

.empty-cart-li {
  color: black;
  padding: 1em;
  margin-left: auto;
  margin-right: auto;
  grid-column: 1 / 3;
}
</style>

<template>
  <div class="cart-table">
    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-price">Price / Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>
      <li v-if="cartStore.count == 0" class="empty-cart-li">
        Your cart is empty!
      </li>
      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              :src="
                require('@/assets/images/books/' + bookImageFileName(item.book))
              "
              :alt="item.book.title"
              width="100px"
              height="auto"
            />
          </div>
          <div class="cart-book-title">{{ item.book.title }}</div>
          <div class="cart-book-price">
            {{ asDollarsAndCents(item.book.price) }}
          </div>
          <div class="cart-book-quantity">
            <button
              class="dec-button"
              @click="updateCart(item.book, item.quantity - 1)"
            >
              -
            </button>
            <span class="quantity">{{ item.quantity }}</span>
            <button
              class="inc-button"
              @click="updateCart(item.book, item.quantity + 1)"
            >
              +
            </button>
          </div>
          <div class="cart-book-subtotal">
            {{ asDollarsAndCents(item.book.price * item.quantity) }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>
    </ul>
  </div>
</template>
