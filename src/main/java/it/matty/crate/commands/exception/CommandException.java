package it.matty.crate.commands.exception;

public class CommandException extends RuntimeException {

    public CommandException(String msg) {
        super(msg);
    }
}
