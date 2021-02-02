package de.yjulian.merly.exceptions;

public class CommandException extends RuntimeException {

    private final String publicMessage;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CommandException() {
        this.publicMessage = null;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CommandException(String message) {
        super(message);
        this.publicMessage = null;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param publicMessage the message that can be shown in the discord
     *                channel.
     */
    public CommandException(String message, String publicMessage) {
        super(message);
        this.publicMessage = publicMessage;
    }

    /**
     * Get the message that can be shown in the discord chat.
     *
     * @return a string.
     */
    public String getPublicMessage() {
        return publicMessage;
    }

    /**
     * Returns true if the thrown exception has a message that should be displayed to the user.
     *
     * @return a boolean.
     */
    public boolean hasPublicMessage() {
        return publicMessage != null;
    }

}
