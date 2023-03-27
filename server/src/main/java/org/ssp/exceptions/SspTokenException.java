package org.ssp.exceptions;

public class SspTokenException extends RuntimeException {

    public SspTokenException(SspException exception, Exception cause, Object... args) {
        super(exception.getMessage().formatted(args), cause);
    }
}
