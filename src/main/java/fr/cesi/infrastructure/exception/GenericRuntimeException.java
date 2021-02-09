package fr.cesi.infrastructure.exception;

public class GenericRuntimeException extends RuntimeException {

    public GenericRuntimeException(String message, Object... objects) {
        super(String.format(message, objects));
    }

}
