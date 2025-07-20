package net.tomeoprod.ancient_explosives.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class ModComponents {
    public static void initialize() {
        AncientExplosives.LOGGER.info("Registering {} components", AncientExplosives.MOD_ID);
    }

    /*public static final ComponentType<Integer> STUCK_SHARDS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(AncientExplosives.MOD_ID, "stuck_shards"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );*/
}
