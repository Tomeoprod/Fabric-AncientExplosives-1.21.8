package net.tomeoprod.ancient_explosives;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.tomeoprod.ancient_explosives.datagen.ModModelProvider;
import net.tomeoprod.ancient_explosives.datagen.ModRecipeProvider;
import net.tomeoprod.ancient_explosives.datagen.ModItemTagProvider;
import net.tomeoprod.ancient_explosives.datagen.ModBlockTagProvider;
import net.tomeoprod.ancient_explosives.datagen.ModLootTableProvider;
import net.tomeoprod.ancient_explosives.datagen.ModEnchantmentProvider;

public class AncientExplosivesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModEnchantmentProvider::new);
	}
}
