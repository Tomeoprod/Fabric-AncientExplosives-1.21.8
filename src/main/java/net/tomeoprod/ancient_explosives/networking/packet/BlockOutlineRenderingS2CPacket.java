package net.tomeoprod.ancient_explosives.networking.packet;

import net.minecraft.block.Block;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.util.OutlineUtils;

public record BlockOutlineRenderingS2CPacket(int ticks, int x, int y, int z,  int r, int g, int b) implements CustomPayload{
    public static final Identifier CHESTPLATE_ACTIVATION_ID = Identifier.of(AncientExplosives.MOD_ID, "block_outline_rendering");
    public static final Id<BlockOutlineRenderingS2CPacket> ID = new Id<>(CHESTPLATE_ACTIVATION_ID);
    public static final PacketCodec<RegistryByteBuf, BlockOutlineRenderingS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::ticks,
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::x,
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::y,
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::z,
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::r,
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::g,
            PacketCodecs.INTEGER, BlockOutlineRenderingS2CPacket::b,
            BlockOutlineRenderingS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
