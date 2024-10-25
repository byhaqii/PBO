package UTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Restaurant {
    private Random random = new Random();
    private int money, reputation;
    private double rating;
    private Queue<Customer> queue = new LinkedList<>();
    private ArrayList<Ingredient> inventory = new ArrayList<>();
    private ArrayList<Food> menu = new ArrayList<>();
    private ArrayList<Employee> employeeList = new ArrayList<>();

    public Restaurant() {
        this.money = 0;
        this.reputation = 0;
        this.rating = 0.0; // Initialize rating
    }

    public int getMoney() {
        return money;
    }

    public int getReputation() {
        return reputation;
    }

    public double getRating() {
        return rating;
    }

    public void addCustomerToQueue(Customer customer) {
        queue.add(customer);
        System.out.println();
        System.out.print("\n\033[0;1m       +++ New Customer +++\033[0;0m");
        System.out.printf("\nCustomer \033[0;1m%s\033[0;0m added to the queue.", customer.getName());
    }

    public void addIngredients(Ingredient... ingredients) {
        inventory.addAll(Arrays.asList(ingredients));
        System.out.println("Ingredients added to inventory.");
    }

    public Queue<Customer> getQueue() {
        return queue;
    }

    private void updateReputation(int reputationChange) {
        this.reputation += reputationChange;
        this.rating = Math.min(5.0, Math.max(0, this.reputation / 10.0)); // Cap rating at 5.0
    }

    private Employee findServingEmployee() {
        for (Employee employee : employeeList) {
            if (employee.hasSkill("serving")) {
                return employee;
            }
        }
        return null; // No employee found with serving skills
    }

    public void takingOrder() {
        if (!queue.isEmpty()) {
            Customer customer = queue.peek();
            Food preferredFood = customer.getPreference();

            if (menu.contains(preferredFood) && checkIngredients(preferredFood.getIngredients())) {
                int cookingTime = (random.nextInt(3) + 1) * preferredFood.getDuration();
                customer.updateSatisfaction(cookingTime);
                cookFood(preferredFood, cookingTime);

                Employee servingEmployee = findServingEmployee();
                if (servingEmployee != null) {
                    int servingTime = random.nextInt(3) + 1; // Random serving time
                    try {
                        System.out.printf("\033[0;1m%s\033[0;0m is serving the food to \033[0;1m%s\033[0;0m...\n", servingEmployee.getName(), customer.getName());
                        Thread.sleep(servingTime * 1000);
                    } catch (InterruptedException e) {
                        System.out.println("Serving interrupted!");
                    }

                    // Update reputation based on randomizer
                    int reputationChange = random.nextInt(21) - 10; // Random change between -10 and +10
                    updateReputation(reputationChange);
                    

                    // After serving, clean up the table
                    Employee cleaningEmployee = findCleaningEmployee();
                    if (cleaningEmployee != null) {
                        cleanUp(cleaningEmployee, customer);
                    } else {
                        System.out.println("No employee available to clean.");
                    }
                } else {
                    System.out.println("No employee available to serve.");
                }

                queue.remove(); // Remove the customer after serving
            } else {
                System.out.printf("Missing ingredients for %s. Customer %s cannot be served.\n", preferredFood.getName(), customer.getName());
                updateReputation(-15);
            }
        } else {
            System.out.println("No customers in the queue to serve.");
        }
    }
    private Employee findCleaningEmployee() {
        for (Employee employee : employeeList) {
            if (employee.hasSkill("cleaning")) {
                return employee;
            }
        }
        return null; // No employee found with cleaning skills
    }

    private boolean checkIngredients(ArrayList<String> requiredIngredients) {
        for (String required : requiredIngredients) {
            boolean found = false;
            for (Ingredient bahan : inventory) {
                if (bahan.getName().equals(required) && bahan.getQuantity() > 0) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private void cookFood(Food food, int cookingTime) {
        Employee cookingEmployee = findCookingEmployee();
        if (cookingEmployee != null) {
            try {
                System.out.printf("\033[0;1m%s\033[0;0m is cooking %s...", cookingEmployee.getName(), food.getName());
                Thread.sleep(cookingTime * 1000);
            } catch (InterruptedException e) {
                System.out.println("Cooking interrupted!");
                return;
            }

            for (String ingredient : food.getIngredients()) {
                for (Ingredient bahan : inventory) {
                    if (bahan.getName().equals(ingredient)) {
                        bahan.reduceQuantity(1); // Reduce the quantity by 1 for each ingredient used
                        break;
                    }
                }
            }
            money += food.getHarga();
            System.out.printf("\n!!! %s is ready !!!%n", food.getName());
        } else {
            System.out.println("No employee available to cook.");
        }
    }

    private Employee findCookingEmployee() {
        for (Employee employee : employeeList) {
            if (employee.hasSkill("cooking")) {
                return employee;
            }
        }
        return null;
    }

    private void cleanUp(Employee cleaningEmployee, Customer customer) {
        int cleaningTime = random.nextInt(3) + 2; // Random cleaning time between 2 to 4 seconds
        try {
            System.out.printf("\033[0;1m%s\033[0;0m is cleaning the table after \033[0;1m%s\033[0;0m...\n", cleaningEmployee.getName(), customer.getName());
            Thread.sleep(cleaningTime * 1000);
        } catch (InterruptedException e) {
            System.out.println("Cleaning interrupted!");
            return;
        }

        // Update customer satisfaction based on cleaning time
        int patienceLevel = customer.getPatienceLevel();
        if (cleaningTime > patienceLevel) {
            customer.updateSatisfaction(-5); // Decrease satisfaction if cleaning time exceeds patience
            updateReputation(-5); // Decrease reputation for slow cleaning
        } else {
            customer.updateSatisfaction(5); // Increase satisfaction for quick cleaning
            updateReputation(5); // Increase reputation for good cleaning
            
        }
        System.out.println("Table cleaned.");
        System.out.println(); 
        System.out.printf("*Customer %s's satisfaction is now: %d*\n", customer.getName(), customer.getSatisfaction());
        System.out.println(); 
    }

    public void addMenu(Food makanan) {
        menu.add(makanan);
        System.out.printf("Menu item %s added.\n", makanan.getName());
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        System.out.printf("Employee %s added to the restaurant.\n", employee.getName());
    }

    public void restockIngredient(String ingredientName, int quantity) {
        for (Ingredient bahan : inventory) {
            if (bahan.getName().equals(ingredientName)) {
                bahan.restock(quantity);
                System.out.printf("Restocked %s by %d.\n", ingredientName, quantity);
                return;
            }
        }
        System.out.printf("Ingredient %s not found in inventory.\n", ingredientName);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (Ingredient bahan : inventory) {
            System.out.printf("%s - Quantity: %d\n", bahan.getName(), bahan.getQuantity());
        }
    }
}
