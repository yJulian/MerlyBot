package de.yjulian.merly.subsystem.command;

import java.io.InputStream;
import java.io.Reader;

public interface AliasManager {

    /**
     * Load a alias list from input streams.
     * @param iss the input streams.
     */
    void loadAliasList(InputStream... iss);

    /**
     * Load a alias list with readers.
     * @param readers the readers.
     */
    void loadAliasList(Reader... readers);

    /**
     * Reset all aliases.
     */
    void resetAlias();

}
