package me.banbeucmas.oregen3.utils.hooks;

import com.wasteofplastic.acidisland.ASkyBlockAPI;
import org.bukkit.Location;

import java.util.UUID;

public class AcidIslandHook implements SkyblockHook {
    private final ASkyBlockAPI api;

    public AcidIslandHook() {
        api = ASkyBlockAPI.getInstance();
    }

    @Override
    public long getIslandLevel(final UUID uuid) {
        return api.getIslandLevel(uuid);
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return api.getIslandAt(loc).getOwner();
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return api.getIslandAt(loc) != null;
    }
}