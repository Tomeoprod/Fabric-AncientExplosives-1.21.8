package net.tomeoprod.ancient_explosives.util;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> IMPLODE_DAMAGE_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(AncientExplosives.MOD_ID, "implode"));
    public static final RegistryKey<DamageType> INTERNAL_BLEEDING_DAMAGE_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(AncientExplosives.MOD_ID, "internal_bleeding"));
}
