package net.tomeoprod.ancient_explosives.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class ModTags {
    public static class Blocks {
        //public static final TagKey<Block> <TAG NAME> = createTag("<TAG ID>");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(AncientExplosives.MOD_ID, name));
        }
    }

    public static class Items {
        //public static final TagKey<Item> <TAG NAME> = createTag("<TAG ID>");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(AncientExplosives.MOD_ID, name));
        }
    }
}
