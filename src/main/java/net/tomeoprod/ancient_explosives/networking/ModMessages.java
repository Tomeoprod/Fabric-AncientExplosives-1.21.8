package net.tomeoprod.ancient_explosives.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.tomeoprod.ancient_explosives.networking.packet.ChestplateActivationC2SPacket;
import net.tomeoprod.ancient_explosives.networking.packet.BlockOutlineRenderingS2CPacket;
import net.tomeoprod.ancient_explosives.networking.packet.EntityOutlineRenderingS2CPacket;
import net.tomeoprod.ancient_explosives.util.OutlineUtils;

public class ModMessages {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(ChestplateActivationC2SPacket.ID, ChestplateActivationC2SPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ChestplateActivationC2SPacket.ID, (payload, context) -> {
            PlayerEntity player = context.player();
            ServerWorld serverWorld = context.server().getWorld(player.getWorld().getRegistryKey());

            ChestplateActivationC2SPacket.sonicBoom(serverWorld, player);
        });
    }

    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(BlockOutlineRenderingS2CPacket.ID, BlockOutlineRenderingS2CPacket.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(BlockOutlineRenderingS2CPacket.ID, (blockOutlineRenderingS2CPacket, context) -> {
            BlockPos pos = new BlockPos(blockOutlineRenderingS2CPacket.x(), blockOutlineRenderingS2CPacket.y(), blockOutlineRenderingS2CPacket.z());
            int ticks = blockOutlineRenderingS2CPacket.ticks();
            int r = blockOutlineRenderingS2CPacket.r();
            int g = blockOutlineRenderingS2CPacket.g();
            int b = blockOutlineRenderingS2CPacket.b();

            OutlineUtils.addBlockGlow(ticks, pos, r, g, b);
        });

        PayloadTypeRegistry.playS2C().register(EntityOutlineRenderingS2CPacket.ID, EntityOutlineRenderingS2CPacket.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(EntityOutlineRenderingS2CPacket.ID, (entityOutlineRenderingS2CPacket, context) -> {
            Entity entity = context.player().getWorld().getEntityById(entityOutlineRenderingS2CPacket.entityId());
            int ticks = entityOutlineRenderingS2CPacket.ticks();
            int r = entityOutlineRenderingS2CPacket.r();
            int g = entityOutlineRenderingS2CPacket.g();
            int b = entityOutlineRenderingS2CPacket.b();

            OutlineUtils.addEntityGlow(ticks, entity, r, g, b);
        });
    }
}
