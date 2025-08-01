<script setup lang="ts">
import { useCategoryStore } from "@/stores/CategoryStore";

const categoryStore = useCategoryStore();
</script>

<style scoped>
.categories-bar {
  background-color: var(--primary-color);
  height: 3em;
  display: flex;
  justify-content: center;
}

.category-button {
  margin: 1em;
  align-self: center;
  text-decoration: none;
  font-size: 1.2em;
}

.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  background-color: var(--neutral-color);
}

.button.selected-category-button {
  background-color: var(--primary-color);
}

.router-link-active {
  text-decoration: underline;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--neutral-color);
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
}
</style>

<template>
  <div class="categories-bar">
    <router-link
      v-for="category in categoryStore.categoryList"
      :key="category.categoryId"
      :to="'../category/' + category.name"
      class="category-button"
    >
      {{ category.name }}
    </router-link>
  </div>
</template>
