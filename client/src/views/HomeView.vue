<script setup lang="ts">
import HomeCategoryList from "@/components/HomeCategoryList.vue";
</script>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
}

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

.category-images {
  align-self: center;
}

#call-to-action {
  font-size: 2em;
  align-self: center;
  border: 1px solid black;
  display: inline-block;
  background: var(--primary-background-color);
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  margin-top: 1em;
  color: black;
  transition: color 1s;
  width: 11em;
  height: 2.5em;
  text-align: center;
}

#call-to-action:hover {
  color: var(--primary-color);
}
</style>
<template>
  <div class="home-page">
    <div id="welcome-text-container">
      <p style="font-size: 3em" class="welcome-text">Welcome!</p>
      <p style="width: auto" class="welcome-text">
        We sell new and used books of all genres. Click below to start browsing!
      </p>
      <img
        id="welcome-divider"
        src="@/assets/images/site/Vintage-Decorative-Divider.svg"
        alt=""
      />
    </div>
    <section class="category-images container">
      <home-category-list></home-category-list>
    </section>
    <router-link id="call-to-action" :to="'../category/Classics'"
      >Shop for Books!</router-link
    >
  </div>
</template>
