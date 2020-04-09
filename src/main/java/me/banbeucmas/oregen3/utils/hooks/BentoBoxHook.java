package me.banbeucmas.oregen3.utils.hooks;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.addons.GameModeAddon;
import world.bentobox.bentobox.api.addons.request.AddonRequestBuilder;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.AddonsManager;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BentoBoxHook implements SkyblockHook {
    private World world;
    private GameModeAddon addon;
    private final IslandsManager manager;
    private boolean level = true;

    public BentoBoxHook() {
        manager = BentoBox.getInstance().getIslands();

        if (!BentoBox.getInstance().getAddonsManager().getAddonByName("Level").isPresent()) {
            Oregen3.getPlugin().getLogger().warning(StringUtils.getPrefixString("Level addon for BentoBox not found! Turning island level feature off...", null));
            level = false;
        }

        final AddonsManager manager = BentoBox.getInstance().getAddonsManager();
        final List<GameModeAddon> addonList = manager.getGameModeAddons();
        if (addonList.isEmpty()) {
            Oregen3.getPlugin().getLogger().severe(StringUtils.getPrefixString("Couldn't find a gamemode for BentoBox! Disabling plugin...", null));
            Oregen3.getPlugin().getPluginLoader().disablePlugin(Oregen3.getPlugin());
            return;
        }

        for (final String name : Oregen3.getPlugin().getConfig().getStringList(StringUtils.getPrefixString("hooks.BentoBox.gamemodePriorities", null))) {
            final Optional<Addon> gamemode = manager.getAddonByName(name);
            if (!gamemode.isPresent() || !(gamemode.get() instanceof GameModeAddon)) continue;
            final GameModeAddon gamemodeAddon = (GameModeAddon) gamemode.get();
            if (addonList.contains(gamemodeAddon)) {
                addon = gamemodeAddon;
            }
        }
        if (addon == null) {
            Oregen3.getPlugin().getServer().getConsoleSender().sendMessage(StringUtils.getPrefixString("Couldn't find any gamemode hook list in config, trying to grab the first one it can find...", null));
            addon = addonList.get(0);
        }
        world = addon.getOverWorld();
    }

    @Override
    public long getIslandLevel(final UUID uuid, final Location loc) {
        final World world;
        if (level) {
            if (loc != null) {
                world = loc.getWorld();
            }
            else {
                world = this.world;
            }
            return (Long) new AddonRequestBuilder()
                    .addon("Level")
                    .label("island-level")
                    .addMetaData("world-name", world)
                    .addMetaData("player", uuid)
                    .request();
        }
        else
            return 0;
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final Optional<Island> island = manager.getIslandAt(loc);
        return island.map(Island::getOwner).orElse(null);
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return manager.getOwner(world, uuid);
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return manager.getIslandAt(loc).isPresent();
    }
}
