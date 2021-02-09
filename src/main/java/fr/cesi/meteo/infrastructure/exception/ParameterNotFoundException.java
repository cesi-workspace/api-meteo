package fr.cesi.meteo.infrastructure.exception;

public class ParameterNotFoundException extends GenericRuntimeException {

    public ParameterNotFoundException(String message, Object... objects) {
        super(message, objects);
    }

}
