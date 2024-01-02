package s2.proyecto.exception;


public class CredencialesErroneasException extends RuntimeException {

    public CredencialesErroneasException(String message) {
        super(message);
    }

    public CredencialesErroneasException(String message, Throwable cause) {
        super(message, cause);
    }
}

