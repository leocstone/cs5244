<script setup lang="ts">
import { defineProps } from "vue";
import { BookItem } from "@/types";
import { useCartStore } from "@/stores/CartStore";
import { bookImageFileName } from "@/utils";

const props = defineProps<{
  book: BookItem;
}>();
const cartStore = useCartStore();
</script>
<style scoped>
.book-box {
  display: flex;
  flex-direction: column;
  background-color: var(--card-background-color);
  gap: 0.25em;
}

.book-description {
  color: black;
}

.book-title {
  font-weight: bold;
  text-align: center;
  color: black;
}

.book-author {
  font-style: italic;
  text-align: center;
}

.book-price {
  text-align: center;
}

.book-read-now {
  color: white;
  background: rgba(0, 0, 0, 0.5);
  text-align: center;
  transform: translateY(-2.25em);
  border: none;
  padding: 0.5em 0.5em;
}
</style>

<template>
  <li class="book-box">
    <div class="book-image">
      <img
        :src="require('@/assets/images/books/' + bookImageFileName(book))"
        :alt="book.title"
        :style="'margin-left: auto;margin-right: auto;padding: 1em;'"
        :height="'200'"
      />
      <button class="book-read-now">Read now</button>
    </div>
    <div class="book-title">{{ book.title }}</div>
    <div class="book-author">{{ book.author }}</div>
    <div class="book-price">${{ (book.price / 100).toFixed(2) }}</div>
    <button class="button" @click="cartStore.addToCart(book)">
      Add to Cart
    </button>
  </li>
</template>
