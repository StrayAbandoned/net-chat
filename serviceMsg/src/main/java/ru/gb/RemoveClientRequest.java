package ru.gb;

public class RemoveClientRequest  implements Request{
    private String nick;
    private RequestType type = RequestType.REMOVECLIENT;


    public RemoveClientRequest(String nick) {
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
