package fr.cesi.meteo.exception;

public class GenericRuntimeException extends RuntimeException {

    public GenericRuntimeException(String message, Object... objects) {
        super(String.format(message, objects));
    }

}
