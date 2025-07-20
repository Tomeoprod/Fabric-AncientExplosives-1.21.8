package net.tomeoprod.ancient_explosives.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup ALL_TOMEOPRODS_SORCERY_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(AncientExplosives.MOD_ID, "ancient_explosives"), FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.WARDEN_HEART))
            .displayName(Text.translatable("itemgroup.tomeoprodssorcery.ancient_explosives"))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.WARDEN_HEART);
                entries.add(ModItems.WARDEN_CHESTPLATE);
                entries.add(ModItems.ECHO_AMPLIFIER);
                entries.add(ModBlocks.ECHO_CRYSTAL.asItem());
                entries.add(ModBlocks.ECHO_TNT.asItem());
                entries.add(ModItems.ECHO_SHARDS_CLUSTER);
            }).build());

    public static void registerItemGroups() {
    }
}
