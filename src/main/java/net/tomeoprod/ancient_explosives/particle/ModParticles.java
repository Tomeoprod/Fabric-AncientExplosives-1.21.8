package net.tomeoprod.ancient_explosives.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class ModParticles {
    public static final SimpleParticleType ALLAY_PARTICLE = registerParticle("allay_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType BLAZE_PARTICLE = registerParticle("blaze_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType BONE_PARTICLE = registerParticle("bone_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType ENDERMAN_PARTICLE = registerParticle("enderman_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType GUNPOWDER_PARTICLE = registerParticle("gunpowder_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType INK_PARTICLE = registerParticle("ink_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType IRON_PARTICLE = registerParticle("iron_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType MAGMA_CREAM_PARTICLE = registerParticle("magma_cream_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType MEAT_PARTICLE = registerParticle("meat_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType MEMBRANE_PARTICLE = registerParticle("membrane_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType PAPER_PARTICLE = registerParticle("paper_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType ROTTEN_FLESH_PARTICLE = registerParticle("rotten_flesh_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType SCULK_PARTICLE = registerParticle("sculk_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType SHELL_PARTICLE = registerParticle("shell_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType SHULKER_PARTICLE = registerParticle("shulker_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType SLIMEBALL_PARTICLE = registerParticle("slimeball_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType SNOW_PARTICLE = registerParticle("snow_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType WITHER_BONE_PARTICLE = registerParticle("wither_bone_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType PILLAGER_PARTICLE = registerParticle("pillager_particle", FabricParticleTypes.simple(true));
    public static final SimpleParticleType GUARDIAN_PARTICLE = registerParticle("guardian_particle", FabricParticleTypes.simple(true));

    public static final SimpleParticleType SOUND_WAVE_PARTICLE = registerParticle("sound_wave_particle", FabricParticleTypes.simple(true));

    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(AncientExplosives.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
    }

    public static void registerClientParticles() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.ALLAY_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLAZE_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BONE_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ENDERMAN_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GUNPOWDER_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.INK_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.IRON_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGMA_CREAM_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MEAT_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MEMBRANE_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PAPER_PARTICLE, PaperParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ROTTEN_FLESH_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SCULK_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHELL_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHULKER_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SLIMEBALL_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SNOW_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WITHER_BONE_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PILLAGER_PARTICLE, GibParticleBase.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GUARDIAN_PARTICLE, GibParticleBase.Factory::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.SOUND_WAVE_PARTICLE, SoundWaveParticle.Factory::new);
    }
}
