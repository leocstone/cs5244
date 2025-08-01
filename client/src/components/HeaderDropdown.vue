<script setup lang="ts">
import { useCategoryStore } from "@/stores/CategoryStore";

const categoryStore = useCategoryStore();
</script>

<style scoped>
.header-dropdown {
  display: flex;
  flex-direction: row;
  position: relative;
}

.categories-button {
  background-color: var(--primary-color-dark);
  margin: 0.5em;
  max-height: 2.5em;
}

.header-dropdown ul {
  background-color: white;
  color: black;
  display: none;
  top: 3em;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
}

.header-dropdown a {
  color: black;
  text-decoration: none;
}

.header-dropdown li:hover {
  background: lightgray;
}

.header-dropdown:hover ul {
  display: block;
  position: absolute;
  min-width: 8em;
  z-index: 1;
  cursor: pointer;
}
</style>

<template>
  <div class="header-dropdown">
    <button class="button categories-button">Categories</button>
    <ul>
      <li
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
      >
        <router-link :to="'../category/' + category.name">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>
