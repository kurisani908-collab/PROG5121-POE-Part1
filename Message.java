import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// Message class used for creating, validating, sending and storing messages
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }

        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.startsWith("+") && recipient.length() <= 13) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int excessCharacters = messageText.length() - 250;

        return "Message exceeds 250 characters by "
                + excessCharacters
                + ", please reduce size.";
    }

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

    // Full message details shown after sending
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Number: " + messageNumber
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Stores message details in a JSON file
    public void storeMessage() {
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            writer.write("{\n");
            writer.write("\"messageID\": \"" + messageID + "\",\n");
            writer.write("\"messageNumber\": \"" + messageNumber + "\",\n");
            writer.write("\"messageHash\": \"" + messageHash + "\",\n");
            writer.write("\"recipient\": \"" + recipient + "\",\n");
            writer.write("\"message\": \"" + messageText + "\"\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public String getMessageID() {
        return messageID;
    }

    public static void clearSentMessages() {
        sentMessages.clear();
        totalMessagesSent = 0;
    }
}