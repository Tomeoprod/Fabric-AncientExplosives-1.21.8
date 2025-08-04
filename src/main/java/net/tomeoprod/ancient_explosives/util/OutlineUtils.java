package net.tomeoprod.ancient_explosives.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.tomeoprod.ancient_explosives.AncientExplosives;

import java.util.ArrayList;
import java.util.List;

public class OutlineUtils {
    private static List<Integer> blockGlowTickList = new ArrayList<>();
    private static List<Integer> blockMaxGlowTickList = new ArrayList<>();
    private static List<BlockPos> posList = new ArrayList<>();
    private static List<Integer> entityGlowTickList = new ArrayList<>();
    private static List<Integer> entityMaxGlowTickList = new ArrayList<>();
    private static List<Entity> glowingEntityList = new ArrayList<>();

    public static void initializeTick() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (blockGlowTickList != null) {
                for (int i = 0; i < blockGlowTickList.size(); i++) {
                    int glowTick = blockGlowTickList.get(i);

                    if (glowTick > 0) {
                        blockGlowTickList.set(i, glowTick - 1);
                    }
                }
            }

            if (entityGlowTickList != null) {
                for (int i = 0; i < entityGlowTickList.size(); i++) {
                    int glowTick = entityGlowTickList.get(i);

                    if (glowTick > 0) {
                        entityGlowTickList.set(i, glowTick - 1);
                    }
                }
            }

        });
    }

    public static void addBlockGlow(int ticks, BlockPos blockPos, int red, int green, int blue) {
        blockGlowTickList.addLast(ticks);
        blockMaxGlowTickList.addLast(ticks);
        posList.addLast(blockPos);
        int i = blockGlowTickList.size() - 1;

        WorldRenderEvents.AFTER_TRANSLUCENT.register(worldRenderContext -> {
            Box renderBox = new Box(posList.get(i));
            if (blockGlowTickList.get(i) <= 0) {
                renderBox = new Box(0, 0, 0, 0, 0, 0);
            }

            RenderUtils.renderOutlinedBox(worldRenderContext, renderBox, red, green, blue, (255 * blockGlowTickList.get(i)) / blockMaxGlowTickList.get(i));
        });

        AncientExplosives.LOGGER.info("-> added glow at " + blockPos + " for " + ticks + " ticks");
    }

    public static void addEntityGlow(int ticks, Entity target, int red, int green, int blue) {
        entityGlowTickList.addLast(ticks);
        entityMaxGlowTickList.addLast(ticks);
        glowingEntityList.addLast(target);
        int i = entityGlowTickList.size() - 1;

        WorldRenderEvents.AFTER_TRANSLUCENT.register(worldRenderContext -> {
            Entity entity = glowingEntityList.get(i);

            Box renderBox = new Box(entity.getBoundingBox().getCenter().subtract(0.1), entity.getBoundingBox().getCenter().add(0.1));
            if (entityGlowTickList.get(i) <= 0) {
                renderBox = new Box(0, 0, 0, 0, 0, 0);
            }

            RenderUtils.renderOutlinedBox(worldRenderContext, renderBox, red, green, blue, (255 * entityGlowTickList.get(i)) / entityMaxGlowTickList.get(i));
        });

        AncientExplosives.LOGGER.info("-> added glow to " + target + " for " + ticks + " ticks");
    }
}
