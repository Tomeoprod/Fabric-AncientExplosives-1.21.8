package net.tomeoprod.ancient_explosives;

import net.fabricmc.api.ModInitializer;

import net.tomeoprod.ancient_explosives.block.ModBlocks;
import net.tomeoprod.ancient_explosives.item.custom.EchoAmplifierItem;
import net.tomeoprod.ancient_explosives.util.ModSounds;
import net.tomeoprod.ancient_explosives.util.OutlineUtils;
import net.tomeoprod.ancient_explosives.component.ModComponents;
import net.tomeoprod.ancient_explosives.effect.ModStatusEffects;
import net.tomeoprod.ancient_explosives.entity.ModEntities;
import net.tomeoprod.ancient_explosives.item.ModItemGroups;
import net.tomeoprod.ancient_explosives.item.ModItems;
import net.tomeoprod.ancient_explosives.networking.ModMessages;
import net.tomeoprod.ancient_explosives.particle.ModParticles;
import net.tomeoprod.ancient_explosives.util.ModGameRules;
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
		ModSounds.initialize();

		OutlineUtils.initializeTick();
	}
}