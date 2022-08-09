package ru.gb;

public class PrivateMessageRequest implements Request{
    private String message;
    private String sender;
    private String receiver;
    private RequestType type = RequestType.PRIVATE_MESSAGE;

    public PrivateMessageRequest(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public RequestType getType() {
        return type;
    }
}
