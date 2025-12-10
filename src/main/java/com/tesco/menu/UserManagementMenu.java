package com.tesco.menu;

import com.tesco.model.User;
import com.tesco.repositories.InMemoryUserRepositry;
import com.tesco.service.IdGenerator;
import com.tesco.service.user.UserService;
import com.tesco.service.user.UserServiceImpl;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagementMenu implements MenuHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementMenu.class);
    private final UserService userService;
    private final Scanner scanner;

    public UserManagementMenu(Scanner scanner) {
        this.scanner = scanner;
        this.userService = new UserServiceImpl(new InMemoryUserRepositry(), new IdGenerator());
    }

    @Override
    public void showMenu() {
        while (true) {
            logger.info("\nUser Management Menu:");
            logger.info("1. Add User");
            logger.info("2. View Users");
            logger.info("3. Update User");
            logger.info("4. Delete User");
            logger.info("5. Back to Main Menu");
            logger.info("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> {
                    return;
                } // exit user menu, go back to App menu
                default -> logger.warn("Invalid choice, please try again!");
            }
        }
    }

    private void addUser() {
        logger.info("Enter username:");
        String username = scanner.nextLine();
        logger.info("Enter mobile number:");
        String mobileNumber = scanner.nextLine();
        logger.info("Enter email:");
        String email = scanner.nextLine();

        User savedUser = userService.addUser(new User(username, mobileNumber, email));
        logger.info("User '{}' added successfully!", savedUser);
    }

    private void viewUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No users found.");
        } else {
            logger.info("User List:");
            users.forEach(user -> logger.info(" - {}", user));
        }
    }

    private void updateUser() {
        logger.info("Enter user to update:");
        String oldUsername = scanner.nextLine();
        User existingUser = userService.findUserByName(oldUsername);

        if (Objects.nonNull(existingUser)) {
            logger.info("Enter new username:");
            String newUsername = scanner.nextLine();
            logger.info("Enter new mobile number:");
            String newMobileNumber = scanner.nextLine();
            logger.info("Enter new email:");
            String newEmail = scanner.nextLine();

            User updatedUser = new User(existingUser);
            if (!newUsername.isEmpty()) updatedUser.setUsername(newUsername);
            if (!newMobileNumber.isEmpty()) updatedUser.setMobileNumber(newMobileNumber);
            if (!newEmail.isEmpty()) updatedUser.setEmail(newEmail);

            userService.updateUser(existingUser.getId(), updatedUser);
            logger.info("Updated user '{}' ", updatedUser);
        } else {
            logger.warn("User '{}' not found!", oldUsername);
        }
    }

    private void deleteUser() {
        logger.info("Enter username to delete:");
        String username = scanner.nextLine();
        User userToDelete = userService.findUserByName(username);

        if (userToDelete != null) {
            userService.deleteUser(userToDelete.getId());
            logger.info("User '{}' deleted successfully.", userToDelete);
        } else {
            logger.warn("User '{}' not found!", username);
        }
    }
}
