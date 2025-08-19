package net.tomeoprod.ancient_explosives.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class ModStatusEffects {
    public static final RegistryEntry<StatusEffect> SHARDS = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(AncientExplosives.MOD_ID, "shards"), new ShardsEffect());
    public static final RegistryEntry<StatusEffect> STUNNED = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(AncientExplosives.MOD_ID, "stunned"), new StunnedEffect());
    
    public static void register() {}
}
