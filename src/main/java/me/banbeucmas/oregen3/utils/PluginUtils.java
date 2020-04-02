package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

import java.util.UUID;

import static me.banbeucmas.oregen3.Oregen3.getHook;

public class PluginUtils {
    public static OfflinePlayer getOwner(final Location loc) {
        if (Oregen3.DEBUG) {
            System.out.println("Begin getting Owner: ");
        }

        if (!getHook().isOnIsland(loc)) {
            return null;
        }

        final UUID uuid = getHook().getIslandOwner(loc);
        if (uuid == null) {
            return null;
        }

        if (Oregen3.DEBUG) {
            System.out.println("UUID: " + uuid);
        }

        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    public static MaterialChooser getChooser(final Location loc) {
        final Oregen3 plugin = Oregen3.getPlugin();
        MaterialChooser mc = DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator"));
        if (plugin.getConfig().getBoolean("enableDependency")) {
            final Entity p = (Entity) PluginUtils.getOwner(loc);
            if (p == null) {
                return mc;
            }
            for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
                if (p.hasPermission(chooser.getPermission())
                        && chooser.getPriority() >= mc.getPriority()
                        && getLevel(p.getUniqueId()) >= chooser.getLevel()) {
                    mc = chooser;
                }
            }
        }
        return mc;
    }

    private static long getLevel(final UUID id) {
        return Oregen3.getHook().getIslandLevel(id);
    }

    public static Sound getCobbleSound() {
        return Sound.BLOCK_FIRE_EXTINGUISH;
    }
}
