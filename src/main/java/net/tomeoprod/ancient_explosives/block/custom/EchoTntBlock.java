package net.tomeoprod.ancient_explosives.block.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class EchoTntBlock extends Block {
    public EchoTntBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onDestroyedByExplosion(ServerWorld world, BlockPos pos, Explosion explosion) {
        if (world.getGameRules().getBoolean(GameRules.TNT_EXPLODES)) {
            explode(world, explosion.getCausingEntity(), pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public static void explode(ServerWorld serverWorld, Entity sourceEntity, double x, double y, double z) {
        serverWorld.createExplosion(sourceEntity, x, y, z, 4, false, World.ExplosionSourceType.TNT);
    }
}
