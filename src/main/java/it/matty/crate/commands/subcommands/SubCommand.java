package it.matty.crate.commands.subcommands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class SubCommand implements ISubCommand {
    private final String subcommand;
}
