package ru.gb;

public class PrivateMessageResponse implements Response{
    private String message;
    private ResponseType type = ResponseType.PRIVATE_MESSAGE;

    public PrivateMessageResponse(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    @Override
    public ResponseType getType() {
        return type;
    }
}
