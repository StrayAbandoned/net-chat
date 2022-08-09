package ru.gb;

public class AddClientRequest implements Request{
    private String nick;
    private RequestType type = RequestType.ADDCLIENT;


    public AddClientRequest(String nick) {
        this.nick = nick;

    }

    public String getNick() {
        return nick;
    }

    @Override
    public RequestType getType() {
        return type;
    }
}
