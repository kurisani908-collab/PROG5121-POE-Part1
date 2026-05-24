import java.util.Scanner;

// Main application class
public class PROG5121POE {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Registration section
        System.out.println("=== User Registration ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number: ");
        String cellphoneNumber = scanner.nextLine();

        Registration registeredUser =
                new Registration(username, password, cellphoneNumber);

        String registrationResult = registeredUser.registerUser();
        System.out.println(registrationResult);

        if (!registrationResult.equals("User has been registered successfully.")) {
            System.out.println("Registration failed. You cannot access QuickChat.");
            scanner.close();
            return;
        }

        // Login section
        System.out.println("\n=== User Login ===");

        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        Login login =
                new Login(registeredUser, enteredUsername, enteredPassword);

        if (!login.loginUser()) {
            System.out.println("Login failed. You cannot send messages.");
            scanner.close();
            return;
        }

        // QuickChat starts only after successful login
        System.out.println("Welcome to QuickChat.");

        boolean running = true;

        while (running) {

            System.out.println("\nSelect an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("How many messages would you like to send? ");
                    int numberOfMessages = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 1; i <= numberOfMessages; i++) {

                        System.out.println("\nMessage " + i);

                        String recipient;

                        while (true) {
                            System.out.print("Enter recipient number: ");
                            recipient = scanner.nextLine();

                            if (recipient.startsWith("+") && recipient.length() <= 13) {
                                System.out.println("Cell phone number successfully captured.");
                                break;
                            }

                            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        }

                        String messageText;

                        while (true) {
                            System.out.print("Enter your message: ");
                            messageText = scanner.nextLine();

                            if (messageText.length() <= 250) {
                                System.out.println("Message ready to send.");
                                break;
                            }

                            int excessCharacters = messageText.length() - 250;

                            System.out.println("Message exceeds 250 characters by "
                                    + excessCharacters
                                    + ", please reduce size.");
                        }

                        Message message =
                                new Message(i, recipient, messageText);

                        System.out.println("\nChoose message action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");

                        int action = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println(message.sentMessage(action));

                        if (action == 1) {
                            System.out.println("\n" + message.printMessages());
                        }
                    }

                    System.out.println("\nTotal messages sent: "
                            + Message.returnTotalMessages());

                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option selected.");
            }
        }

        scanner.close();
    }
}