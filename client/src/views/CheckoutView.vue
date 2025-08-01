<script setup lang="ts">
import { reactive, Ref } from "vue";
import useVuelidate from "@vuelidate/core";
import {
  helpers,
  maxLength,
  minLength,
  required,
  email,
} from "@vuelidate/validators";
import { useCartStore } from "@/stores/CartStore";
import { useCategoryStore } from "@/stores/CategoryStore";
import { asDollarsAndCents } from "@/main";
import { isMobilePhone } from "@/utils";
import { isCreditCard } from "@/utils";

const cartStore = useCartStore();
const categoryStore = useCategoryStore();
const cart = cartStore.cart;
// import { isCreditCard, isMobilePhone } from "@/utils";
// import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import router from "@/router";

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  address: {
    required: helpers.withMessage("Please provide an address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 10 letters.",
      minLength(10)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 100 letters.",
      maxLength(100)
    ),
  },
  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    isMobilePhone: helpers.withMessage(
      "Please provide a valid US mobile number.",
      isMobilePhone
    ),
  },
  email: {
    required: helpers.withMessage("Please provide an email.", required),
    validEmail: helpers.withMessage("Please provide a vaild email.", email),
  },
  ccNumber: {
    required: helpers.withMessage(
      "Please provide a credit card number.",
      required
    ),
    isCreditCard: helpers.withMessage(
      "Please provide a valid credit card number.",
      isCreditCard
    ),
  },
};
const $v = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await $v.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    form.checkoutStatus = "PENDING";
    console.log("Placing order: ");
    console.log(form);
    await cartStore
      .placeOrder({
        name: form.name,
        address: form.address,
        phone: form.phone,
        email: form.email,
        ccNumber: form.ccNumber,
        ccExpiryMonth: form.ccExpiryMonth,
        ccExpiryYear: form.ccExpiryYear,
      })
      .then(() => {
        form.checkoutStatus = "OK";
        cartStore.clearCart();
        router.push({ name: "confirmation-view" });
      })
      .catch((reason) => {
        form.checkoutStatus = "SERVER_ERROR";
        console.log("Error placing order", reason);
      });
  }
}

/* NOTE: For example yearFrom(0) == <current_year> */
function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
.checkout-page {
  background: white;
  color: black;
}
.checkout-page-body {
  display: flex;
  flex-direction: column;
  padding: 1em;
}

form {
  display: flex;
  flex-direction: column;
  margin-left: auto;
  margin-right: auto;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
}

form > div > input,
form > div > select {
  background-color: #eeeeee;
  margin-left: 0.5em;
}

form > .error {
  color: red;
  text-align: right;
}

.error-text {
  color: red;
  font-size: large;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}

.checkout-button {
  color: var(--primary-color);
  text-align: center;
  margin: 2em auto auto;
}

.complete-button {
  margin-left: auto;
}

.cart-actions {
  width: fit-content;
  align-content: center;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-direction: column;
}

#total {
  text-align: center;
  margin: 0.3em 0.3em 0.3em auto;
}

.error-message {
  margin-bottom: 2em;
  font-size: small;
}
</style>

<template>
  <div class="checkout-page">
    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <div>
          <label for="name">Name:</label>
          <input
            type="text"
            size="20"
            id="name"
            name="name"
            v-model.lazy="$v.name.$model"
          />
        </div>
        <template v-if="$v.name.$error">
          <span
            class="error error-message"
            v-for="error of $v.name.$errors"
            :key="error.$uid"
            >{{ error.$message }}</span
          >
        </template>
        <div>
          <label for="address">Address:</label>
          <input
            type="text"
            size="20"
            id="address"
            name="address"
            v-model.lazy="$v.address.$model"
          />
        </div>
        <template v-if="$v.address.$error">
          <span
            class="error error-message"
            v-for="error of $v.address.$errors"
            :key="error.$uid"
            >{{ error.$message }}</span
          >
        </template>
        <div>
          <label for="phone">Phone:</label>
          <input
            type="text"
            size="20"
            id="phone"
            name="phone"
            v-model.lazy="$v.phone.$model"
          />
        </div>
        <template v-if="$v.phone.$error">
          <span
            class="error error-message"
            v-for="error of $v.phone.$errors"
            :key="error.$uid"
            >{{ error.$message }}</span
          >
        </template>

        <div>
          <label for="email">Email:</label>
          <input
            type="text"
            size="20"
            id="email"
            name="email"
            v-model.lazy="$v.email.$model"
          />
        </div>
        <template v-if="$v.email.$error">
          <span
            class="error error-message"
            v-for="error of $v.email.$errors"
            :key="error.$uid"
            >{{ error.$message }}</span
          >
        </template>

        <div>
          <label for="ccNumber">Credit Card Number:</label>
          <input
            type="text"
            size="20"
            id="ccNumber"
            name="ccNumber"
            v-model.lazy="$v.ccNumber.$model"
          />
        </div>
        <template v-if="$v.ccNumber.$error">
          <span
            class="error error-message"
            v-for="error of $v.ccNumber.$errors"
            :key="error.$uid"
            >{{ error.$message }}</span
          >
        </template>

        <div>
          <label>Exp. Date:</label>
          <select v-model="$v.ccExpiryMonth">
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
            >
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <select v-model="$v.ccExpiryYear">
            <option v-for="(year, index) in 16" :key="index" :value="index + 1">
              {{ year + 2021 }}
            </option>
          </select>
        </div>

        <input
          type="submit"
          name="submit"
          class="button complete-button"
          :disabled="form.checkoutStatus === 'PENDING'"
          value="Complete Purchase"
        />
        <p id="total">
          Total: ( {{ asDollarsAndCents(cartStore.total) }} + $5.00 shipping ) =
          {{ asDollarsAndCents(cartStore.total + 500) }}
        </p>
      </form>

      <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
        <div class="error-text" v-if="form.checkoutStatus === 'ERROR'">
          Error: Please fix the problems above and try again.
        </div>

        <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>

        <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

        <div v-else>An unexpected error occurred, please try again.</div>
      </section>
    </section>
    <section v-else>
      <div class="cart-actions">
        <h1 style="text-align: center; width: 100%">
          You must add something to your cart to check out!
        </h1>
        <router-link
          class="checkout-button button"
          :to="'../category/' + categoryStore.selectedCategoryName"
          >Continue Shopping</router-link
        >
      </div>
    </section>
  </div>
</template>
