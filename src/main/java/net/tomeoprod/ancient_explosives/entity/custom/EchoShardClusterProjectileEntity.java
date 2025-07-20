package net.tomeoprod.ancient_explosives.entity.custom;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
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
import net.minecraft.world.World;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.component.ModComponents;
import net.tomeoprod.ancient_explosives.entity.ModEntities;
import net.tomeoprod.ancient_explosives.item.ModItems;

public class EchoShardClusterProjectileEntity extends PersistentProjectileEntity{
    public EchoShardClusterProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public EchoShardClusterProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.ECHO_SHARD_CLUSTER, player, world, new ItemStack(ModItems.ECHO_SHARDS_CLUSTER), null);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.ECHO_SHARDS_CLUSTER);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        World world = this.getWorld();
        NbtCompound nbtCompound = new NbtCompound();;
        nbtCompound.putInt("echo_shard_clusters_stuck", entity.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0) + 1);

        if (!world.isClient) {
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
