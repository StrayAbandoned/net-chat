package ru.gb;

public class ChangePasswordRequest implements Request{
    private String nick;
    private String password;
    private RequestType type = RequestType.CHANGE_PASSWORD;

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public ChangePasswordRequest(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    @Override
    public RequestType getType() {
        return type;
    }

}
