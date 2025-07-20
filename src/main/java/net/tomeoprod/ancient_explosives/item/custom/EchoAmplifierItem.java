package net.tomeoprod.ancient_explosives.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.block.ModBlocks;
import net.tomeoprod.ancient_explosives.block.custom.EchoTntBlock;
import net.tomeoprod.ancient_explosives.effect.ModStatusEffects;
import net.tomeoprod.ancient_explosives.entity.custom.EchoShardClusterProjectileEntity;
import net.tomeoprod.ancient_explosives.particle.ModParticles;
import net.tomeoprod.ancient_explosives.util.ModDamageTypes;
import net.tomeoprod.ancient_explosives.util.ModGameRules;

import java.util.List;
import java.util.Random;

public class EchoAmplifierItem extends Item {
    public EchoAmplifierItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld) {
            Vec3d playerPos = user.getPos().add(user.getAttachments().getPoint(EntityAttachmentType.WARDEN_CHEST, 0, user.getYaw()));
            Vec3d vec3d2 = user.getEyePos().add(user.getRotationVec(1.0F).multiply(64)).subtract(playerPos);
            Vec3d vec3d3 = vec3d2.normalize();
            int i = MathHelper.floor(vec3d2.length()) + 7;
            List<LivingEntity> livingEntities = null;
            List<EchoShardClusterProjectileEntity> echoShardClusters = null;

            serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.NEUTRAL, 0.5f, 1);

            for (int j = 1; j < i; j++) {
                Vec3d vec3d4 = playerPos.add(vec3d3.multiply(j));
                //serverWorld.spawnParticles(ParticleTypes.VIBRATION, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0);

                Box box = new Box(vec3d4.x - 1, vec3d4.y - 1, vec3d4.z - 1, vec3d4.x + 1, vec3d4.y + 1, vec3d4.z + 1);
                List<LivingEntity> livingEntitiesTemp = serverWorld.getEntitiesByClass(LivingEntity.class, box, entity -> true);
                if (livingEntities == null) {
                    livingEntities = livingEntitiesTemp;
                }else livingEntities.addAll(livingEntitiesTemp);
                
                List<EchoShardClusterProjectileEntity> echoShardClustersTemp = serverWorld.getEntitiesByClass(EchoShardClusterProjectileEntity.class, box, echoShardClusterProjectileEntity -> true);
                if (echoShardClusters == null) {
                    echoShardClusters = echoShardClustersTemp;
                }else echoShardClusters.addAll(echoShardClustersTemp);

                for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
                    for (int y = (int) box.minY; y <= (int) box.maxY; y++) {
                        for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            BlockState state = world.getBlockState(pos);
                            if (state.getBlock() == ModBlocks.ECHO_CRYSTAL) {
                                serverWorld.spawnParticles(ModParticles.SCULK_PARTICLE, x, y, z, 900, 0, 0.5, 0, 0.1);

                                serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                                serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                                serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                                applyShards(x, y, z, serverWorld, true);

                                serverWorld.removeBlock(pos, false);
                                return ActionResult.SUCCESS;
                            }
                        }
                    }
                }

                for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
                    for (int y = (int) box.minY; y <= (int) box.maxY; y++) {
                        for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            BlockState state = world.getBlockState(pos);
                            if (state.getBlock() == ModBlocks.ECHO_TNT) {
                                serverWorld.spawnParticles(ModParticles.SCULK_PARTICLE, x, y, z, 900, 0, 0.5, 0, 0.1);

                                serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                                serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                                serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                                applyShards(x, y, z, serverWorld, true);
                                EchoTntBlock.explode(serverWorld, user, x, y, z);

                                serverWorld.removeBlock(pos, false);
                                return ActionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
            
            if (echoShardClusters != null) {
                for (EchoShardClusterProjectileEntity echoShardCluster : echoShardClusters) {
                    serverWorld.spawnParticles(ModParticles.SCULK_PARTICLE, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), 100, 0, 0.5, 0, 0.1);

                    serverWorld.playSound(echoShardCluster, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                    serverWorld.playSound(echoShardCluster, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                    serverWorld.playSound(echoShardCluster, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                    applyShards(echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), serverWorld, false);

                    echoShardCluster.discard();
                    return ActionResult.SUCCESS;
                }
            }

            if (livingEntities != null) {
                for (LivingEntity target : livingEntities) {
                    int echoShardClustersStuck = target.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0);

                    if (target != user && echoShardClustersStuck > 0) {
                        if (target.getHealth() < 7 * echoShardClustersStuck) {
                            implode(target, serverWorld);
                        } else target.damage(serverWorld, serverWorld.getDamageSources().create(ModDamageTypes.IMPLODE_DAMAGE_KEY, user), 7 * echoShardClustersStuck);
                        applyShards(target.getX(), target.getY(), target.getZ(), serverWorld, false);

                        return ActionResult.SUCCESS;
                    }
                }
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        return ActionResult.PASS;
    }

    public static void implodeEffect(LivingEntity target, ServerWorld serverWorld, SimpleParticleType particleType) {
        if (target instanceof SlimeEntity slimeEntity) {
            serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), 1000 * slimeEntity.getSize(), 0, 0.5, 0, 0.1);
        }else if (target instanceof MagmaCubeEntity magmaCubeEntity) {
            serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), 1000 * magmaCubeEntity.getSize(), 0, 0.5, 0, 0.1);
        }else serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), 1000, 0, 0.5, 0, 0.1);
        serverWorld.spawnParticles(ModParticles.SCULK_PARTICLE, target.getX(), target.getY(), target.getZ(), 100 * target.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 1), 0, 0.5, 0, 0.1);

        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

        target.discard();
    }

    public static void implodeEffectBoss(LivingEntity target, ServerWorld serverWorld, SimpleParticleType particleType) {
        serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), 2000, 0, 0.5, 0, 0.1);
        serverWorld.spawnParticles(ModParticles.SCULK_PARTICLE, target.getX(), target.getY(), target.getZ(), 200 * target.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 1), 0, 0.5, 0, 0.1);

        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

        target.discard();
    }

    public static void implode(LivingEntity target, ServerWorld serverWorld) {
        if (serverWorld.getGameRules().getBoolean(ModGameRules.FUN_TIME)) {
            implodeEffect(target, serverWorld, ModParticles.PAPER_PARTICLE);
        } else if (target.getType().equals(EntityType.ALLAY)) {
            implodeEffect(target, serverWorld, ModParticles.ALLAY_PARTICLE);

        } else if (target.getType().equals(EntityType.BLAZE)) {
            implodeEffect(target, serverWorld, ModParticles.BLAZE_PARTICLE);

        } else if (target.getType().equals(EntityType.SKELETON) || target.getType().equals(EntityType.SKELETON_HORSE) || target.getType().equals(EntityType.STRAY) || target.getType().equals(EntityType.BOGGED)) {
            implodeEffect(target, serverWorld, ModParticles.BONE_PARTICLE);

        } else if (target.getType().equals(EntityType.ENDERMAN) || target.getType().equals(EntityType.ENDERMITE)) {
            implodeEffect(target, serverWorld, ModParticles.ENDERMAN_PARTICLE);

        } else if (target.getType().equals(EntityType.ENDER_DRAGON)) {
            implodeEffectBoss(target, serverWorld, ModParticles.ENDERMAN_PARTICLE);

        } else if (target.getType().equals(EntityType.CREEPER)) {
            implodeEffect(target, serverWorld, ModParticles.GUNPOWDER_PARTICLE);

        } else if (target.getType().equals(EntityType.SQUID) || target.getType().equals(EntityType.GLOW_SQUID)) {
            implodeEffect(target, serverWorld, ModParticles.INK_PARTICLE);

        } else if (target.getType().equals(EntityType.IRON_GOLEM)) {
            implodeEffect(target, serverWorld, ModParticles.IRON_PARTICLE);

        } else if (target.getType().equals(EntityType.MAGMA_CUBE)) {
            implodeEffect(target, serverWorld, ModParticles.MAGMA_CREAM_PARTICLE);

        } else if (target.getType().equals(EntityType.PHANTOM)) {
            implodeEffect(target, serverWorld, ModParticles.MEMBRANE_PARTICLE);

        } else if (target.getType().equals(EntityType.ZOMBIE) || target.getType().equals(EntityType.ZOMBIE_HORSE) || target.getType().equals(EntityType.ZOMBIE_VILLAGER) || target.getType().equals(EntityType.ZOMBIFIED_PIGLIN) || target.getType().equals(EntityType.ZOGLIN) || target.getType().equals(EntityType.DROWNED) || target.getType().equals(EntityType.HUSK)) {
            implodeEffect(target, serverWorld, ModParticles.ROTTEN_FLESH_PARTICLE);

        } else if (target.getType().equals(EntityType.WARDEN)) {
            implodeEffectBoss(target, serverWorld, ModParticles.SCULK_PARTICLE);

        } else if (target.getType().equals(EntityType.TURTLE)) {
            implodeEffect(target, serverWorld, ModParticles.SHELL_PARTICLE);

        } else if (target.getType().equals(EntityType.SHULKER)) {
            implodeEffect(target, serverWorld, ModParticles.SHULKER_PARTICLE);

        } else if (target.getType().equals(EntityType.SLIME)) {
            implodeEffect(target, serverWorld, ModParticles.SLIMEBALL_PARTICLE);

        } else if (target.getType().equals(EntityType.SNOW_GOLEM)) {
            implodeEffect(target, serverWorld, ModParticles.SNOW_PARTICLE);

        } else if (target.getType().equals(EntityType.WITHER) || target.getType().equals(EntityType.WITHER_SKELETON)) {
            implodeEffect(target, serverWorld, ModParticles.WITHER_BONE_PARTICLE);

        } else implodeEffect(target, serverWorld, ModParticles.MEAT_PARTICLE);
    }
    
    public static void applyShards(double x, double y, double z, ServerWorld serverWorld, Boolean damage) {
        Vec3d pos = new Vec3d(x, y, z);

        Box explosionBox = new Box(x - 8, y - 8, z - 8, x + 8, y + 8, z + 8);
        List<LivingEntity> livingEntitiesShards = serverWorld.getEntitiesByClass(LivingEntity.class, explosionBox, entity -> true);
        for (LivingEntity shardTarget : livingEntitiesShards) {
            int armorPieces = 0;
            if (shardTarget.getEquippedStack(EquipmentSlot.HEAD) != ItemStack.EMPTY) {
                armorPieces++;
            }
            if (shardTarget.getEquippedStack(EquipmentSlot.CHEST) != ItemStack.EMPTY) {
                armorPieces++;
            }
            if (shardTarget.getEquippedStack(EquipmentSlot.LEGS) != ItemStack.EMPTY) {
                armorPieces++;
            }
            if (shardTarget.getEquippedStack(EquipmentSlot.FEET) != ItemStack.EMPTY) {
                armorPieces++;
            }

            if (armorPieces == 0) {
                shardTarget.addStatusEffect(new StatusEffectInstance(
                        ModStatusEffects.SHARDS,
                        400,
                        new Random().nextInt(1, 10),
                        false,
                        false,
                        true));
            } else if (new Random().nextInt(101) < 25 / armorPieces) {
                shardTarget.addStatusEffect(new StatusEffectInstance(
                        ModStatusEffects.SHARDS,
                        400,
                        new Random().nextInt(1, 10 / armorPieces),
                        false,
                        false,
                        true));
            }

            double distance = shardTarget.getPos().distanceTo(pos);
            double distancePercent = (100 * (8 - distance)) / 8;
            float damageDealt = (14f * (float) distancePercent) / 100f;

            shardTarget.damage(serverWorld, serverWorld.getDamageSources().create(ModDamageTypes.INTERNAL_BLEEDING_DAMAGE_KEY), damageDealt);
        }
    }
}
