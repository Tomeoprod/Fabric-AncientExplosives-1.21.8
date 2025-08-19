package net.tomeoprod.ancient_explosives.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.tomeoprod.ancient_explosives.item.ModItems;
import net.tomeoprod.ancient_explosives.networking.packet.FlashRenderingS2CPacket;
import net.tomeoprod.ancient_explosives.util.ExplosionUtils;
import net.tomeoprod.ancient_explosives.util.ModDamageTypes;
import net.tomeoprod.ancient_explosives.util.ModSounds;
import net.tomeoprod.ancient_explosives.util.RenderUtils;
import org.joml.Matrix4f;

import java.awt.*;

public class StunnedEffect extends StatusEffect {
    protected StunnedEffect() {
        super(StatusEffectCategory.HARMFUL, Color.HSBtoRGB(255, 255, 255));
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            int duration = player.getStatusEffect(ModStatusEffects.STUNNED).getDuration();
            double distance = (double) ((duration / 20) * 64) / 10;
            float ringVolume = (float) (distance / 64) / 2;

            player.playSoundToPlayer(ModSounds.RING, SoundCategory.NEUTRAL, ringVolume, 1);
            if (player.getEquippedStack(EquipmentSlot.HEAD).getItem() != ModItems.GOGGLES) {
                ServerPlayNetworking.send((ServerPlayerEntity) player, new FlashRenderingS2CPacket(player.getId()));
            }
        }
    }
}
