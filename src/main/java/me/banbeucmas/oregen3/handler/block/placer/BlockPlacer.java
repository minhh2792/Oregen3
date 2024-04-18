package me.banbeucmas.oregen3.handler.block.placer;

import org.bukkit.block.Block;

public interface BlockPlacer {
    static BlockPlacer getBlockPlacer(String mat) {
        if (mat.startsWith("oraxen-")) {
            return new OraxenBlockPlacer(mat.substring(7));
        }
        if (mat.startsWith("itemsadder-")) {
            return new ItemsAdderBlockPlacer(mat.substring(11));
        } else {
            return new VanillaBlockPlacer(mat);
        }
    }

    void placeBlock(Block block);
}
