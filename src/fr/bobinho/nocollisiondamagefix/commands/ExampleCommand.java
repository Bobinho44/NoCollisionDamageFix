package fr.bobinho.nocollisiondamagefix.commands;

import co.aikar.commands.annotation.*;
import fr.bobinho.nocollisiondamagefix.api.command.BCommand;
import org.bukkit.entity.Player;

/**
 * Command of example
 */
@CommandAlias("example")
public final class ExampleCommand extends BCommand {

    /**
     * Command example help
     */
    @Default
    @Syntax("/example help")
    @Subcommand("help")
    @CommandPermission("example.help")
    @Description("Gets the example command help.")
    public void onCommandExampleHelp(Player sender) {
        sendCommandHelp(this.getClass(), sender, "example");
    }

}
