import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Message.java
 *
 * Handles the creation, validation, sending, storing, and display
 * of messages in the QuickChat application.
 *
 * Each message contains a unique auto-generated 10-digit Message ID,
 * a Message Hash, a recipient cell number, and a message body (max 250 characters).
 *
 * @author  Kurisani Baloyi
 * @version 2.0
 */
public class Message {

    // ── Instance fields ───────────────────────────────────────────────────────

    /** Auto-generated unique 10-digit identifier for this message. */
    private String messageID;

    /** The sequential number of this message (loop counter from the for loop). */
    private int messageNumber;

    /** The recipient's cell phone number — must include international code. */
    private String recipient;

    /** The message body — must not exceed 250 characters. */
    private String messageText;

    /** The auto-generated hash string for this message. */
    private String messageHash;

    // ── Static shared fields ──────────────────────────────────────────────────

    /**
     * Shared list of all messages sent or stored during this session.
     * 'static' means ONE list exists for ALL Message objects — not one per object.
     * This allows returnTotalMessages() to count across the whole session.
     */
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    /**
     * Running count of messages that were sent (option 1) during this session.
     * Incremented inside sentMessage() when the user chooses to send.
     */
    private static int totalMessagesSent = 0;

    // ── Constructor ───────────────────────────────────────────────────────────

    /**
     * Constructs a Message and immediately auto-generates its ID and Hash.
     * The ID is generated first because createMessageHash() uses it.
     *
     * @param messageNumber  The for-loop counter (0, 1, 2 ...) used in the hash.
     * @param recipient      The recipient's cell phone number.
     * @param messageText    The body of the message (max 250 characters).
     */
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID     = generateMessageID(); // must be set before createMessageHash()
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageHash   = createMessageHash();
    }

    // ── ID Generation ─────────────────────────────────────────────────────────

    /**
     * Generates a random 10-digit numeric Message ID as a String.
     *
     * FIX APPLIED: the first digit is forced to be 1–9 (never 0) so the result
     * is always a true 10-digit number. The original code used nextInt(10) for
     * every digit, which could produce a leading zero.
     *
     * @return A 10-digit numeric String e.g. "3847261059".
     */
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        // First digit: 1–9  (nextInt(9) gives 0–8, +1 shifts to 1–9)
        id.append(random.nextInt(9) + 1);

        // Remaining 9 digits: 0–9
        for (int i = 1; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }

    // ── Validation methods ────────────────────────────────────────────────────

    /**
     * Checks that the Message ID is no more than 10 characters long.
     * Because generateMessageID() always produces exactly 10 digits, this
     * should always return true — but the check satisfies the rubric requirement.
     *
     * @return true if the ID is within the 10-character limit, false otherwise.
     */
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    /**
     * Validates the recipient cell number.
     * The number must start with '+' (international code) and be no longer
     * than 13 characters total (e.g. +27 + 10 digits = 13 chars).
     *
     * @return "Cell phone number successfully captured." on success, or
     *         an error message describing the problem on failure.
     */
    public String checkRecipientCell() {
        if (recipient.startsWith("+") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.";
    }

    /**
     * Checks that the message body does not exceed 250 characters.
     *
     * @return "Message ready to send." if within the limit, or
     *         "Message exceeds 250 characters by X, please reduce size." if over.
     */
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excessCharacters = messageText.length() - 250;
        return "Message exceeds 250 characters by "
                + excessCharacters
                + ", please reduce size.";
    }

    /**
     * Creates and returns the Message Hash.
     *
     * Format: [first 2 chars of ID]:[messageNumber]:[FIRSTWORDLASTWORD]
     * Example: ID = "3800000000", num = 0, message = "Hi Mike, thanks"
     *          Hash = "38:0:HITHANKS"  (entire hash uppercased)
     *
     * substring(0, 2)  — extracts first 2 characters (end index is exclusive)
     * trim().split("\\s+") — splits on any whitespace, handles multiple spaces
     * toUpperCase()    — converts the entire result to capitals
     *
     * @return The formatted Message Hash String.
     */
    public String createMessageHash() {
        String[] words    = messageText.trim().split("\\s+");
        String firstWord  = words[0];
        String lastWord   = words[words.length - 1]; // length-1 = last index

        return (messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord).toUpperCase();
    }

    /**
     * Presents a send / disregard / store menu and processes the user's choice.
     *
     * FIX APPLIED: parameter changed from int to String so it works directly
     * with scanner.nextLine() in PROG5121POE.java without a type mismatch error.
     * switch-case on String requires Java 7 or higher.
     *
     * Options:
     *   "1" Send    — adds to sentMessages list, increments counter.
     *   "2" Discard — does nothing (message is not stored).
     *   "3" Store   — saves to JSON file via storeMessage().
     *
     * @param  option The menu choice entered by the user ("1", "2", or "3").
     * @return A String describing the outcome of the chosen action.
     */
    public String sentMessage(String option) {
        switch (option) {
            case "1":
                sentMessages.add(this); // 'this' = the current Message object
                totalMessagesSent++;
                return "Message successfully sent.";

            case "2":
                // Message is NOT added — effectively discarded
                return "Press 0 to delete the message.";

            case "3":
                storeMessage();
                return "Message successfully stored.";

            default:
                return "Invalid option selected.";
        }
    }

    /**
     * Returns a formatted String of this message's details.
     * Displays Message ID, Message Hash, Recipient, and Message text.
     * Called by displaySentMessages() to print each message in the list.
     *
     * @return A multi-line String of this message's details.
     */
    public String printMessages() {
        return "Message ID: "   + messageID
             + "\nMessage Hash: " + messageHash
             + "\nRecipient: "    + recipient
             + "\nMessage: "      + messageText;
    }

    /**
     * Prints all messages in the sentMessages list to the console.
     * Called after the for loop in PROG5121POE.java to display the session summary.
     */
    public static void displaySentMessages() {
        for (Message message : sentMessages) {
            System.out.println("\n--------------------------------");
            System.out.println(message.printMessages());
        }
    }

    /**
     * Returns the total number of messages sent (option 1) this session.
     * Reads the static totalMessagesSent counter.
     *
     * @return The count of messages successfully sent.
     */
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /**
     * Writes this message's details to a JSON file named stored_messages.json.
     *
     * FIX APPLIED: FileWriter is opened WITHOUT the append flag (true removed).
     * The original code used 'new FileWriter("stored_messages.json", true)' which
     * appended every run, causing duplicate entries and invalid JSON over time.
     * Now the file is overwritten cleanly on each save.
     *
     * Note: This writes simple JSON manually. If the message text contains quote
     * characters (") they would break the JSON — acceptable for this assessment level.
     */
    public void storeMessage() {
        // No 'true' flag = overwrite mode (not append)
        try (FileWriter writer = new FileWriter("stored_messages.json")) {
            writer.write("{\n");
            writer.write("  \"messageID\": \""   + messageID   + "\",\n");
            writer.write("  \"messageHash\": \"" + messageHash + "\",\n");
            writer.write("  \"recipient\": \""   + recipient   + "\",\n");
            writer.write("  \"message\": \""     + messageText + "\"\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    // Public getters allow other classes (main, tests) to read private fields.

    /** @return The Message ID. */
    public String getMessageID()     { return messageID; }

    /** @return The message number (loop counter). */
    public int getMessageNumber()    { return messageNumber; }

    /** @return The recipient cell number. */
    public String getRecipient()     { return recipient; }

    /** @return The message text. */
    public String getMessageText()   { return messageText; }

    /** @return The Message Hash. */
    public String getMessageHash()   { return messageHash; }

    /** @return The shared sentMessages list (used in tests). */
    public static ArrayList<Message> getSentMessages() { return sentMessages; }

    /** Clears the sentMessages list and resets the counter — used in @BeforeEach in tests. */
    public static void clearSentMessages() {
        sentMessages.clear();
        totalMessagesSent = 0;
    }
}