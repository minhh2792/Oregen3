package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class DebugCommand extends AbstractCommand {
    public DebugCommand(final CommandSender sender) {
        super("oregen3.debug", sender);
    }

    @Override
    public ExecutionResult now() {
        Oregen3.DEBUG = !Oregen3.DEBUG;
        getSender().sendMessage(StringUtils.getPrefixString("&eToggled Debug"));
        return ExecutionResult.DONT_CARE;
    }
}
