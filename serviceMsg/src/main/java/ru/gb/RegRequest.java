package ru.gb;

public class RegRequest implements Request{
    private String login;
    private String password;
    private  String nick;
    private RequestType type;

    public RegRequest(String nick, String login, String password) {
        this.nick = nick;
        this.login = login;
        this.password = password;
        setType(RequestType.REG);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNick(){
        return nick;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    @Override
    public RequestType getType() {
        return RequestType.REG;
    }
}
