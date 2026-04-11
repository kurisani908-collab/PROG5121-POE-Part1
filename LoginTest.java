import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LoginTest.java
 *
 * Unit tests for the {@link Login} class.
 * Tests cover loginUser() and returnLoginStatus() for both
 * successful and failed login scenarios as specified in the PROG5121 POE rubric.
 *
 * @author  Kurisani Baloyi
 * @version 1.0
 */
public class LoginTest {

    // ── Test fixture ──────────────────────────────────────────────────────────

    /** Shared Registration object representing a valid registered user. */
    private Registration registeredUser;

    /**
     * Sets up a valid registered user before each test runs.
     * Uses credentials that pass all Registration validation checks.
     */
    @BeforeEach
    public void setUp() {
        registeredUser = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // loginUser() — assertTrue / assertFalse
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Test: Login successful.
     * Condition: Correct username and password entered.
     * Expected: The system returns true.
     */
    @Test
    @DisplayName("Login successful - correct credentials")
    public void testLoginSuccessful() {
        Login login = new Login(registeredUser, "kyl_1", "Ch&&sec@ke99!");
        assertTrue(login.loginUser(),
                "Expected TRUE: entered credentials match the registered user's credentials.");
    }

    /**
     * Test: Login failed.
     * Condition: Incorrect password entered.
     * Expected: The system returns false.
     */
    @Test
    @DisplayName("Login failed - incorrect password")
    public void testLoginFailed() {
        Login login = new Login(registeredUser, "kyl_1", "wrongPassword1!");
        assertFalse(login.loginUser(),
                "Expected FALSE: entered password does not match the stored password.");
    }

    /**
     * Test: Login failed with wrong username.
     * Condition: Incorrect username entered.
     * Expected: The system returns false.
     */
    @Test
    @DisplayName("Login failed - incorrect username")
    public void testLoginFailedWrongUsername() {
        Login login = new Login(registeredUser, "ab_1", "Ch&&sec@ke99!");
        assertFalse(login.loginUser(),
                "Expected FALSE: entered username does not match the stored username.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // returnLoginStatus() — message content assertions
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Test: Successful login returns a welcome message.
     * Expected: Message contains "welcome" and "great to see you".
     */
    @Test
    @DisplayName("returnLoginStatus returns welcome message on success")
    public void testReturnLoginStatusSuccessMessage() {
        Login login = new Login(registeredUser, "kyl_1", "Ch&&sec@ke99!");
        String statusMessage = login.returnLoginStatus();
        assertTrue(statusMessage.toLowerCase().contains("welcome"),
                "Expected 'welcome' in the success status message.");
        assertTrue(statusMessage.toLowerCase().contains("great to see you"),
                "Expected 'great to see you' in the success status message.");
    }

    /**
     * Test: Failed login returns an error message.
     * Expected: Message contains "incorrect".
     */
    @Test
    @DisplayName("returnLoginStatus returns error message on failure")
    public void testReturnLoginStatusFailedMessage() {
        Login login = new Login(registeredUser, "kyl_1", "wrongPassword1!");
        String statusMessage = login.returnLoginStatus();
        assertTrue(statusMessage.toLowerCase().contains("incorrect"),
                "Expected 'incorrect' in the failed login status message.");
    }
}