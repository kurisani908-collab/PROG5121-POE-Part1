/**
 * Registration.java
 *
 * Handles user account creation for the PROG5121 POE application.
 * Validates username format, password complexity, and South African
 * cell phone number using a regex-based checker.
 *
 * Cell phone regex pattern adapted from:
 * Friedl, J. E. F. (2006). Mastering Regular Expressions (3rd ed.).
 * O'Reilly Media. https://www.oreilly.com/library/view/mastering-regular-expressions/0596528124/
 *
 * @author  Kurisani Baloyi
 * @version 1.0
 */
public class Registration {

    // ── Instance fields ───────────────────────────────────────────────────────

    /** The username chosen by the registering user. */
    private String username;

    /** The password chosen by the registering user. */
    private String password;

    /** The South African cell phone number of the registering user. */
    private String cellPhoneNumber;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs a Registration object with the supplied credentials.
     *
     * @param username        The desired username.
     * @param password        The desired password.
     * @param cellPhoneNumber The user's cell phone number (international format).
     */
    public Registration(String username, String password, String cellPhoneNumber) {
        this.username        = username;
        this.password        = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // ── Validation methods ────────────────────────────────────────────────────

    /**
     * Checks that the username contains an underscore (_)
     * and is no more than five characters long.
     *
     * @return {@code true} if the username is correctly formatted,
     *         {@code false} otherwise.
     */
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }
        boolean containsUnderscore = username.contains("_");
        boolean isCorrectLength    = username.length() <= 5;
        return containsUnderscore && isCorrectLength;
    }

    /**
     * Checks that the password meets the following complexity requirements:
     * <ul>
     *   <li>At least eight characters long.</li>
     *   <li>Contains at least one capital letter.</li>
     *   <li>Contains at least one digit.</li>
     *   <li>Contains at least one special character.</li>
     * </ul>
     *
     * @return {@code true} if the password meets all complexity rules,
     *         {@code false} otherwise.
     */
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasDigit   = false;
        boolean hasSpecial = false;

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasDigit && hasSpecial;
    }

    /**
     * Validates the cell phone number using a regular expression.
     *
     * The number must:
     * <ul>
     *   <li>Begin with '+' followed by the international country code.</li>
     *   <li>Contain no more than 12 digits in total after the '+' symbol,
     *       accommodating country codes (e.g. +27) plus a 9-digit SA number.</li>
     * </ul>
     *
     * Regex pattern used: {@code ^\+\d{7,12}$}
     * <ul>
     *   <li>{@code ^}       – asserts start of string</li>
     *   <li>{@code \+}      – matches literal '+' (international dialling prefix)</li>
     *   <li>{@code \d{7,12}}– matches 7 to 12 digits (covers all valid international formats)</li>
     *   <li>{@code $}       – asserts end of string</li>
     * </ul>
     *
     * Example valid input: +27789628127 ('+27' country code + 9-digit number = 11 digits)
     *
     * Regex reference:
     * Friedl, J. E. F. (2006). Mastering Regular Expressions (3rd ed.).
     * O'Reilly Media.
     * https://www.oreilly.com/library/view/mastering-regular-expressions/0596528124/
     *
     * @return {@code true} if the cell phone number is correctly formatted,
     *         {@code false} otherwise.
     */
    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }
        // Regex: '+' followed by 7 to 12 digits — covers SA (+27) and other international formats
        String cellPhoneRegex = "^\\+\\d{7,12}$";
        return cellPhoneNumber.matches(cellPhoneRegex);
    }

    /**
     * Attempts to register the user by validating all fields in sequence.
     * Returns a descriptive message for the first validation failure encountered,
     * or a full success message when all validations pass.
     *
     * @return A {@code String} message indicating the registration outcome.
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, "
                    + "and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international "
                    + "code; please correct the number and try again.";
        }

        return "Registration successful!\n"
                + "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell number successfully captured.";
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** @return The registered username. */
    public String getUsername()        { return username; }

    /** @return The registered password. */
    public String getPassword()        { return password; }

    /** @return The registered cell phone number. */
    public String getCellPhoneNumber() { return cellPhoneNumber; }
}