package ru.gb;

public class SendToEveryoneRequest implements Request{
    private String message;
    private String nick;
    private RequestType type = RequestType.SEND_TO_EVERYONE;


    public SendToEveryoneRequest(String nick,String message) {
        this.message = message;
        this.nick = nick;

    }

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public RequestType getType() {
        return type;
    }

}
