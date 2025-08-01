<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/CartStore";
import { useCategoryStore } from "@/stores/CategoryStore";
import { asDollarsAndCents } from "@/main";

const categoryStore = useCategoryStore();
const cartStore = useCartStore();
const clearCart = function () {
  cartStore.cart.clear();
};
const itemText = function () {
  if (cartStore.count == 1) {
    return "item";
  } else {
    return "items";
  }
};
</script>
<style scoped>
h1 {
  margin: 1em auto;
  text-align: center;
}
h2 {
  margin: 1em auto;
  text-align: center;
}
.cart-actions {
  width: fit-content;
  align-content: center;
  margin-left: auto;
  margin-right: auto;
  display: flex;
}
.checkout-button {
  color: var(--primary-color);
  text-align: center;
  margin: 1em auto 1em 1em;
}

.secondary-action {
  color: #aaaaaa;
  margin: 1em auto;
  text-align: center;
  border: none;
}

.tertiary-action {
  color: #aaaaaa;
  margin: 1em auto;
  text-align: center;
  font-size: 0.8em;
  border: none;
  background: none;
}

.cart-total {
  padding: 0.5em 1em;
  margin: 1em auto 1em 3em;
}
</style>

<template>
  <h1>Cart Page</h1>
  <cart-table v-if="cartStore.count > 0"></cart-table>
  <h2 v-else>Your cart is empty!</h2>
  <div class="cart-actions">
    <router-link
      class="secondary-action button"
      :to="'../category/' + categoryStore.selectedCategoryName"
      v-if="cartStore.count != 0"
      >Continue Shopping</router-link
    >
    <router-link
      class="checkout-button button"
      :to="'../category/' + categoryStore.selectedCategoryName"
      v-else
      >Continue Shopping</router-link
    >
    <p class="cart-total" v-if="cartStore.count != 0">
      {{
        "Total: " +
        asDollarsAndCents(cartStore.total) +
        " (" +
        cartStore.count +
        " " +
        itemText() +
        ")"
      }}
    </p>
    <router-link
      v-if="cartStore.count != 0"
      class="checkout-button button"
      to="/checkout"
      >Checkout</router-link
    >
  </div>
  <div class="cart-actions">
    <button
      @click="cartStore.clearCart()"
      class="tertiary-action button"
      v-if="cartStore.count != 0"
    >
      Clear Cart
    </button>
  </div>
</template>
