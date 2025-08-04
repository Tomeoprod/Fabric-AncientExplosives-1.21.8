package net.tomeoprod.ancient_explosives.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class ModSounds {
    public static final SoundEvent YAY = registerSound("yay");
    public static final SoundEvent BALLOON_POP = registerSound("balloon_pop");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(AncientExplosives.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {}
}
