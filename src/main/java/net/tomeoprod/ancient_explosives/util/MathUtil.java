package net.tomeoprod.ancient_explosives.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class MathUtil {
    public static boolean isEntityVisible(PlayerEntity player, Entity entity) {
        // Step 1: Check if entity is in the player's field of view
        Vec3d playerLook = player.getRotationVec(1.0F).normalize();
        Vec3d directionToEntity = entity.getPos().subtract(player.getPos()).normalize();

        double dot = playerLook.dotProduct(directionToEntity);
        dot = MathHelper.clamp(dot, -1.0, 1.0);

        double angleDegrees = Math.toDegrees(Math.acos(dot));

        if (angleDegrees >= (MinecraftClient.getInstance().options.getFov().getValue() / 2.0)) {
            return false; // Outside FOV
        }

        // Step 2: Raycast to check for visibility (line of sight)
        World world = player.getWorld();
        Vec3d start = player.getCameraPosVec(1.0F);
        Vec3d end = entity.getBoundingBox().getCenter(); // Aim at entity center

        BlockHitResult blockHit = world.raycast(new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                player
        ));

        // If ray hits before reaching entity, it's blocked
        if (blockHit != null && blockHit.getPos().squaredDistanceTo(start) < end.squaredDistanceTo(start)) {
            return false;
        }

        return true; // Entity is in FOV and not obstructed
    }
}
