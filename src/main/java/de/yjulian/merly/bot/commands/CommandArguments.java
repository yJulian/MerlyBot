package de.yjulian.merly.bot.commands;

class CommandArguments {

    private final String[] arguments;

    public CommandArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String getArgument(int index) {
        return arguments[index];
    }

}
