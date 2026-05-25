import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * RegistrationTest.java
 *
 * Unit tests
 * Tests cover all validation methods and the registerUser() message output.
 *
 * @author  Kurisani Baloyi
 * @version 1.0
 */
public class RegistrationTest {

    // ─────────────────────────────────────────────────────────────────────────
    // checkUserName() — assertEquals / assertTrue / assertFalse
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Test: Username is correctly formatted.
     * Test Data: "kyl_1"
     * Expected: The system returns true (contains underscore, length = 5).
     */
    @Test
    @DisplayName("Username correctly formatted - kyl_1")
    public void testUsernameCorrectlyFormatted() {
        Registration registration = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(registration.checkUserName(),
                "Expected TRUE: 'kyl_1' contains underscore and is no more than 5 characters.");
    }

    /**
     * Test: Username incorrectly formatted.
     * Test Data: "kyle!!!!!!!"
     * Expected: The system returns false (no underscore, length > 5).
     */
    @Test
    @DisplayName("Username incorrectly formatted - kyle!!!!!!!")
    public void testUsernameIncorrectlyFormatted() {
        Registration registration = new Registration("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(registration.checkUserName(),
                "Expected FALSE: 'kyle!!!!!!!' has no underscore and exceeds 5 characters.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // checkPasswordComplexity() — assertTrue / assertFalse
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Test: Password meets complexity requirements.
     * Test Data: "Ch&&sec@ke99!"
     * Expected: The system returns true.
     */
    @Test
    @DisplayName("Password meets complexity - Ch&&sec@ke99!")
    public void testPasswordMeetsComplexityRequirements() {
        Registration registration = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(registration.checkPasswordComplexity(),
                "Expected TRUE: 'Ch&&sec@ke99!' satisfies all complexity rules.");
    }

    /**
     * Test: Password does NOT meet complexity requirements.
     * Test Data: "password"
     * Expected: The system returns false.
     */
    @Test
    @DisplayName("Password does not meet complexity - password")
    public void testPasswordDoesNotMeetComplexityRequirements() {
        Registration registration = new Registration("kyl_1", "password", "+27838968976");
        assertFalse(registration.checkPasswordComplexity(),
                "Expected FALSE: 'password' has no capital letter, digit, or special character.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // checkCellPhoneNumber() — assertTrue / assertFalse
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Test: Cell phone number correctly formatted (international code present).
     * Test Data: "+27838968976"
     * Expected: The system returns true.
     */
    @Test
    @DisplayName("Cell phone correctly formatted - +27838968976")
    public void testCellPhoneNumberCorrectlyFormatted() {
        Registration registration = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(registration.checkCellPhoneNumber(),
                "Expected TRUE: '+27838968976' begins with '+' and is within 10 digits.");
    }

    /**
     * Test: Cell phone number incorrectly formatted (no international code).
     * Test Data: "08966553"
     * Expected: The system returns false.
     */
    @Test
    @DisplayName("Cell phone incorrectly formatted - 08966553")
    public void testCellPhoneNumberIncorrectlyFormatted() {
        Registration registration = new Registration("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(registration.checkCellPhoneNumber(),
                "Expected FALSE: '08966553' does not contain an international code.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // registerUser() — message content assertions
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Test: All fields valid → registration succeeds.
     * Expected: Message contains "successful".
     */
    @Test
    @DisplayName("registerUser returns success message when all fields valid")
    public void testRegisterUserSuccessMessage() {
        Registration registration = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        String result = registration.registerUser();
        assertTrue(result.toLowerCase().contains("successful"),
                "Expected success message when all registration fields are valid.");
    }

    /**
     * Test: Invalid username → registration fails with username message.
     */
    @Test
    @DisplayName("registerUser returns username error for bad username")
    public void testRegisterUserUsernameErrorMessage() {
        Registration registration = new Registration("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        String result = registration.registerUser();
        assertTrue(result.toLowerCase().contains("username"),
                "Expected a message referencing 'username' when username is invalid.");
    }

    /**
     * Test: Invalid password → registration fails with password message.
     */
    @Test
    @DisplayName("registerUser returns password error for bad password")
    public void testRegisterUserPasswordErrorMessage() {
        Registration registration = new Registration("kyl_1", "password", "+27838968976");
        String result = registration.registerUser();
        assertTrue(result.toLowerCase().contains("password"),
                "Expected a message referencing 'password' when password is invalid.");
    }

    /**
     * Test: Invalid cell number → registration fails with cell number message.
     */
    @Test
    @DisplayName("registerUser returns cell number error for bad cell number")
    public void testRegisterUserCellNumberErrorMessage() {
        Registration registration = new Registration("kyl_1", "Ch&&sec@ke99!", "08966553");
        String result = registration.registerUser();
        assertTrue(result.toLowerCase().contains("cell"),
                "Expected a message referencing 'cell' when cell number is invalid.");
    }
}
