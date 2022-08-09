package ru.gb;

public class ChangeNickResponse implements Response{

    ResponseType type;

    public ChangeNickResponse(ResponseType type) {
        this.type = type;
    }


    @Override
    public ResponseType getType() {
        return type;
    }
}
