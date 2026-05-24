import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit test class
public class MessageTest {

    // Test valid message length
    @Test
    public void testMessageLengthSuccess() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hi Mike, can you join us for dinner tonight?"
                );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    // Test invalid message length
    @Test
    public void testMessageLengthFailure() {

        String longMessage =
                "A".repeat(260);

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        longMessage
                );

        assertEquals(
                "Message exceeds 250 characters by 10, please reduce size.",
                message.checkMessageLength()
        );
    }

    // Test recipient success
    @Test
    public void testRecipientSuccess() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hello there"
                );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    // Test recipient failure
    @Test
    public void testRecipientFailure() {

        Message message =
                new Message(
                        1,
                        "0718693002",
                        "Hello there"
                );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    // Test message ID
    @Test
    public void testMessageIDCreated() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hello there"
                );

        assertTrue(
                message.checkMessageID()
        );
    }

    // Test send message
    @Test
    public void testSentMessage() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hello there"
                );

        assertEquals(
                "Message successfully sent.",
                message.sentMessage(1)
        );
    }

    // Test disregard message
    @Test
    public void testDisregardMessage() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hello there"
                );

        assertEquals(
                "Press 0 to delete the message.",
                message.sentMessage(2)
        );
    }

    // Test store message
    @Test
    public void testStoreMessage() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hello there"
                );

        assertEquals(
                "Message successfully stored.",
                message.sentMessage(3)
        );
    }
}