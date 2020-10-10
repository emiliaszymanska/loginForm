package org.example.exceptions;

import java.sql.SQLException;

public class ObjectNotFoundException extends SQLException {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
