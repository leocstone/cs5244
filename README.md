## Final Project for Web Application Development
This project implements a complete web application, including a frontend written with Vue.js and a backend written in Java using Jersey.

The frontend [queries the backend server](client/stores), which [queries a SQL server](server/src/main/java/business/category/CategoryDaoJdbc.java) for the details of the books listed on the website.

The client can add books to their cart, and [POST an order to the backend](client/src/stores/CartStore.ts), where it will be [validated and stored in the SQL database](server/src/main/java/business/order/DefaultOrderService.java).

The contents of the client's cart are stored as a cookie, so it is saved across browser sessions.

