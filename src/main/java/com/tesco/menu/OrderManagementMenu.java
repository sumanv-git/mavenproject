package com.tesco.menu;

import com.tesco.model.Order;
import com.tesco.repositories.OrderRepository;
import com.tesco.service.order.OrderService;
import com.tesco.service.order.OrderServiceImpl;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderManagementMenu implements MenuHandler {
    private static final Logger logger = LoggerFactory.getLogger(OrderManagementMenu.class);
    private final Scanner scanner;
    private final OrderService orderService;

    public OrderManagementMenu(Scanner scanner, OrderRepository orderRepository) {
        this.scanner = scanner;
        this.orderService = new OrderServiceImpl(orderRepository);
    }

    @Override
    public void showMenu() {
        while (true) {
            logger.info("\nOrder Management Menu:");
            logger.info("1. Add Order");
            logger.info("2. View Orders");
            logger.info("3. Update Order");
            logger.info("4. Delete Order");
            logger.info("5. Back to Main Menu");
            logger.info("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addOrder();
                case 2 -> viewOrders();
                case 3 -> updateOrder();
                case 4 -> deleteOrder();
                case 5 -> {
                    return;
                }
                default -> logger.warn("Invalid choice, please try again!");
            }
        }
    }

    private void addOrder() {
        logger.info("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        logger.info("Enter Item Name: ");
        String itemName = scanner.nextLine();
        logger.info("Enter Quantity: ");
        int quantity = scanner.nextInt();
        logger.info("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Order order = new Order(null, customerName, itemName, quantity, price);
        Order savedOrder = orderService.createOrder(order);
        logger.info("Order created successfully: {}", savedOrder);
    }

    private void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            logger.info("No orders found.");
        } else {
            orders.forEach(order -> logger.info(order.toString()));
        }
    }

    private void updateOrder() {
        logger.info("Enter Order ID to update: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            Order existingOrder = orderService.getOrder(id);
            if (existingOrder == null) {
                logger.warn("Order not found with ID: {}", id);
                return;
            }
            logger.info("Existing Order: {}", existingOrder);
            logger.info("Enter new Customer Name (leave blank to keep current): ");
            String customerName = scanner.nextLine();
            logger.info("Enter new Item Name (leave blank to keep current): ");
            String itemName = scanner.nextLine();
            logger.info("Enter new Quantity (-1 to keep current): ");
            int quantity = scanner.nextInt();
            logger.info("Enter new Price (-1 to keep current): ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume newline

            if (!customerName.isBlank()) existingOrder.setCustomerName(customerName);
            if (!itemName.isBlank()) existingOrder.setItemName(itemName);
            if (quantity >= 0) existingOrder.setQuantity(quantity);
            if (price >= 0) existingOrder.setPrice(price);

            orderService.updateOrder(existingOrder.getId(), existingOrder);
            logger.info("Order updated successfully: {}", existingOrder);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID format entered.");
        }
    }

    private void deleteOrder() {
        logger.info("Enter Order ID to delete: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            Order orderToDelete = orderService.getOrder(id);
            if (orderToDelete == null) {
                logger.warn("Order not found with ID: {}", id);
                return;
            }
            orderService.deleteOrder(orderToDelete.getId());
            logger.info("Order deleted successfully.");

        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID format entered.");
        }
    }
}
