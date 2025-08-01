<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable";

import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;

const orderDate = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toLocaleTimeString();
});

const ccExpDate = computed(function (): Date {
  return new Date(orderDetails.customer.ccExpDate);
});

const ccExpYear = computed(function (): number {
  return ccExpDate.value.getFullYear();
});

const ccExpMonth = computed(function (): string {
  let month = ccExpDate.value.getMonth() + 1;
  let result = "" + month;
  if (month < 10) {
    result = "0" + month;
  }
  return result;
});

const formattedPhoneNumber = computed(function (): string {
  let areaCode = "(" + orderDetails.customer.phone.substring(0, 3) + ")";
  let exchangeCode = orderDetails.customer.phone.substring(3, 6);
  let phoneNumber = orderDetails.customer.phone.substring(6, 10);
  return areaCode + "-" + exchangeCode + "-" + phoneNumber;
});

const formattedCcNumber = computed(function (): string {
  return "**** **** **** " + orderDetails.customer.ccNumber.substring(12, 16);
});

const formattedCcExpDate = computed(function (): string {
  return "(" + ccExpMonth.value + "-" + ccExpYear.value + ")";
});
</script>

<style scoped>
#confirmationView {
  display: flex;
  flex-direction: column;
  align-items: center;
}
ul {
  text-align: left;
}
ul > li {
  margin: 0.25em;
}
#confirmationMessage {
  text-align: center;
  margin-bottom: 1em;
}
</style>

<template>
  <div id="confirmationView" v-if="orderDetailsStore.hasOrderDetails">
    <h1 id="confirmationMessage">Your order is confirmed!</h1>
    <ul>
      <li>Confirmation #: {{ orderDetails.order.confirmationNumber }}</li>
      <li>Time: {{ orderDate }}</li>
    </ul>
    <confirmation-table> </confirmation-table>
    <ul>
      <li>{{ orderDetails.customer.customerName }}</li>
      <li>{{ orderDetails.customer.address }}</li>
      <li>{{ orderDetails.customer.email }}</li>
      <li>{{ formattedPhoneNumber }}</li>
      <li>{{ formattedCcNumber }} {{ formattedCcExpDate }}</li>
    </ul>
    <div id="customerInfo"></div>
  </div>
  <h1 style="text-align: center; margin-bottom: 1em" v-else>
    You haven't placed any orders yet!
  </h1>
</template>
