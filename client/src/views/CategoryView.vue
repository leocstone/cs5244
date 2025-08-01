<script setup lang="ts">
import { watch } from "vue";
import CategoryNav from "@/components/CategoryNav.vue";
import CategoryBookList from "@/components/CategoryBookList.vue";
import { useRoute } from "vue-router";
import { useBookStore } from "@/stores/BookStore";
import { useCategoryStore } from "@/stores/CategoryStore";

const route = useRoute();
const bookStore = useBookStore();
const categoryStore = useCategoryStore();

watch(
  () => route.params.name,
  (newName) => {
    bookStore.fetchBooks(newName as string);
    categoryStore.selectedCategoryName = newName as string;
  },
  { immediate: true }
);
</script>

<style scoped>
/* welcome message */
#welcome-text-container {
  height: 8em;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  margin: 1em;
}

.welcome-text {
  align-self: center;
}

#welcome-divider {
  width: 25em;
  align-self: center;
  margin: 1em;
}
</style>

<template>
  <div style="'display: flex;flex-direction: column;'">
    <category-nav></category-nav>
    <category-book-list></category-book-list>
  </div>
</template>
