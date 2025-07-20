package net.tomeoprod.ancient_explosives.item;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

import java.util.Map;

public class WardenArmorMaterial {
    public static final RegistryKey<EquipmentAsset> WARDEN_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(AncientExplosives.MOD_ID, "warden_armor"));

    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
            37,
            Map.of(
                    EquipmentType.BODY, 8
            ),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            3.0F,
            0.1F,
            ItemTags.REPAIRS_NETHERITE_ARMOR,
            WARDEN_ARMOR_MATERIAL_KEY
    );
}
