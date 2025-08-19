package net.tomeoprod.ancient_explosives.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.item.custom.EchoAmplifierItem;
import net.tomeoprod.ancient_explosives.item.custom.EchoShardClusterItem;
import net.tomeoprod.ancient_explosives.item.custom.GlowingShardClusterItem;

import javax.xml.crypto.Data;
import java.util.function.Function;

public class ModItems {
    public static final Item WARDEN_HEART = registerItem("warden_heart", Item::new, new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
    public static final Item WARDEN_CHESTPLATE = registerItem("warden_chestplate", settings -> new Item(settings.armor(WardenArmorMaterial.INSTANCE, EquipmentType.CHESTPLATE)), new Item.Settings().maxCount(1));
    public static final Item ECHO_AMPLIFIER = registerItem("echo_amplifier", EchoAmplifierItem::new, new Item.Settings().maxCount(1));
    public static final Item ECHO_SHARDS_CLUSTER = registerItem("echo_shard_cluster", EchoShardClusterItem::new,new Item.Settings().maxCount(16));
    public static final Item GLOWING_SHARDS_CLUSTER = registerItem("glowing_shard_cluster", GlowingShardClusterItem::new,new Item.Settings().maxCount(16));
    public static final Item GLOWING_SHARD = registerItem("glowing_shard", Item::new, new Item.Settings());
    public static final Item GOGGLES = registerItem("goggles", Item::new, new Item.Settings()
            .maxDamage(EquipmentType.HELMET.getMaxDamage(5))
            .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD)
                    .cameraOverlay(Identifier.of(AncientExplosives.MOD_ID, "misc/goggles_overlay"))
                    .dispensable(true)
                    .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER)
                    .build())
            .repairable(ArmorMaterials.LEATHER.repairIngredient())
    );

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
