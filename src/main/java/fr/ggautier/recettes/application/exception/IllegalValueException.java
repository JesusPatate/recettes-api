package fr.ggautier.recettes.application.exception;

/**
 * This exception is thrown when one try to access a value whose type is not the one expected.
 */
public class IllegalValueException extends RuntimeException {

    /**
     * Instantiates a new exception.
     *
     * @param message
     *         Details of the error
     *
     * @param cause
     *         The cause of the error
     */
    public IllegalValueException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
