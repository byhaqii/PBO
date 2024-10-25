package UTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Restaurant restoran = new Restaurant();

        // Sample ingredients
        Ingredient bun = new Ingredient("Bun", 10);
        Ingredient patty = new Ingredient("Patty", 10);
        Ingredient lettuce = new Ingredient("Lettuce", 10);
        Ingredient potatoes = new Ingredient("Potatoes", 10);
        Ingredient salt = new Ingredient("Salt", 10);
        Ingredient water = new Ingredient("Water", 10);
        Ingredient sugar = new Ingredient("Sugar", 10);

        // Adding ingredients to the inventory
        restoran.addIngredients(bun, patty, lettuce, potatoes, salt, water, sugar);

        // Sample menu items
        Food burger = new Food("Burger", 15000, new ArrayList<>(Arrays.asList("Bun", "Patty", "Lettuce")), 5);
        Food fries = new Food("French Fries", 6000, new ArrayList<>(Arrays.asList("Potatoes", "Salt")), 2);
        Food soda = new Food("Soda", 4000, new ArrayList<>(Arrays.asList("Water", "Sugar")), 1);

        // Adding menu items
        restoran.addMenu(burger);
        restoran.addMenu(fries);
        restoran.addMenu(soda);

        // Predefined customers
        List<Customer> predefinedCustomers = Arrays.asList(
            new Customer("Arka", burger, 7),
            new Customer("Seno", fries, 5),
            new Customer("Xavier", burger, 4),
            new Customer("Kevin", fries, 3),
            new Customer("Hammam", soda, 6)
        );

        // Karyawan
        Employee chef = new Employee("Chef Majid", "cooking");
        Employee employee1 = new Employee("Aqilla","serving");
        Employee employee2 = new Employee("Adzin","cleaning");
        restoran.addEmployee(chef);
        restoran.addEmployee(employee1);
        restoran.addEmployee(employee2);

        Scanner scanner = new Scanner(System.in);

        // Start a new thread for customer arrival
        new Thread(() -> {
            List<Customer> arrivedCustomers = new ArrayList<>(); // Track customers who have arrived
            Random arrivalRandom = new Random();

            while (true) {
                try {
                    // Random wait time between 4 to 9 seconds
                    int waitTime = (arrivalRandom.nextInt(6) + 4) * 1500; // in milliseconds
                    Thread.sleep(waitTime);

                    // Check if there are still customers who haven't arrived yet
                    List<Customer> notArrived = new ArrayList<>(predefinedCustomers);
                    notArrived.removeAll(arrivedCustomers);

                    if (!notArrived.isEmpty()) {
                        // Randomly select a customer from those who haven't arrived
                        Customer newCustomer = notArrived.get(arrivalRandom.nextInt(notArrived.size()));
                        arrivedCustomers.add(newCustomer); // Mark this customer as arrived
                        restoran.addCustomerToQueue(newCustomer);
                        System.out.println("\n\033[0;1m         " + newCustomer.getName() + " \033[0;0mhas arrived!");
                        System.out.println("\033[0;1m       ++++++++++++++++++++\033[0;0m\n");
                        System.out.print("Choose an option: ");
                    }
                } catch (InterruptedException e) {
                    System.out.println("Customer arrival interrupted.");
                    break;
                }
            } 
        }).start();

        // Main interaction loop
        while (true) {
            System.out.println("\n\033[0;1m==========================");
            System.out.println("|        PLAYHOUSE       |");
            System.out.println("==========================");
            System.out.println("1. View Queue");
            System.out.println("2. Serve Customer");
            System.out.println("3. View Status (Money & Reputation)");
            System.out.println("4. View Inventory");
            System.out.println("5. Restock Ingredient");
            System.out.println("6. Exit\n\033[0;0m");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                System.out.println();
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    System.out.println("Current Queue: ");
                    if (restoran.getQueue().isEmpty()) {
                        System.out.println("No customers in the queue.");
                    } else {
                        for (Customer customer : restoran.getQueue()) {
                            System.out.printf(" - \033[0;1m%s\033[0;0m (Order: %s)\n", customer.getName(), customer.getOrderDetails());
                        }
                    }
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    break;
                case 2:
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    restoran.takingOrder();
                System.out.println("\033[0;1m========================================================\033[0;0m");
                
                    break;
                case 3:
                System.out.println("\033[0;1m========================================================\033[0;0m");
                    System.out.println("\nStatus Report:");
                    System.out.printf("%-20s: %d\n", "Money", restoran.getMoney());
                    System.out.printf("%-20s: %d\n", "Reputation", restoran.getReputation());
                    System.out.printf("%-20s: %.2f\n", "Rating", restoran.getRating());
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    break;
                case 4:
                System.out.println("\033[0;1m========================================================\033[0;0m");
                    restoran.displayInventory();
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    break;
                case 5:
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    System.out.println("\nEnter ingredient name and quantity to restock:");
                    restoran.displayInventory();
                    String ingredientName = scanner.next();
                    int quantity = scanner.nextInt();
                    restoran.restockIngredient(ingredientName, quantity);
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    break;
                case 6:
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    System.out.println("Exiting...");
                System.out.println("\033[0;1m========================================================\033[0;0m");

                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
