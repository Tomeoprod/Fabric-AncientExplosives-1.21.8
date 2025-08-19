package net.tomeoprod.ancient_explosives.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public record FlashRenderingS2CPacket(int entityId) implements CustomPayload{
    public static final Identifier FLASH_RENDERING_ID = Identifier.of(AncientExplosives.MOD_ID, "flash_rendering");
    public static final Id<FlashRenderingS2CPacket> ID = new Id<>(FLASH_RENDERING_ID);
    public static final PacketCodec<RegistryByteBuf, FlashRenderingS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FlashRenderingS2CPacket::entityId,
            FlashRenderingS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
