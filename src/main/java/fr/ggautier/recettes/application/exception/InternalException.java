package fr.ggautier.recettes.application.exception;

/**
 * Exception thrown when an internal error occurred.
 */
public class InternalException extends RuntimeException {

    /**
     * Instantiates a new exception.
     *
     * @param message
     *         Details of the error
     */
    public InternalException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new exception.
     *
     * @param message
     *         Details of the error
     *
     * @param cause
     *         The cause of the error
     */
    public InternalException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
