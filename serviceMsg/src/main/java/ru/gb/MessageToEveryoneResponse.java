package ru.gb;

public class MessageToEveryoneResponse implements Response{
    private String message;
    private ResponseType type = ResponseType.MESSAGETOEVERYONE;


    public MessageToEveryoneResponse(String message) {
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
