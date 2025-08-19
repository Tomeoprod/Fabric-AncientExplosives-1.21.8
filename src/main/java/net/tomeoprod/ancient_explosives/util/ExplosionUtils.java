package net.tomeoprod.ancient_explosives.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tomeoprod.ancient_explosives.effect.ModStatusEffects;
import net.tomeoprod.ancient_explosives.particle.ModParticles;

import java.util.List;
import java.util.Random;

public class ExplosionUtils {
    public static void implodeEffect(LivingEntity target, ServerWorld serverWorld, SimpleParticleType particleType, int count) {
        if (target instanceof SlimeEntity slimeEntity) {
            serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), count * slimeEntity.getSize(), 0, 0.5, 0, 0.1);
        } else if (target instanceof MagmaCubeEntity magmaCubeEntity) {
            serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), count * magmaCubeEntity.getSize(), 0, 0.5, 0, 0.1);
        } else
            serverWorld.spawnParticles(particleType, target.getX(), target.getY(), target.getZ(), count, 0, 0.5, 0, 0.1);
        serverWorld.spawnParticles(ModParticles.ECHO_SHARD_PARTICLE, target.getX(), target.getY(), target.getZ(), 100 * target.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 1), 0, 0.5, 0, 0.1);

        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

        if (target instanceof PlayerEntity) {
            target.kill(serverWorld);
        } else target.discard();
    }

    public static void funTimeImplodeEffect(LivingEntity target, ServerWorld serverWorld) {
        serverWorld.spawnParticles(ModParticles.PAPER_PARTICLE, target.getX(), target.getY(), target.getZ(), 1000, 0, 0.5, 0, 0.1);

        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), ModSounds.BALLOON_POP, SoundCategory.NEUTRAL, 1f, 1f);
        serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), ModSounds.YAY, SoundCategory.NEUTRAL, 1f, 1f);

        if (target instanceof PlayerEntity) {
            target.kill(serverWorld);
        } else target.discard();
    }

    public static void implode(LivingEntity target, ServerWorld serverWorld) {
        if (serverWorld.getGameRules().getBoolean(ModGameRules.FUN_TIME)) {
            funTimeImplodeEffect(target, serverWorld);
        } else if (target.getType().equals(EntityType.ALLAY)) {
            implodeEffect(target, serverWorld, ModParticles.ALLAY_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.BLAZE)) {
            implodeEffect(target, serverWorld, ModParticles.BLAZE_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.SKELETON) || target.getType().equals(EntityType.SKELETON_HORSE) || target.getType().equals(EntityType.STRAY) || target.getType().equals(EntityType.BOGGED)) {
            implodeEffect(target, serverWorld, ModParticles.BONE_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.ENDERMAN) || target.getType().equals(EntityType.ENDERMITE)) {
            implodeEffect(target, serverWorld, ModParticles.ENDERMAN_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.ENDER_DRAGON)) {
            implodeEffect(target, serverWorld, ModParticles.ENDERMAN_PARTICLE, 2000);

        } else if (target.getType().equals(EntityType.CREEPER)) {
            implodeEffect(target, serverWorld, ModParticles.GUNPOWDER_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.SQUID) || target.getType().equals(EntityType.GLOW_SQUID)) {
            implodeEffect(target, serverWorld, ModParticles.INK_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.IRON_GOLEM)) {
            implodeEffect(target, serverWorld, ModParticles.IRON_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.MAGMA_CUBE)) {
            implodeEffect(target, serverWorld, ModParticles.MAGMA_CREAM_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.PHANTOM)) {
            implodeEffect(target, serverWorld, ModParticles.MEMBRANE_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.ZOMBIE) || target.getType().equals(EntityType.ZOMBIE_HORSE) || target.getType().equals(EntityType.ZOMBIE_VILLAGER) || target.getType().equals(EntityType.ZOMBIFIED_PIGLIN) || target.getType().equals(EntityType.ZOGLIN) || target.getType().equals(EntityType.DROWNED) || target.getType().equals(EntityType.HUSK)) {
            implodeEffect(target, serverWorld, ModParticles.ROTTEN_FLESH_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.WARDEN)) {
            implodeEffect(target, serverWorld, ModParticles.ECHO_SHARD_PARTICLE, 2000);

        } else if (target.getType().equals(EntityType.TURTLE)) {
            implodeEffect(target, serverWorld, ModParticles.SHELL_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.SHULKER)) {
            implodeEffect(target, serverWorld, ModParticles.SHULKER_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.SLIME)) {
            implodeEffect(target, serverWorld, ModParticles.SLIMEBALL_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.SNOW_GOLEM)) {
            implodeEffect(target, serverWorld, ModParticles.SNOW_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.WITHER) || target.getType().equals(EntityType.WITHER_SKELETON)) {
            implodeEffect(target, serverWorld, ModParticles.WITHER_BONE_PARTICLE, 1000);

        } else if (target.getType().equals(EntityType.PILLAGER) || target.getType().equals(EntityType.VINDICATOR) || target.getType().equals(EntityType.EVOKER) || target.getType().equals(EntityType.RAVAGER)) {
            implodeEffect(target, serverWorld, ModParticles.PILLAGER_PARTICLE, 500);
            implodeEffect(target, serverWorld, ModParticles.MEAT_PARTICLE, 500);

        } else if (target.getType().equals(EntityType.GUARDIAN) || target.getType().equals(EntityType.ELDER_GUARDIAN)) {
            implodeEffect(target, serverWorld, ModParticles.GUARDIAN_PARTICLE, 1000);

        } else implodeEffect(target, serverWorld, ModParticles.MEAT_PARTICLE, 1000);
    }

    public static void applyShards(double x, double y, double z, ServerWorld serverWorld, float maxDamage) {
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
            float damageDealt = (maxDamage * (float) distancePercent) / 100f;

            shardTarget.damage(serverWorld, serverWorld.getDamageSources().create(ModDamageTypes.INTERNAL_BLEEDING_DAMAGE_KEY), damageDealt);
        }
    }

    public static void flashPlayer(PlayerEntity target, int duration) {
        target.addStatusEffect(new StatusEffectInstance(
                ModStatusEffects.STUNNED,
                duration * 20,
                1,
                false,
                false,
                true
        ));
    }

    public static void flashArea(Entity source) {
        World world = source.getWorld();

        Box box = new Box(
                source.getX() - 64,
                source.getY() - 64,
                source.getZ() - 64,
                source.getX() + 64,
                source.getY() + 64,
                source.getZ() + 64
        );

        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, box, playerEntity -> playerEntity != source);
        for (PlayerEntity player : players) {
            if (MathUtil.isEntityVisible(player, source)) {
                double distance = Math.abs(64 - source.getPos().subtract(player.getPos()).length());
                int duration = (int) (distance * 10) / 64;

                flashPlayer(player, duration);
            }
        }
    }
}
