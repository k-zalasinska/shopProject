package main;

import model.Customer;
import model.Order;
import model.OrderStatus;
import model.Product;
import service.CategoryService;
import service.OrderService;
import service.ProductService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	private final Scanner scanner = new Scanner(System.in);
	private final ProductService productService = new ProductService();
	private final CategoryService categoryService = new CategoryService();
	private final OrderService orderService = new OrderService();

	{
		createCategories();
		createProducts(); //blok inicjaliz. danych
		createOrders();

	}

	public void createOrders() {
		final Map<Product, Integer> productsMap = new HashMap<>();
		productsMap.put(productService.getProductById(1), 2);
		productsMap.put(productService.getProductById(2), 3);
		productsMap.put(productService.getProductById(3), 2);
		productsMap.put(productService.getProductById(4), 4);

		final Customer customer1 = new Customer("Anna", "Nowak", "Kryształowa 7, 48 - 300 Nysa");
		final Customer customer2 = new Customer("Adam", "Nowak", "Kryształowa 7, 48 - 300 Nysa");
		final Customer customer3 = new Customer("Tomasz", "Kowalski", "Lazurowa 1, 48 - 300 Nysa");
		final Customer customer4 = new Customer("Joanna", "Kowalska", "Lazurowa 1, 48 - 300 Nysa");

		orderService.createAndAddOrder(customer1, OrderStatus.PREPARING, productsMap);
		orderService.createAndAddOrder(customer2, OrderStatus.PAID, productsMap);
		orderService.createAndAddOrder(customer3, OrderStatus.SHIPPED, productsMap);
		orderService.createAndAddOrder(customer4, OrderStatus.PREPARING, productsMap);
	}

	public void createCategories() {
		categoryService.createAndAddCategory("Clothing");
		categoryService.createAndAddCategory("Footwear");
		categoryService.createAndAddCategory("Accessories");
	}

	public void createProducts() {
		productService.createAndAddProduct(199.99, "Dress", categoryService.getAllCategories().get(0));
		productService.createAndAddProduct(149.99, "Heels", categoryService.getAllCategories().get(1));
		productService.createAndAddProduct(39.99, "Cap", categoryService.getAllCategories().get(2));
		productService.createAndAddProduct(79.99, "Trousers", categoryService.getAllCategories().get(1));
		productService.createAndAddProduct(249.99, "Sneakers", categoryService.getAllCategories().get(1));
		productService.createAndAddProduct(19.99, "Hat", categoryService.getAllCategories().get(2));
		productService.createAndAddProduct(79.99, "Earrings", categoryService.getAllCategories().get(2));
	}

	public void changeOrderStatus(final String orderNumber, final OrderStatus newStatus) {
		final Order order = orderService.findOrder(orderNumber);
		if (order != null) {
			order.setOrderStatus(newStatus);
			System.out.println("Zmieniono status zamówienia o numerze " + orderNumber + " na: " + newStatus);
		} else {
			System.out.println("Nie zmieniono statusu dla zamówienia o numerze: " + orderNumber
					+ ". Podany numer zamówienia nie istnieje, bądź jest nieprawidłowy.");
		}
	}

	public void showMainMenu() {
		boolean exit = false;
		while (!exit) {
			System.out.println("[1] Zamówiena");
			System.out.println("[2] Kategorie produktów");
			System.out.println("[3] Produkty");
			System.out.println("[4] Wyjdź");

			final int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> showOrderSubMenu();
				case 2 -> showCategorySubMenu();
				case 3 -> showProductSubMenu();
				case 4 -> exit = true;
				default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
			}

			System.out.println();
		}
	}

	public void showOrderSubMenu() {
		boolean back = false;
		while (!back) {
			System.out.println("[1] Lista zamówień");
			System.out.println("[2,OrderNumber] Konkretne zamówienie");
			System.out.println("[3,clientName,clientSurname,clientAddress,orderStatus] Dodaj zamówienie");
			System.out.println("[4,OrderNumber] Usuń zamówienie");
			System.out.println("[5] Cofnij");

			final String choice = scanner.next();
			final String[] words = choice.split(",");
			scanner.nextLine();

			switch (Integer.parseInt(words[0])) {
				case 1 -> System.out.println(orderService.getAllOrders());
				case 2 -> System.out.println(orderService.findOrder(words[1]));
				case 3 -> {
//                    System.out.println(orderService.createAndAddOrder(words[1], words[2], words[3],
//                            OrderStatus.valueOf(words[4]), words[5]));
				}
				case 4 -> orderService.removeOrderByNumber(words[1]);
				case 5 -> back = true;
				default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
			}

			System.out.println();
		}
	}

	public void showCategorySubMenu() {
		boolean back = false;
		while (!back) {
			System.out.println("[1] Lista kategorii");
			System.out.println("[2,categoryID,categoryName] Konkretna kategoria");
			System.out.println("[3,name] Dodaj kategorie");
			System.out.println("[4,categoryID] Usuń kategorie");
			System.out.println("[5] Cofnij");

			final String choice = scanner.next();
			final String[] words = choice.split(",");
			scanner.nextLine();

			switch (Integer.parseInt(words[0])) {
				case 1 -> System.out.println(categoryService.getAllCategories());
				case 2 ->
						System.out.println(categoryService.getCategoryByIdOrName(Integer.parseInt(words[1]), words[2]));
				case 3 -> categoryService.createAndAddCategory(words[1]);
				case 4 -> categoryService.removeCategory(Integer.parseInt(words[1]));
				case 5 -> back = true;
				default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
			}

			System.out.println();
		}
	}

	public void showProductSubMenu() {
		boolean back = false;
		while (!back) {
			System.out.println("[1] Lista produktów");
			System.out.println("[2,productId] Konkretny produkt");
			System.out.println("[3,price,name,category] Dodaj produkt");
			System.out.println("[4,productId] Usuń produkt ");
			System.out.println("[5] Cofnij");

			final String choice = scanner.next();
			final String[] words = choice.split(",");
			scanner.nextLine();

			switch (Integer.parseInt(words[0])) {
				case 1 -> System.out.println(productService.getAllProducts());
				case 2 -> System.out.println(productService.getProductById(Integer.parseInt(words[1])));
				case 3 -> {
//                    System.out.println(productService.createAndAddProduct(Double.parseDouble(words[1]), words[2],
//                            words[3]));
				}
				case 4 -> productService.removeProduct(Integer.parseInt(words[1]));
				case 5 -> back = true;
				default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
			}

			System.out.println();
		}
	}
}
