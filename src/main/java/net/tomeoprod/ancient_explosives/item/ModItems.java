package net.tomeoprod.ancient_explosives.item;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.item.custom.EchoAmplifierItem;
import net.tomeoprod.ancient_explosives.item.custom.EchoShardClusterItem;

import java.util.function.Function;

public class ModItems {
    public static final Item WARDEN_HEART = registerItem("warden_heart", Item::new, new Item.Settings().rarity(Rarity.EPIC));
    public static final Item WARDEN_CHESTPLATE = registerItem("warden_chestplate", settings -> new Item(settings.armor(WardenArmorMaterial.INSTANCE, EquipmentType.CHESTPLATE)), new Item.Settings());
    public static final Item ECHO_AMPLIFIER = registerItem("echo_amplifier", EchoAmplifierItem::new, new Item.Settings());
    public static final Item ECHO_SHARDS_CLUSTER = registerItem("echo_shard_cluster", EchoShardClusterItem::new,new Item.Settings().maxCount(16));

    private static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AncientExplosives.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void registerItems() {
        AncientExplosives.LOGGER.info("Registering Items");
    }
}
