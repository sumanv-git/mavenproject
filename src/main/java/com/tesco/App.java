package com.tesco;

import com.tesco.menu.OrderManagementMenu;
import com.tesco.menu.UserManagementMenu;
import com.tesco.repositories.InMemoryOrderRepository;
import com.tesco.repositories.OrderRepository;
import com.tesco.repositories.PostgresOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                case 2 -> {
                    OrderRepository selectedRepo = chooseOrderRepository();
                    new OrderManagementMenu(scanner, selectedRepo).showMenu();
                }
                case 3 -> {
                    logger.info("Exiting application...");
                    System.exit(0);
                }
                default -> logger.warn("Invalid choice, please try again!");
            }
        }
    }

    private static OrderRepository chooseOrderRepository() {
        logger.info("\nSelect Order Repository:");
        logger.info("1. In-Memory Repository (No DB)");
        logger.info("2. Postgres Repository (Requires DB)");
        logger.info("Enter your choice: ");

        int repoChoice = scanner.nextInt();
        scanner.nextLine();

        return switch (repoChoice) {
            case 1 -> {
                logger.info("Using InMemoryOrderRepository...");
                yield new InMemoryOrderRepository();
            }
            case 2 -> {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("tescoPU");
                EntityManager em = emf.createEntityManager();
                yield new PostgresOrderRepository(em);
            }
            default -> {
                logger.warn("Invalid choice, defaulting to In-Memory Repository...");
                yield new InMemoryOrderRepository();
            }
        };
    }
}
