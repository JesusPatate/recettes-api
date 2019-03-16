package fr.ggautier.recettes.spi.exceptions;

public class InternalErrorException extends RuntimeException {

    public InternalErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
