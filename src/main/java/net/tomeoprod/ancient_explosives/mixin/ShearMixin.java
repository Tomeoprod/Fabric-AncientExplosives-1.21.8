package net.tomeoprod.ancient_explosives.mixin;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin (ShearsItem.class)
public class ShearMixin extends Item {
    public ShearMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        int stuckEchoShards = entity.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0);
        int stuckGlowingShards = entity.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("glowing_shard_clusters_stuck", 0);

        if (stuckGlowingShards > 0 || stuckEchoShards > 0) {
            if (stuckEchoShards > 0) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putInt("echo_shard_clusters_stuck", 0);
                ItemStack echoShards = Items.ECHO_SHARD.getDefaultStack();
                ItemStack slimeBall = Items.SLIME_BALL.getDefaultStack();

                echoShards.setCount(3 * stuckEchoShards);
                slimeBall.setCount(stuckEchoShards);

                world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), echoShards));
                world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), slimeBall));

                entity.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.PLAYERS);
                stack.damage(1, user);
            }

            if (stuckGlowingShards > 0) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putInt("echo_shard_clusters_stuck", 0);
                ItemStack glowingShards = Items.ECHO_SHARD.getDefaultStack();
                ItemStack slimeBall = Items.SLIME_BALL.getDefaultStack();

                glowingShards.setCount(3 * stuckEchoShards);
                slimeBall.setCount(stuckEchoShards);

                world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), glowingShards));
                world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), slimeBall));

                entity.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
                world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.PLAYERS);
                stack.damage(1, user);
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        int stuckShards = user.get(DataComponentTypes.CUSTOM_DATA).copyNbt().getInt("echo_shard_clusters_stuck", 0);

        if (user.isSneaking() && stuckShards > 0) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putInt("echo_shard_clusters_stuck", 0);
            ItemStack shards = Items.ECHO_SHARD.getDefaultStack();
            ItemStack slimeBall = Items.SLIME_BALL.getDefaultStack();

            shards.setCount(3 * stuckShards);
            slimeBall.setCount(stuckShards);

            world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), shards));
            world.spawnEntity(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), slimeBall));

            user.setComponent(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbtCompound));
            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.PLAYERS);
            user.getStackInHand(hand).damage(1, user);

            return ActionResult.SUCCESS;
        }

        return super.use(world, user, hand);
    }
}
