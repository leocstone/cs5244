<script setup lang="ts"></script>

<style scoped>
footer {
  background: var(--primary-color);
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  margin: 0 auto;
  align-items: center;
}

.links,
.social-media-icons {
  display: flex;
  flex-direction: column;
  text-align: center;
  vertical-align: middle;
}

.social-media-icons {
  text-align: center;
  gap: 0.25em;
  flex-direction: row;
}

.social-media-icon {
  width: 1em;
  height: auto;
  float: left;
  color: white;
}

.social-container {
  display: flex;
}

#ornamental-divider {
  width: 20em;
  height: auto;
  align-self: center;
  margin-top: 15em;
  margin-bottom: 0.25em;
  margin-left: auto;
  margin-right: auto;
}
</style>

<template>
  <img
    id="ornamental-divider"
    src="@/assets/images/site/DecorativeDivider98.svg"
  />
  <footer class="container">
    <section class="links">
      <router-link to="/"><span>&copy;</span> 2022 Leo Stone</router-link>
      <router-link to="/">About</router-link>
      <router-link to="/">Contact</router-link>
      <router-link to="/">Directions</router-link>
    </section>
    <section class="social-media-icons">
      <div style="display: flex">
        <img
          :src="require('@/assets/images/site/facebook_white.svg')"
          :alt="'Facebook'"
          class="social-media-icon"
        />
        <router-link to="/" class="link-social">Facebook</router-link>
      </div>
      <div style="display: flex">
        <img
          :src="require('@/assets/images/site/twitter_white.svg')"
          :alt="'Twitter'"
          class="social-media-icon"
        />
        <router-link to="/" class="link-social">Twitter</router-link>
      </div>
    </section>
  </footer>
</template>
