import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// Message class used for creating, validating, sending and storing messages
public class Message {

    // Message details
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Stores messages that were successfully sent
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    // Tracks total number of sent messages
    private static int totalMessagesSent = 0;

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }

    // Checks if the message ID is no more than 10 characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Checks if the recipient number has an international code
    public String checkRecipientCell() {
        if (recipient.startsWith("+") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Checks if the message is not more than 250 characters
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int excessCharacters = messageText.length() - 250;

        return "Message exceeds 250 characters by "
                + excessCharacters
                + ", please reduce size.";
    }

    // Creates the required message hash
    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord).toUpperCase();
    }

    // Allows the user to send, disregard or store a message
    public String sentMessage(int option) {
        switch (option) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                storeMessage();
                return "Message successfully stored.";

            default:
                return "Invalid option selected.";
        }
    }

    // Returns message details as text
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Displays all recently sent messages
    public static void displaySentMessages() {
        for (Message message : sentMessages) {
            System.out.println("\n--------------------------------");
            System.out.println(message.printMessages());
        }
    }

    // Returns total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Stores message details in a JSON file
    public void storeMessage() {
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            writer.write("{\n");
            writer.write("\"messageID\": \"" + messageID + "\",\n");
            writer.write("\"messageHash\": \"" + messageHash + "\",\n");
            writer.write("\"recipient\": \"" + recipient + "\",\n");
            writer.write("\"message\": \"" + messageText + "\"\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }
}
