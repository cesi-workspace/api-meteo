package fr.cesi.meteo.exception;

public class ParameterNotFoundException extends GenericRuntimeException {

    public ParameterNotFoundException(String message, Object... objects) {
        super(message, objects);
    }

}
