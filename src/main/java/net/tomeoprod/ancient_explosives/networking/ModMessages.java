package net.tomeoprod.ancient_explosives.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.tomeoprod.ancient_explosives.networking.packet.ChestplateActivationC2SPacket;

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

    }
}
