package com.tesco;

import com.tesco.menu.OrderManagementMenu;
import com.tesco.menu.UserManagementMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            logger.info("\nMain Menu:");
            logger.info("1. User Management");
            logger.info("2. Order Management");
            logger.info("3. Exit");
            logger.info("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> new UserManagementMenu(scanner).showMenu();
                case 2 -> new OrderManagementMenu(scanner).showMenu();
                case 3 -> {
                    logger.info("Exiting application...");
                    System.exit(0);
                }
                default -> logger.warn("Invalid choice, please try again!");
            }
        }
    }
}
