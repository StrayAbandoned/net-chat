package ru.gb;

import java.io.Serializable;

public interface Request extends Serializable {
    RequestType getType();
}
