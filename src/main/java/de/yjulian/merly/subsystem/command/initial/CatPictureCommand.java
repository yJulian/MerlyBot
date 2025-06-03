package de.yjulian.merly.subsystem.command.initial;

import de.yjulian.merly.subsystem.command.CommandArguments;
import de.yjulian.merly.subsystem.command.GenericCommand;
import de.yjulian.merly.subsystem.command.Help;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Command that sends a random cat picture to the invoking channel.
 */
public class CatPictureCommand implements GenericCommand {

    private static final String API_URL = "https://cataas.com/cat";

    @Override
    public String prefix() {
        return "cat";
    }

    @Override
    public void onExecute(CommandArguments arguments) {
        try (InputStream is = new URL(API_URL).openStream()) {
            arguments.getMessageChannel().sendFile(is, "cat.jpg").queue();
        } catch (IOException e) {
            arguments.getMessageChannel().sendMessage("Could not load cat image.").queue();
        }
    }

    @Override
    public Help helpProvider() {
        return Help.Builder("Sends a random cat picture.").build();
    }
}
