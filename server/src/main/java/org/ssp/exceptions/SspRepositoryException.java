package org.ssp.exceptions;

public class SspRepositoryException extends RuntimeException {

    public SspRepositoryException(SspException exception) {
        super(exception.getMessage());
    }

    public SspRepositoryException(SspException exception, Object... args) {
        super(exception.getMessage().formatted(args));
    }

    public SspRepositoryException(SspException exception, Exception cause, Object... args) {
        super(exception.getMessage().formatted(args), cause);
    }

}
