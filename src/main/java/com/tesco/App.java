package com.tesco;

import com.tesco.model.User;
import com.tesco.repositories.InMemoryUserRepositry;
import com.tesco.service.IdGenerator;
import com.tesco.service.UserService;
import com.tesco.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    //private static final List<User> users = new ArrayList<>();
    private static UserService userService;

    static {
        InMemoryUserRepositry userRepository1 = new InMemoryUserRepositry();
        IdGenerator idGenerator = new IdGenerator();
        userService = new UserServiceImpl(userRepository1, idGenerator);


    }

    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {



        while (true) {
            logger.info("\nMenu:");
            logger.info("1. Add User");
            logger.info("2. View Users");
            logger.info("3. Update User");
            logger.info("4. Delete User");
            logger.info("5. Exit");
            logger.info("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> {
                    logger.info("Exiting application...");
                    System.exit(0);
                }
                default -> logger.warn("Invalid choice, please try again!");
            }
        }

    }

    private static void addUser() {
        logger.info("Provide User details to add:");
        logger.info("Enter username:");
        String username = scanner.nextLine();
        logger.info("Enter mobile number:");
        String mobileNumber = scanner.nextLine();
        logger.info("Enter email:");
        String email = scanner.nextLine();

        User savedUser = userService.addUser(new User(username, mobileNumber, email));

        logger.info("User '{}' added successfully!", savedUser);
    }

    private static void viewUsers() {


        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            logger.warn("No users found.");
        } else {
            logger.info("User List:");
            users.forEach(user -> logger.info(" - {}", user));
        }
    }

    private static void updateUser() {
        logger.info("Enter user to update :");
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

            if(!newUsername.isEmpty()) updatedUser.setUsername(newUsername);
            if(!newMobileNumber.isEmpty()) updatedUser.setMobileNumber(newMobileNumber);
            if(!newEmail.isEmpty()) updatedUser.setEmail(newEmail);

            userService.updateUser(existingUser.getId(), updatedUser);

            logger.info("existing user '{}' ",existingUser);
            logger.info("updated user '{}' ",updatedUser);

        }else{
            logger.warn("User '{}' not found!", oldUsername);
        }
    }

    private static void deleteUser() {
        logger.info("Enter username to delete:");
        String username = scanner.nextLine();

        User userToDelete = userService.findUserByName(username);

        if(userToDelete!=null) {
            userService.deleteUser(userToDelete.getId());
            logger.info("User '{}' deleted successfully.", userToDelete);
        }else{
            logger.warn("User '{}' not found!", username);
        }
    }



}
