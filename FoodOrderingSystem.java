import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FoodOrderingSystem {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String USER_USERNAME = "user1";
    private static final String USER_PASSWORD = "user123";
    private static Scanner scanner = new Scanner(System.in);
    private ArrayList<FoodItem> foodItems;
    private ArrayList<FoodItem> cart;
    private double totalIncome;

    public FoodOrderingSystem() {
        this.foodItems = new ArrayList<>();
        this.cart = new ArrayList<>();
        this.totalIncome = 0;
    }

    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();
        system.run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\nFood Ordering System");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice) {
                case 1:
                    if (authenticate(ADMIN_USERNAME, ADMIN_PASSWORD)) {
                        adminMenu();
                    } else {
                        System.out.println("Invalid credentials for admin!");
                        clearScreenWithDelay();
                    }
                    break;
                case 2:
                    if (authenticate(USER_USERNAME, USER_PASSWORD)) {
                        userMenu();
                    } else {
                        System.out.println("Invalid credentials for user!");
                        clearScreenWithDelay();
                    }
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    clearScreenWithDelay();
            }
        }
    }

    private boolean authenticate(String expectedUsername, String expectedPassword) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        return username.equals(expectedUsername) && password.equals(expectedPassword);
    }

    private void adminMenu() {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Food Item");
            System.out.println("2. Update Food Item");
            System.out.println("3. Delete Food Item");
            System.out.println("4. View Inventory");
            System.out.println("5. View Sales Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice) {
                case 1:
                    addFoodItem();
                    break;
                case 2:
                    updateFoodItem();
                    break;
                case 3:
                    deleteFoodItem();
                    break;
                case 4:
                    displayInventory();
                    break;
                case 5:
                    viewSalesReport();
                    break;
                case 6:
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    clearScreenWithDelay();
            }
        }
    }

    private void addFoodItem() {
        try {
            System.out.print("Enter food name: ");
            String name = scanner.nextLine();
            System.out.print("Enter food price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter food quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  
            int id = foodItems.size() + 1;
            foodItems.add(new FoodItem(id, name, price, quantity, quantity, 0));
            System.out.println("Food item added!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter the correct data type.");
            scanner.nextLine();  
        }
        clearScreenWithDelay();
    }

    private void updateFoodItem() {
        try {
            System.out.print("Enter food ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();  
            System.out.print("Enter new food name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new food price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter new food quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  
            for (FoodItem item : foodItems) {
                if (item.getId() == id) {
                    item.setName(name);
                    item.setPrice(price);
                    item.setQuantity(quantity);
                    System.out.println("Food item updated!");
                    return;
                }
            }
            System.out.println("Food item not found!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter the correct data type.");
            scanner.nextLine(); 
        }
        clearScreenWithDelay();
    }

    private void deleteFoodItem() {
        try {
            System.out.print("Enter food ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();  
            foodItems.removeIf(item -> item.getId() == id);
           
            for (int i = 0; i < foodItems.size(); i++) {
                foodItems.get(i).setId(i + 1);
            }
            System.out.println("Food item deleted!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter the correct data type.");
            scanner.nextLine();  
        }
        clearScreenWithDelay();
    }

    private void userMenu() {
        boolean ordering = true;
        while (ordering) {
            System.out.println("\nAvailable Food Items:");
            displayInventory();
            System.out.println("1. Add to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Edit Cart");
            System.out.println("4. Cancel Order");
            System.out.println("5. Proceed to Checkout");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addToCart();
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    editCart();
                    break;
                case 4:
                    cancelOrder();
                    ordering = false;
                    break;
                case 5:
                    checkout();
                    ordering = false;
                    break;
                case 6:
                    ordering = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    clearScreenWithDelay();
            }
        }
    }

    private void addToCart() {
        clearScreen();
        System.out.println("\nAvailable Food Items:");
        displayInventory();
        System.out.print("Enter Food ID to add to cart: ");
        int id = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        for (FoodItem item : foodItems) {
            if (item.getId() == id) {
                for (FoodItem cartItem : cart) {
                    if (cartItem.getId() == id) {
                        int newQuantity = cartItem.getQuantity() + quantity;
                        if (newQuantity <= item.getQuantity()) {
                            cartItem.setQuantity(newQuantity);
                            System.out.println("Item quantity updated in cart!");
                            clearScreenWithDelay();
                            return;
                        } else {
                            System.out.println("Requested quantity exceeds available stock!");
                            clearScreenWithDelay();
                            return;
                        }
                    }
                }
                if (quantity <= item.getQuantity()) {
                    cart.add(new FoodItem(id, item.getName(), item.getPrice(), quantity, item.getInitialQuantity(), item.getSoldQuantity()));
                    System.out.println("Item added to cart!");
                    clearScreenWithDelay();
                } else {
                    System.out.println("Requested quantity exceeds available stock!");
                    clearScreenWithDelay();
                }
                return;
            }
        }
        System.out.println("Food item not found!");
        clearScreenWithDelay();
    }

    private void viewCart() {
        clearScreen();
        System.out.println("\nCart Items:");
        System.out.println("+-----+----------------------+----------+----------+");
        System.out.printf("| %-3s | %-20s | %-8s | %-8s |\n", "ID", "Name", "Price", "Quantity");
        System.out.println("+-----+----------------------+----------+----------+");
        for (FoodItem item : cart) {
            System.out.printf("| %-3d | %-20s | $%-7.2f | %-8d |\n", item.getId(), item.getName(), item.getPrice(), item.getQuantity());
        }
        System.out.println("+-----+----------------------+----------+----------+");
    }

    private void editCart() {
        clearScreen();
        System.out.println("\nCart Items:");
        viewCart();
        System.out.print("Enter Food ID to edit in cart: ");
        int id = scanner.nextInt();
        for (FoodItem item : cart) {
            if (item.getId() == id) {
                System.out.print("Enter new quantity: ");
                int quantity = scanner.nextInt();
                if (quantity == 0) {
                    cart.remove(item);
                    System.out.println("Food item removed from cart.");
                    clearScreenWithDelay();
                    return;
                }
                for (FoodItem invItem : foodItems) {
                    if (invItem.getId() == id) {
                        if (quantity <= invItem.getQuantity()) {
                            item.setQuantity(quantity);
                            System.out.println("Food item quantity updated in cart.");
                            clearScreenWithDelay();
                        } else {
                            System.out.println("Requested quantity exceeds available stock!");
                            clearScreenWithDelay();
                        }
                        return;
                    }
                }
            }
        }
        System.out.println("Food item not found in cart!");
        clearScreenWithDelay();
    }

    private void cancelOrder() {
        cart.clear();
        System.out.println("Order canceled.");
        clearScreenWithDelay();
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty! Please add items to your cart before checking out.");
            clearScreenWithDelay();
            return;
        }

        clearScreen();
        double total = 0;
        System.out.println("Receipt - Food Ordering System\n");
        System.out.println("+----------------------+----------+----------+");
        System.out.printf("| %-20s | %-8s | %-8s |\n", "Food", "Quantity", "Price");
        System.out.println("+----------------------+----------+----------+");
        for (FoodItem item : cart) {
            System.out.printf("| %-20s | %-8d | $%-7.2f |\n", item.getName(), item.getQuantity(), item.getPrice() * item.getQuantity());
            total += item.getPrice() * item.getQuantity();
        }
        System.out.println("+----------------------+----------+----------+");
        System.out.printf("| %-20s | %-8s | $%-7.2f |\n", "", "Total:", total);
        System.out.println("+----------------------+----------+----------+");

        System.out.println("\nChoose payment method:");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. Online Payment");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        int paymentChoice = scanner.nextInt();

        if (paymentChoice == 1) {
            handleCashOnDelivery();
        } else if (paymentChoice == 2) {
            handleOnlinePayment();
        } else if (paymentChoice == 0) {
            return;
        } else {
            System.out.println("Invalid choice! Payment not completed.");
            return;
        }

        updateInventory();
        System.out.println("\nTransaction complete.");
        System.out.println("Thank you for using our Food Ordering System!");
        System.out.println("Your receipt has been printed.");
        cart.clear();
    }

    private void handleCashOnDelivery() {
        clearScreen();
        System.out.print("Have you received the order? (yes/no): ");
        String received = scanner.next();
        if (received.equalsIgnoreCase("yes")) {
            System.out.println("Transaction complete. Thank you!");
        } else {
            System.out.println("Please receive the order first.");
        }
        clearScreenWithDelay();
    }

    private void handleOnlinePayment() {
        clearScreen();
        System.out.println("\nChoose online payment method:");
        System.out.println("1. GCash");
        System.out.println("2. BPI");
        System.out.println("3. BDO");
        System.out.print("Enter your choice: ");
        int onlineChoice = scanner.nextInt();

        switch (onlineChoice) {
            case 1:
                System.out.println("Payment through GCash completed.");
                break;
            case 2:
                System.out.println("Payment through BPI completed.");
                break;
            case 3:
                System.out.println("Payment through BDO completed.");
                break;
            default:
                System.out.println("Invalid choice! Payment not completed.");
                clearScreenWithDelay();
                return;
        }
        clearScreenWithDelay();
    }

    private void updateInventory() {
        for (FoodItem cartItem : cart) {
            for (FoodItem invItem : foodItems) {
                if (cartItem.getId() == invItem.getId()) {
                    int newQuantity = invItem.getQuantity() - cartItem.getQuantity();
                    invItem.setQuantity(newQuantity);
                    invItem.increaseSoldQuantity(cartItem.getQuantity());
                    totalIncome += cartItem.getPrice() * cartItem.getQuantity();
                }
            }
        }
    }

    private void displayInventory() {
        System.out.println("+-----+----------------------+----------+----------+");
        System.out.printf("| %-3s | %-20s | %-8s | %-8s |\n", "ID", "Name", "Price", "Quantity");
        System.out.println("+-----+----------------------+----------+----------+");
        for (FoodItem item : foodItems) {
            System.out.printf("| %-3d | %-20s | $%-7.2f | %-8d |\n", item.getId(), item.getName(), item.getPrice(), item.getQuantity());
        }
        System.out.println("+-----+----------------------+----------+----------+");
    }

    private void viewSalesReport() {
        clearScreen();
        System.out.println("Sales Report - Food Ordering System\n");
        System.out.println("+-----+----------------------+----------+---------------+---------------+---------------+");
        System.out.printf("| %-3s | %-20s | %-8s | %-13s | %-13s | %-13s |\n", "ID", "Name", "Price", "Initial Qty", "Sold Qty", "Income");
        System.out.println("+-----+----------------------+----------+---------------+---------------+---------------+");
        for (FoodItem item : foodItems) {
            double income = item.getPrice() * item.getSoldQuantity();
            System.out.printf("| %-3d | %-20s | $%-7.2f | %-13d | %-13d | $%-12.2f |\n", item.getId(), item.getName(), item.getPrice(), item.getInitialQuantity(), item.getSoldQuantity(), income);
        }
        System.out.println("+-----+----------------------+----------+---------------+---------------+---------------+");
        System.out.printf("| %-50s | %-13s | $%-12.2f |\n", "", "Total Income", totalIncome);
        System.out.println("+-----+----------------------+----------+---------------+---------------+---------------+");
        clearScreenWithDelay();
    }

    private void clearScreenWithDelay() {
        try {
            Thread.sleep(6000);  // Delay for 4 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearScreen();
    }

    private void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    class FoodItem {
        private int id;
        private String name;
        private double price;
        private int quantity;
        private int initialQuantity;
        private int soldQuantity;

        public FoodItem(int id, String name, double price, int quantity, int initialQuantity, int soldQuantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.initialQuantity = initialQuantity;
            this.soldQuantity = soldQuantity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getInitialQuantity() {
            return initialQuantity;
        }

        public int getSoldQuantity() {
            return soldQuantity;
        }

        public void increaseSoldQuantity(int quantity) {
            this.soldQuantity += quantity;
        }

        @Override
        public String toString() {
            return String.format("| %-3d | %-20s | $%-7.2f | %-8d |", id, name, price, quantity);
        }
    }
}