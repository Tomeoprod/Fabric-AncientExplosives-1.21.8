package net.tomeoprod.ancient_explosives.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.block.custom.EchoTntBlock;

import java.util.function.Function;

public class ModBlocks {
    public static final Block ECHO_CRYSTAL = register("echo_crystal", Block::new, AbstractBlock.Settings.create().requiresTool().strength(1.5f).sounds(BlockSoundGroup.AMETHYST_BLOCK), true);
    public static final Block ECHO_TNT = register("echo_tnt", EchoTntBlock::new, AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS).breakInstantly(), true);

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AncientExplosives.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AncientExplosives.MOD_ID, name));
    }

    public static void registerModBlocks() {}
}
