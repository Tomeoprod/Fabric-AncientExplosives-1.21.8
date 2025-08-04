package net.tomeoprod.ancient_explosives.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (SheepEntity.class)
public abstract class SheepMixin extends AnimalEntity {
    protected SheepMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract DyeColor getColor();

    @Inject(method = "interactMob", at = @At("HEAD"))
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        World world = player.getWorld();
        ItemStack stack = player.getStackInHand(hand);
        int stuckShards = this.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0);

        if (stack.isOf(Items.SHEARS) && stuckShards > 0) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putInt("echo_shard_clusters_stuck", 0);
            ItemStack shards = Items.ECHO_SHARD.getDefaultStack();
            ItemStack slimeBall = Items.SLIME_BALL.getDefaultStack();

            shards.setCount(3 * stuckShards);
            slimeBall.setCount(stuckShards);

            world.spawnEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), shards));
            world.spawnEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), slimeBall));

            this.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
            world.playSound(null, this.getBlockPos(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.PLAYERS);
            stack.damage(1, player);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.SHEEP_FOOD);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        SheepEntity sheepEntity = (SheepEntity) EntityType.SHEEP.create(serverWorld, SpawnReason.BREEDING);
        if (sheepEntity != null) {
            DyeColor dyeColor = this.getColor();
            DyeColor dyeColor2 = ((SheepEntity)passiveEntity).getColor();
            sheepEntity.setColor(DyeColor.mixColors(serverWorld, dyeColor, dyeColor2));
        }

        return sheepEntity;
    }
}
