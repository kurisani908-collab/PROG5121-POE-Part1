import java.util.Scanner;

/**
 * PROG5121POE.java
 *
 * Entry point for the PROG5121 POE Registration and Login application.
 * Validates each field immediately after input and re-prompts on failure.
 *
 * @author  Kurisani Baloyi
 * @version 1.0
 */
public class PROG5121POE {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== PROG5121 POE — Registration & Login ===\n");

        // ── Step 1: Registration ─────────────────────────────────────────────
        System.out.println("--- REGISTRATION ---");

        // Username: keep asking until valid
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            Registration usernameCheck = new Registration(username, "Placeholder1!", "+271234567");
            if (usernameCheck.checkUserName()) {
                System.out.println("Username successfully captured.\n");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that "
                        + "your username contains an underscore and is no more than five "
                        + "characters in length.\n");
            }
        }

        // Password: keep asking until valid
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            Registration passwordCheck = new Registration(username, password, "+271234567");
            if (passwordCheck.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.\n");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that "
                        + "the password contains at least eight characters, a capital letter, "
                        + "a number, and a special character.\n");
            }
        }

        // Cell phone: keep asking until valid
        String cellPhoneNumber;
        while (true) {
            System.out.print("Enter cell phone number (e.g. +27838968976): ");
            cellPhoneNumber = scanner.nextLine();
            Registration cellCheck = new Registration(username, password, cellPhoneNumber);
            if (cellCheck.checkCellPhoneNumber()) {
                System.out.println("Cell number successfully captured.\n");
                break;
            } else {
                System.out.println("Cell number is incorrectly formatted or does not contain "
                        + "an international code; please correct the number and try again.\n");
            }
        }

        System.out.println("Registration successful!\n");

        // ── Step 2: Login ────────────────────────────────────────────────────
        System.out.println("--- LOGIN ---");

        Registration registration = new Registration(username, password, cellPhoneNumber);

        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();

        Login login = new Login(registration, enteredUsername, enteredPassword);
        System.out.println("\n" + login.returnLoginStatus());

        scanner.close();
    }
}