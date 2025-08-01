package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private Random random = new Random();
	private BookDao bookDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	private Date getDate(String monthString, String yearString) {
		return new GregorianCalendar(Integer.parseInt(yearString), Integer.parseInt(monthString), 1).getTime();
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId, item.getBookId(),
						item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		return random.nextInt(999999999);
	}

	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();
		if (name == null || name.equals("") || name.length() < 4 || name.length() > 45) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		String address = customerForm.getAddress();
		if(address == null || address.equals("") || address.length() < 4 || address.length() > 45) {
			throw new ApiException.InvalidParameter("Invalid address");
		}

		String phone = customerForm.getPhone();
		if(phone == null || phone.equals("")) {
			throw new ApiException.InvalidParameter("Invalid phone number: No phone number");
		}
		phone = phone.trim().replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\s", "");
		if(phone.length() != 10) {
			throw new ApiException.InvalidParameter("Invalid phone number: Invalid length (" + phone + ")");
		}

		String email = customerForm.getEmail();
		if(email == null || email.equals("") || email.contains(" ") || !email.contains("@") || email.endsWith(".")) {
			throw new ApiException.InvalidParameter("Invalid email");
		}

		String ccNumber = customerForm.getCcNumber();

		if(ccNumber == null || ccNumber.equals("")) {
			throw new ApiException.InvalidParameter("Invalid CC number: No CC number");
		}
		ccNumber = ccNumber.trim().replaceAll("\\s", "").replaceAll("-", "");
		int ccNumberLength = ccNumber.length();
		if(ccNumberLength < 14 || ccNumberLength > 16) {
			throw new ApiException.InvalidParameter("Invalid CC number: Invalid length");
		}

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date: " + customerForm.getCcExpiryMonth() + " " + customerForm.getCcExpiryYear());
		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {
		YearMonth currentDate = YearMonth.now();
		int expMonthInt;
		int expYearInt;
		try {
			expMonthInt = Integer.parseInt(ccExpiryMonth);
			expYearInt = Integer.parseInt(ccExpiryYear);
		} catch(Exception e) {
			return true;
		}
		return currentDate.isAfter(YearMonth.of(expYearInt, expMonthInt));
	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if(item.getBookForm().getPrice() != databaseBook.getPrice()) {
				throw new ApiException.InvalidParameter("Invalid price for book: " + databaseBook.getTitle());
			}
			if(item.getBookForm().getCategoryId() != databaseBook.getCategoryId()) {
				throw new ApiException.InvalidParameter("Invalid category for book: " + databaseBook.getTitle());
			}
		});
	}

}
