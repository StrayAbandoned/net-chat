package ru.gb;

import java.nio.file.Path;

public class AuthResponse implements Response{
    private ResponseType type;
    String nick;

    public AuthResponse(ResponseType type, String nick) {
        this.nick = nick;
        this.type = type;
    }

    public AuthResponse(ResponseType type) {
        this.type = type;
    }


    public String getNick() {
        return nick;
    }

    @Override
    public ResponseType getType() {
        return type;
    }
}
