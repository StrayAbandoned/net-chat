package ru.gb;

public class ChangePasswordResponse implements Response{

    ResponseType type;

    public ChangePasswordResponse(ResponseType type) {
        this.type = type;
    }


    @Override
    public ResponseType getType() {
        return type;
    }
}
