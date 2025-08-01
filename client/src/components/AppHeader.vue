<script setup lang="ts">
import HeaderDropdown from "@/components/HeaderDropdown.vue";
import { useCartStore } from "@/stores/CartStore";

const cartStore = useCartStore();
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
}

.header-dropdown-and-cart {
  display: flex;
  flex-direction: row;
}

.search-bar {
  padding: 0.5em;
}

.title-and-search-bar {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.bookstore-logo {
  display: flex;
  flex-direction: row;
}

.bookstore-logo-text-div {
  display: flex;
  flex-wrap: wrap;
  padding: 1em;
  gap: 1em;
}

.bookstore-logo-text {
  font-family: var(--title-font-family);
  padding: 0.5em;
  text-align: center;
  color: black;
  text-decoration: none;
  font-size: 1.5em;
}

.header-button {
  margin-top: 0.25em;
  margin: 0.5em;
  max-height: 2.5em;
  color: black;
}

#bookstore-logo-image {
  min-width: 150px;
}

.cart-icon {
  width: 2em;
  min-width: 2em;
  height: 2em;
  padding-right: 0.1em;
}

.cart-button {
  border: none;
  background: none;
}

#cart-button-container {
  position: relative;
}

.cart-count-bubble {
  display: block;
  min-width: 1.5em;
  background-color: red;
  border-radius: 50%;
  padding: 0.3em;
  text-align: center;
}

#cart-count {
  position: absolute;
  color: white;
  top: -1em;
  margin: 0.3em;
  font-size: 0.9em;
}
</style>

<template>
  <header class="container">
    <section class="bookstore-logo">
      <router-link to="/">
        <img
          src="@/assets/images/site/logo.svg"
          alt="Open book Logo"
          width="150"
          id="bookstore-logo-image"
        />
      </router-link>
      <div class="bookstore-logo-text-div">
        <router-link to="/" class="bookstore-logo-text"
          >Leo's Book Bonanza</router-link
        >
      </div>
    </section>
    <section class="title-and-search-bar">
      <div>
        <form
          action="category.html"
          style="'display: flex;align-content: center;'"
        >
          <input type="text" class="search-bar" />
          <input type="submit" class="button search-button" value="Search" />
        </form>
      </div>
    </section>
    <section class="header-dropdown-and-cart">
      <router-link to="/cart" class="button header-button cart-button">
        <div id="cart-button-container">
          <img class="cart-icon" src="@/assets/images/site/cart.svg" />
          <div v-if="cartStore.count != 0" id="cart-count">
            <span class="cart-count-bubble">{{ cartStore.count }}</span>
          </div>
        </div>
      </router-link>
      <header-dropdown></header-dropdown>
      <button class="button header-button">Login</button>
    </section>
  </header>
</template>
