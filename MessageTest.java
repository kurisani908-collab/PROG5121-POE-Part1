import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

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

    @Test
    public void testMessageLengthFailure() {

        String longMessage = "A".repeat(260);

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

    @Test
    public void testMessageIDCreated() {

        Message message =
                new Message(
                        1,
                        "+27718693002",
                        "Hello there"
                );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testSendMessage() {

        Message.clearSentMessages();

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