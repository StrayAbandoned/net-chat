package ru.gb;

import java.util.concurrent.CopyOnWriteArrayList;

public class BroadcastClientListResponse implements Response{
    CopyOnWriteArrayList<String> clients;
    ResponseType type = ResponseType.BROADCAST_CLIENT_LIST;

    public BroadcastClientListResponse(CopyOnWriteArrayList<String> clients) {
        this.clients = clients;
    }

    public CopyOnWriteArrayList<String> getClients() {
        return clients;
    }

    @Override
    public ResponseType getType() {
        return type;
    }
}
