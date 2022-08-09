package ru.gb;

import java.io.Serializable;

public enum RequestType implements Serializable {
    REG,
    AUTH,
    CHANGE_PASSWORD,
    LOGOUT,
    ADDCLIENT,
    REMOVECLIENT,
    SEND_TO_EVERYONE,
    PRIVATE_MESSAGE,
    CHANGE_NICK
}
