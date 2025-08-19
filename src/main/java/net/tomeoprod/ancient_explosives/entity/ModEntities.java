package net.tomeoprod.ancient_explosives.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.entity.custom.EchoShardClusterProjectileEntity;
import net.tomeoprod.ancient_explosives.entity.custom.GlowingShardClusterProjectileEntity;

public class ModEntities {
    public static final EntityType<EchoShardClusterProjectileEntity> ECHO_SHARD_CLUSTER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(AncientExplosives.MOD_ID, "echo_shard_cluster"),
            EntityType.Builder.<EchoShardClusterProjectileEntity>create(EchoShardClusterProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 1.15F)
                    .maxTrackingRange(4).trackingTickInterval(10).
                    build(RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(AncientExplosives.MOD_ID, "echo_shard_cluster"))));
                
    public static final EntityType<GlowingShardClusterProjectileEntity> GLOWING_SHARD_CLUSTER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(AncientExplosives.MOD_ID, "glowing_shard_cluster"),
            EntityType.Builder.<GlowingShardClusterProjectileEntity>create(GlowingShardClusterProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 1.15F)
                    .maxTrackingRange(4).trackingTickInterval(10).
                    build(RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(AncientExplosives.MOD_ID, "glowing_shard_cluster"))));
            

    public static void registerModEntities() {}
}
