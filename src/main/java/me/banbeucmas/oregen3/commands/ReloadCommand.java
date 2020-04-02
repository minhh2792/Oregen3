package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends AbstractCommand {
    ReloadCommand(final CommandSender sender) {
        super("oregen3.reload", sender);
    }

    @Override
    public ExecutionResult now() {
        if (!getSender().hasPermission(getPermission())) {
            //This is a method which was uneccessary on the old file but decided to keep it as to honoring the old author
            //Link: https://imgur.com/XAXJppv
            //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add *");
            return ExecutionResult.NO_PERMISSION;
        }

        final Oregen3 plugin = getPlugin();
        plugin.reloadConfig();
        DataManager.loadData();

        getSender().sendMessage(StringUtils.getPrefixString("Config reloaded"));


        return ExecutionResult.DONT_CARE;
    }
}
