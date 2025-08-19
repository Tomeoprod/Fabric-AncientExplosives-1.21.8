package net.tomeoprod.ancient_explosives.entity.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tomeoprod.ancient_explosives.entity.ModEntities;
import net.tomeoprod.ancient_explosives.item.ModItems;

public class GlowingShardClusterProjectileEntity extends PersistentProjectileEntity{
    public GlowingShardClusterProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public GlowingShardClusterProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.GLOWING_SHARD_CLUSTER, player, world, new ItemStack(ModItems.GLOWING_SHARDS_CLUSTER), null);
    }

    @Override
    protected Box calculateDefaultBoundingBox(Vec3d pos) {
        return super.calculateDefaultBoundingBox(pos);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.ECHO_SHARDS_CLUSTER);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        World world = this.getWorld();
        if (!world.isClient) {
            Entity entity = entityHitResult.getEntity();
            NbtCompound nbtCompound = new NbtCompound();;
            nbtCompound.putInt("glowing_shard_clusters_stuck", entity.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("glowing_shard_clusters_stuck", 0) + 1);

            entity.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
            world.sendEntityStatus(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_SLIME_BLOCK_HIT;
    }
}
