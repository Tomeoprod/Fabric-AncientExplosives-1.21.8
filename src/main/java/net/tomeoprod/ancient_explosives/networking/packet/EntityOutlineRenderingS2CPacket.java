package net.tomeoprod.ancient_explosives.networking.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public record EntityOutlineRenderingS2CPacket(int ticks, int entityId, int r, int g, int b) implements CustomPayload{
    public static final Identifier ENTITY_OUTLINE_RENDERING_ID = Identifier.of(AncientExplosives.MOD_ID, "entity_outline_rendering");
    public static final Id<EntityOutlineRenderingS2CPacket> ID = new Id<>(ENTITY_OUTLINE_RENDERING_ID);
    public static final PacketCodec<RegistryByteBuf, EntityOutlineRenderingS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, EntityOutlineRenderingS2CPacket::ticks,
            PacketCodecs.INTEGER, EntityOutlineRenderingS2CPacket::entityId,
            PacketCodecs.INTEGER, EntityOutlineRenderingS2CPacket::r,
            PacketCodecs.INTEGER, EntityOutlineRenderingS2CPacket::g,
            PacketCodecs.INTEGER, EntityOutlineRenderingS2CPacket::b,
            EntityOutlineRenderingS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
