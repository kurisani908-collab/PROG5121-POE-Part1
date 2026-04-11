/**
 * Login.java
 *
 * Handles user authentication for the PROG5121 POE application.
 * Verifies that the credentials supplied at login match those captured
 * during registration, and returns appropriate status messages.
 *
 * @author  Kurisani Baloyi
 * @version 1.0
 */
public class Login {

    // ── Instance fields ───────────────────────────────────────────────────────

    /** The Registration object containing the stored credentials. */
    private Registration registeredUser;

    /** The username supplied at the login attempt. */
    private String enteredUsername;

    /** The password supplied at the login attempt. */
    private String enteredPassword;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs a Login object with the registered user's data
     * and the credentials entered during the login attempt.
     *
     * @param registeredUser  The Registration object holding stored credentials.
     * @param enteredUsername The username entered by the user trying to log in.
     * @param enteredPassword The password entered by the user trying to log in.
     */
    public Login(Registration registeredUser, String enteredUsername, String enteredPassword) {
        this.registeredUser  = registeredUser;
        this.enteredUsername = enteredUsername;
        this.enteredPassword = enteredPassword;
    }

    // ── Authentication methods ────────────────────────────────────────────────

    /**
     * Verifies that the login details entered by the user match the details
     * stored during registration. Uses a decision structure to compare both
     * the username and password simultaneously.
     *
     * @return {@code true} if both username and password match the stored
     *         credentials, {@code false} otherwise.
     */
    public boolean loginUser() {
        boolean usernameMatches = registeredUser.getUsername().equals(enteredUsername);
        boolean passwordMatches = registeredUser.getPassword().equals(enteredPassword);

        // Decision structure: both fields must match for a successful login
        if (usernameMatches && passwordMatches) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the appropriate status message based on the outcome of
     * {@link #loginUser()}.
     *
     * <ul>
     *   <li>Successful login: welcome message including the user's first and last name.</li>
     *   <li>Failed login: error message prompting the user to try again.</li>
     * </ul>
     *
     * The username is split on the underscore to derive a first name and last name.
     * Example: username {@code "kyl_1"} yields first name {@code "kyl"} and
     * identifier {@code "1"}.
     *
     * @return A {@code String} status message for the login attempt.
     */
    public String returnLoginStatus() {
        if (loginUser()) {
            // Split username on underscore to extract name components
            String[] nameParts = enteredUsername.split("_");
            String firstName   = nameParts.length > 0 ? nameParts[0] : enteredUsername;
            String lastName    = nameParts.length > 1 ? nameParts[1] : "";

            return "Welcome " + firstName + " " + lastName + ", it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}