package example.chatapp;

/**
 * @author Andrew Messing
 */
public class Message {

    public String sender;
    public String message;
    public long timestamp;

    public Message()
    {

    }

    public Message(String sender, String message)
    {
        this.sender = sender;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Message(String sender, String message, long timestamp)
    {
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

}
