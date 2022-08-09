package ru.gb;

public class ChangeNickRequest implements Request{
    private String nick, newNick;
    private RequestType type = RequestType.CHANGE_NICK;

    public String getNick() {
        return nick;
    }

    public String getNewNick() {
        return newNick;
    }

    public ChangeNickRequest(String nick, String newNick) {
        this.nick = nick;
        this.newNick = newNick;

    }

    @Override
    public RequestType getType() {
        return type;
    }
}
