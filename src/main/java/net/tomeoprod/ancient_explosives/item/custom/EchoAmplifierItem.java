package net.tomeoprod.ancient_explosives.item.custom;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
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
import net.tomeoprod.ancient_explosives.block.ModBlocks;
import net.tomeoprod.ancient_explosives.block.custom.EchoCrystalBlock;
import net.tomeoprod.ancient_explosives.block.custom.EchoTntBlock;
import net.tomeoprod.ancient_explosives.entity.custom.GlowingShardClusterProjectileEntity;
import net.tomeoprod.ancient_explosives.networking.packet.BlockOutlineRenderingS2CPacket;
import net.tomeoprod.ancient_explosives.networking.packet.EntityOutlineRenderingS2CPacket;
import net.tomeoprod.ancient_explosives.entity.custom.EchoShardClusterProjectileEntity;
import net.tomeoprod.ancient_explosives.item.ModItems;
import net.tomeoprod.ancient_explosives.particle.ModParticles;
import net.tomeoprod.ancient_explosives.util.ExplosionUtils;
import net.tomeoprod.ancient_explosives.util.ModDamageTypes;

import java.util.List;
import java.util.Random;

public class EchoAmplifierItem extends Item {
    public EchoAmplifierItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld) {
            if (user.isSneaking()) {
                Box radiusBox;
                serverWorld.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 1, new Random().nextFloat(0.5f, 1.5f));

                if (user.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.WARDEN_CHESTPLATE)) {
                    radiusBox = new Box(
                            user.getX() - 30,
                            user.getY() - 30,
                            user.getZ() - 30,
                            user.getX() + 30,
                            user.getY() + 30,
                            user.getZ() + 30
                    );
                } else {
                    radiusBox = new Box(
                            user.getX() - 15,
                            user.getY() - 15,
                            user.getZ() - 15,
                            user.getX() + 15,
                            user.getY() + 15,
                            user.getZ() + 15
                    );
                }

                for (int x = (int) radiusBox.minX; x <= (int) radiusBox.maxX; x++) {
                    for (int y = (int) radiusBox.minY; y <= (int) radiusBox.maxY; y++) {
                        for (int z = (int) radiusBox.minZ; z <= (int) radiusBox.maxZ; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            BlockState state = world.getBlockState(pos);

                            if (state.getBlock() instanceof EchoCrystalBlock || state.getBlock() instanceof EchoTntBlock) {
                                ServerPlayNetworking.send((ServerPlayerEntity) user, new BlockOutlineRenderingS2CPacket(60, x, y, z, 48, 188, 188));
                                serverWorld.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 5, new Random().nextFloat(0.5f, 1.5f));
                            }
                        }
                    }
                }

                List<LivingEntity> vibratingEntities = world.getEntitiesByClass(LivingEntity.class, radiusBox, livingEntity -> true);
                for (LivingEntity entity : vibratingEntities) {
                    if (entity.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0) > 0) {
                        ServerPlayNetworking.send((ServerPlayerEntity) user, new EntityOutlineRenderingS2CPacket(60, entity.getId(), 48, 188, 188));
                        serverWorld.playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.BLOCKS, 5, new Random().nextFloat(0.5f, 1.5f));
                    }
                }
            } else {
                Vec3d playerPos = user.getPos().add(user.getAttachments().getPoint(EntityAttachmentType.WARDEN_CHEST, 0, user.getYaw()));
                Vec3d vec3d2;
                if (user.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.WARDEN_CHESTPLATE)) {
                    vec3d2 = user.getEyePos().add(user.getRotationVec(1.0F).multiply(64)).subtract(playerPos);
                } else vec3d2 = user.getEyePos().add(user.getRotationVec(1.0F).multiply(32)).subtract(playerPos);
                Vec3d vec3d3 = vec3d2.normalize();
                int i = MathHelper.floor(vec3d2.length()) + 7;
                List<LivingEntity> livingEntities = null;
                List<EchoShardClusterProjectileEntity> echoShardClusters = null;
                List<GlowingShardClusterProjectileEntity> glowingShardClusters = null;

                serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.NEUTRAL, 0.5f, 1);

                for (int j = 1; j < i; j++) {
                    Vec3d vec3d4 = playerPos.add(vec3d3.multiply(j));

                    Box box = new Box(vec3d4.x - 1, vec3d4.y - 1, vec3d4.z - 1, vec3d4.x + 1, vec3d4.y + 1, vec3d4.z + 1);
                    List<LivingEntity> livingEntitiesTemp = serverWorld.getEntitiesByClass(LivingEntity.class, box, entity -> true);
                    if (livingEntities == null) {
                        livingEntities = livingEntitiesTemp;
                    } else livingEntities.addAll(livingEntitiesTemp);

                    List<EchoShardClusterProjectileEntity> echoShardClustersTemp = serverWorld.getEntitiesByClass(EchoShardClusterProjectileEntity.class, box, echoShardClusterProjectileEntity -> true);
                    if (echoShardClusters == null) {
                        echoShardClusters = echoShardClustersTemp;
                    } else echoShardClusters.addAll(echoShardClustersTemp);
                    
                    List<GlowingShardClusterProjectileEntity> glowingShardClustersTemp = serverWorld.getEntitiesByClass(GlowingShardClusterProjectileEntity.class, box, glowingShardClusterProjectileEntity -> true);
                    if (glowingShardClusters == null) {
                        glowingShardClusters = glowingShardClustersTemp;
                    } else glowingShardClusters.addAll(glowingShardClustersTemp);

                    for (int x = (int) box.minX; x <= (int) box.maxX; x++) {
                        for (int y = (int) box.minY; y <= (int) box.maxY; y++) {
                            for (int z = (int) box.minZ; z <= (int) box.maxZ; z++) {
                                BlockPos pos = new BlockPos(x, y, z);
                                BlockState state = world.getBlockState(pos);
                                if (state.getBlock() instanceof EchoCrystalBlock) {
                                    serverWorld.spawnParticles(ModParticles.ECHO_SHARD_PARTICLE, x + 0.5, y + 0.5, z + 0.5, 900, 0, 0.5, 0, 0.1);

                                    serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                                    serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                                    serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                                    ExplosionUtils.applyShards(x, y, z, serverWorld, 14);

                                    serverWorld.removeBlock(pos, false);
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
                                    serverWorld.spawnParticles(ModParticles.ECHO_SHARD_PARTICLE, x + 0.5, y + 0.5, z + 0.5, 900, 0, 0.5, 0, 0.1);

                                    serverWorld.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                                    serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                                    serverWorld.playSound(null, x, y, z, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                                    ExplosionUtils.applyShards(x, y, z, serverWorld, 14);
                                    EchoTntBlock.explode(serverWorld, user, x, y, z);

                                    serverWorld.removeBlock(pos, false);
                                }
                            }
                        }
                    }
                }

                if (echoShardClusters != null) {
                    for (EchoShardClusterProjectileEntity echoShardCluster : echoShardClusters) {
                        serverWorld.spawnParticles(ModParticles.ECHO_SHARD_PARTICLE, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), 100, 0, 0.5, 0, 0.1);

                        serverWorld.playSound(echoShardCluster, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                        serverWorld.playSound(echoShardCluster, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                        serverWorld.playSound(echoShardCluster, echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                        ExplosionUtils.applyShards(echoShardCluster.getX(), echoShardCluster.getY(), echoShardCluster.getZ(), serverWorld, 14);

                        echoShardCluster.discard();
                    }
                }
                
                if (glowingShardClusters != null) {
                    for (GlowingShardClusterProjectileEntity glowingShardCluster : glowingShardClusters) {
                        serverWorld.spawnParticles(ModParticles.GLOWING_SHARD_PARTICLE, glowingShardCluster.getX(), glowingShardCluster.getY(), glowingShardCluster.getZ(), 100, 0, 0.5, 0, 0.1);

                        serverWorld.playSound(glowingShardCluster, glowingShardCluster.getX(), glowingShardCluster.getY(), glowingShardCluster.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                        serverWorld.playSound(glowingShardCluster, glowingShardCluster.getX(), glowingShardCluster.getY(), glowingShardCluster.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                        serverWorld.playSound(glowingShardCluster, glowingShardCluster.getX(), glowingShardCluster.getY(), glowingShardCluster.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);
                        
                        ExplosionUtils.flashArea(glowingShardCluster);
                        
                        glowingShardCluster.discard();
                    }
                }

                if (livingEntities != null) {
                    for (LivingEntity target : livingEntities) {
                        int echoShardClustersStuck = target.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0);
                        int glowingShardClustersStuck = target.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("glowing_shard_clusters_stuck", 0);

                        if (target != user && echoShardClustersStuck > 0) {
                            if (target.getHealth() < 7 * echoShardClustersStuck) {
                                ExplosionUtils.implode(target, serverWorld);
                            } else {
                                serverWorld.spawnParticles(ModParticles.ECHO_SHARD_PARTICLE, target.getX(), target.getY(), target.getZ(), 100, 0, 0.5, 0, 0.1);

                                serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                                serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                                serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);
                                
                                target.damage(serverWorld, serverWorld.getDamageSources().create(ModDamageTypes.IMPLODE_DAMAGE_KEY, user), 7 * echoShardClustersStuck);
                                NbtCompound nbtCompound = new NbtCompound();
                                nbtCompound.putInt("echo_shard_clusters_stuck", 0);
                                target.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
                            }
                            ExplosionUtils.applyShards(target.getX(), target.getY(), target.getZ(), serverWorld, 14);
                        }
                        
                        if (target != user && glowingShardClustersStuck > 0) {
                            if (target instanceof PlayerEntity player) {
                                ExplosionUtils.flashPlayer(player, 10);
                            }

                            serverWorld.spawnParticles(ModParticles.GLOWING_SHARD_PARTICLE, target.getX(), target.getY(), target.getZ(), 100, 0, 0.5, 0, 0.1);

                            serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1f, 1.25f);
                            serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 4f, 0.6f);
                            serverWorld.playSound(target, target.getX(), target.getY(), target.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.NEUTRAL, 4f, 0.6f);

                            ExplosionUtils.flashArea(target);
                            NbtCompound nbtCompound = new NbtCompound();
                            nbtCompound.putInt("glowing_shard_clusters_stuck", 0);
                            target.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
                        }
                    }
                }
                user.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }
        return ActionResult.SUCCESS;
    }
}
