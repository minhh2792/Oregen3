package me.banbeucmas.oregen3.utils;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PluginUtils {
    public static OfflinePlayer getOwner(Location loc) {
        if(Oregen3.DEBUG){
            System.out.println("Begin getting Owner: ");
        }
        Set<Location> set = new HashSet<>();
        set.add(loc);

        UUID uuid = null;
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock")) {
            uuid = com.wasteofplastic.askyblock.ASkyBlockAPI.getInstance()
                    .getOwner(com.wasteofplastic.askyblock.ASkyBlockAPI.getInstance().locationIsOnIsland(set, loc));
        }else if(Bukkit.getServer().getPluginManager().isPluginEnabled("AcidIsland")) {
            uuid = com.wasteofplastic.acidisland.ASkyBlockAPI.getInstance()
                    .getOwner(com.wasteofplastic.acidisland.ASkyBlockAPI.getInstance().locationIsOnIsland(set, loc));
        }
        if(uuid == null){
            return null;
        }
        if(Oregen3.DEBUG){
            System.out.println("UUID: " + uuid);
        }
        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(uuid);


        return p;
    }

    public static MaterialChooser getChooser(Location loc){
        Oregen3 plugin = Oregen3.getPlugin();
        MaterialChooser mc = DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator"));
        if(plugin.getConfig().getBoolean("enableDependency")){
            Player p = (Player) PluginUtils.getOwner(loc);
            for(MaterialChooser chooser : DataManager.getChoosers().values()){
                if(p == null){
                    break;
                }
                if(p.hasPermission(chooser.getPermission())
                        && chooser.getPriority() >= mc.getPriority()
                        && getLevel(p.getUniqueId()) >= mc.getLevel()){
                    mc = chooser;
                }
            }
        }
        return mc;
    }

    public static int getLevel(UUID id){
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock")){
            return (int) com.wasteofplastic.askyblock.ASkyBlockAPI.getInstance().getLongIslandLevel(id);
        }
        else if(Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock")){
            return com.wasteofplastic.acidisland.ASkyBlockAPI.getInstance().getIslandLevel(id);
        }
        return 0;
    }

    public static Sound getCobbleSound(){
        if(Bukkit.getVersion().contains("1.8")){
            return Sound.valueOf("FIZZ");
        }
        return Sound.valueOf("BLOCK_FIRE_EXTINGUISH");
    }
}
