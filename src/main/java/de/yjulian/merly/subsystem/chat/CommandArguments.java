package de.yjulian.merly.subsystem.chat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CommandArguments implements Iterator<String> {

    private final String[] arguments;
    private final Iterator<String> iterator;

    /**
     * Constructor to initialize a new object instance.
     *
     * @param arguments the string[] with the arguments.
     */
    private CommandArguments(String[] arguments) {
        this.arguments = arguments;
        this.iterator = Arrays.stream(this.arguments).iterator();
    }

    /**
     * Parse a string array to a new object instance.
     *
     * @param evalMessage the message arguments with the command at index 0.
     * @return a new instance.
     */
    static CommandArguments parseArguments(String[] evalMessage) {
        String[] arguments = new String[0];
        System.arraycopy(evalMessage, 1, arguments, 0, evalMessage.length - 1);

        return new CommandArguments(arguments);
    }

    /**
     * Get the count of arguments.
     *
     * @return a positive integer or 0.
     */
    public int count() {
        return arguments.length;
    }

    /**
     * Get the argument at a specified index.
     *
     * @param index the index
     * @return the value
     * @throws IndexOutOfBoundsException when index is out of bounds.
     */
    public String getArgument(int index) {
        return arguments[index];
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public String next() {
        return iterator.next();
    }
}
