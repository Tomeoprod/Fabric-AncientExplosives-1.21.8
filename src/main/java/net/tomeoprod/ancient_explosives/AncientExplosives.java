package net.tomeoprod.ancient_explosives;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tomeoprod.ancient_explosives.block.ModBlocks;
import net.tomeoprod.ancient_explosives.component.ModComponents;
import net.tomeoprod.ancient_explosives.effect.ModStatusEffects;
import net.tomeoprod.ancient_explosives.entity.ModEntities;
import net.tomeoprod.ancient_explosives.item.ModItemGroups;
import net.tomeoprod.ancient_explosives.item.ModItems;
import net.tomeoprod.ancient_explosives.networking.ModMessages;
import net.tomeoprod.ancient_explosives.particle.ModParticles;
import net.tomeoprod.ancient_explosives.util.ModGameRules;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncientExplosives implements ModInitializer {
	public static final String MOD_ID = "ancient_explosives";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModComponents.initialize();
		ModMessages.registerC2SPackets();
		ModParticles.registerParticles();
		ModEntities.registerModEntities();
		ModGameRules.register();
		ModStatusEffects.register();
	}
}