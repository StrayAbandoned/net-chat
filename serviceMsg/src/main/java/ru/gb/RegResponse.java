package ru.gb;

public class RegResponse implements Response{
    private ResponseType type;

    public RegResponse(ResponseType type){
        this.type = type;
    }
    @Override
    public ResponseType getType() {
        return type;
    }
}
