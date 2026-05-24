import java.util.Scanner;

// Main application class
public class PROG5121POE {

    public static void main(String[] args) {

        // Scanner object for user input
        Scanner scanner = new Scanner(System.in);
        

        // Welcome message required in instructions
        System.out.println("Welcome to QuickChat.");

        // Controls application loop
        boolean running = true;

        // While loop keeps application running
        while (running) {

            // Display menu options
            System.out.println("\nSelect an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            // Capture menu option
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // Option 1 - Send Messages
                case 1:

                    // Ask user how many messages they want to send
                    System.out.print("How many messages would you like to send? ");

                    int numberOfMessages = scanner.nextInt();
                    scanner.nextLine();

                    // For loop for messages
                    for (int i = 1; i <= numberOfMessages; i++) {

                        System.out.println("\nMessage " + i);

                        String recipient;

                        // Keep asking until recipient number is valid
                        while (true) {

                            System.out.print("Enter recipient number: ");
                            recipient = scanner.nextLine();

                            // Validate cellphone number
                            if (recipient.startsWith("+")
                                    && recipient.length() <= 13) {

                                System.out.println(
                                        "Cell phone number successfully captured."
                                );

                                break;
                            }

                            // Invalid number message
                            System.out.println(
                                    "Cell phone number is incorrectly formatted "
                                            + "or does not contain an international code. "
                                            + "Please correct the number and try again."
                            );
                        }

                        String messageText;

                        // Keep asking until message length is valid
                        while (true) {

                            System.out.print("Enter your message: ");
                            messageText = scanner.nextLine();

                            // Validate message length
                            if (messageText.length() <= 250) {

                                System.out.println(
                                        "Message ready to send."
                                );

                                break;
                            }

                            // Display excess characters
                            int excessCharacters =
                                    messageText.length() - 250;

                            System.out.println(
                                    "Message exceeds 250 characters by "
                                            + excessCharacters
                                            + ", please reduce size."
                            );
                        }

                        // Create message object
                        Message message =
                                new Message(
                                        i,
                                        recipient,
                                        messageText
                                );

                        // Display message options
                        System.out.println("\nChoose message action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");

                        // Capture action
                        int action = scanner.nextInt();
                        scanner.nextLine();

                        // Display action result
                        System.out.println(
                                message.sentMessage(action)
                        );

                        // Display message details if sent
                        if (action == 1) {

                            System.out.println(
                                    "\n" + message.printMessages()
                            );
                        }
                    }

                    // Display total messages sent
                    System.out.println(
                            "\nTotal messages sent: "
                                    + Message.returnTotalMessages()
                    );

                    break;

                // Option 2
                // Per instructions this feature is still in development
                case 2:

                    System.out.println("Coming Soon.");
                    break;

                // Option 3 - Quit application
                case 3:

                    running = false;

                    System.out.println("Goodbye.");
                    break;

                // Invalid menu option
                default:

                    System.out.println(
                            "Invalid option selected."
                    );
            }
        }

        // Close scanner
        scanner.close();
    }
}
