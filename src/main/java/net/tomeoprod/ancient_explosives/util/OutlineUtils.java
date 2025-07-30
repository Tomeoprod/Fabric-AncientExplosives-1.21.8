package net.tomeoprod.ancient_explosives.util;

import me.emafire003.dev.coloredglowlib.ColoredGlowLibAPI;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.tomeoprod.ancient_explosives.AncientExplosives;

import java.util.ArrayList;
import java.util.List;

public class OutlineUtils {
    private static List<Integer> blockGlowTickList = new ArrayList<>();
    private static List<BlockPos> posList = new ArrayList<>();
    private static List<Integer> entityGlowTickList = new ArrayList<>();
    private static List<Entity> glowingEntityList = new ArrayList<>();

    private static ColoredGlowLibAPI coloredGlowLibAPI;

    public static void initializeTick() {
        ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> {
            coloredGlowLibAPI = ColoredGlowLibMod.getColoredGlowLib();
        });

        ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
            if (blockGlowTickList != null) {
                for (int i = 0; i < blockGlowTickList.size(); i++) {
                    int glowTick = blockGlowTickList.get(i);

                    if (glowTick > 0) {
                        blockGlowTickList.set(i, glowTick - 1);
                    }
                }
            }
            if (coloredGlowLibAPI != null) {
                if (entityGlowTickList != null) {
                    for (int i = 0; i < entityGlowTickList.size(); i++) {
                        int glowTick = entityGlowTickList.get(i);

                        if (glowTick > 0) {
                            entityGlowTickList.set(i, glowTick - 1);
                        } else {
                            coloredGlowLibAPI.setColor(glowingEntityList.get(i), "#ffffff");
                            entityGlowTickList.remove(i);
                            glowingEntityList.remove(i);
                        }
                    }
                }
            }
        });
    }

    public static void addBlockGlow(int ticks, BlockPos blockPos, int red, int green, int blue, int alpha) {
        blockGlowTickList.addLast(ticks);
        posList.addLast(blockPos);
        int i = blockGlowTickList.size() - 1;

        WorldRenderEvents.AFTER_TRANSLUCENT.register(worldRenderContext -> {
            Box renderBox = new Box(posList.get(i));
            if (blockGlowTickList.get(i) <= 0) {
                renderBox = new Box(0, 0, 0, 0, 0, 0);
            }

            RenderUtils.renderOutlinedBox(worldRenderContext, renderBox, red, green, blue, alpha);
        });

        AncientExplosives.LOGGER.info("-> added glow at " + blockPos + " for " + ticks + " ticks");
    }

    public static void addEntityGlow(int ticks, Entity target) {
        entityGlowTickList.addLast(ticks);
        glowingEntityList.addLast(target);

        coloredGlowLibAPI.setColor(target, "#30bcbc");

        AncientExplosives.LOGGER.info("-> added glow to " + target + " for " + ticks + " ticks");
    }
}
