package net.tomeoprod.ancient_explosives.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.tomeoprod.ancient_explosives.block.ModBlocks;
import net.tomeoprod.ancient_explosives.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                createShaped(RecipeCategory.MISC, ModBlocks.ECHO_CRYSTAL.asItem(), 1)
                        .pattern("sss")
                        .pattern("sss")
                        .pattern("sss")
                        .input('s', Items.ECHO_SHARD)
                        .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                        .criterion(hasItem(ModBlocks.ECHO_CRYSTAL.asItem()), conditionsFromItem(ModBlocks.ECHO_CRYSTAL.asItem()))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.ECHO_SHARDS_CLUSTER, 3)
                        .pattern("   ")
                        .pattern(" s ")
                        .pattern("sbs")
                        .input('s', Items.ECHO_SHARD)
                        .input('b', Items.SLIME_BALL)
                        .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                        .criterion(hasItem(Items.SLIME_BALL), conditionsFromItem(Items.SLIME_BALL))
                        .criterion(hasItem(ModItems.ECHO_SHARDS_CLUSTER), conditionsFromItem(ModItems.ECHO_SHARDS_CLUSTER))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.ECHO_AMPLIFIER, 1)
                        .pattern(" a ")
                        .pattern("ses")
                        .pattern("ddd")
                        .input('s', Items.ECHO_SHARD)
                        .input('a', Items.AMETHYST_SHARD)
                        .input('d', Items.DEEPSLATE)
                        .input('e', ModBlocks.ECHO_CRYSTAL.asItem())
                        .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                        .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                        .criterion(hasItem(ModBlocks.ECHO_CRYSTAL.asItem()), conditionsFromItem(ModBlocks.ECHO_CRYSTAL.asItem()))
                        .criterion(hasItem(Items.DEEPSLATE), conditionsFromItem(Items.DEEPSLATE))
                        .criterion(hasItem(ModItems.ECHO_AMPLIFIER), conditionsFromItem(ModItems.ECHO_AMPLIFIER))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModBlocks.ECHO_TNT, 1)
                        .pattern("ese")
                        .pattern("sgs")
                        .pattern("ese")
                        .input('s', Items.SAND)
                        .input('g', Items.GUNPOWDER)
                        .input('e', Items.ECHO_SHARD)
                        .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                        .criterion(hasItem(Items.GUNPOWDER), conditionsFromItem(Items.GUNPOWDER))
                        .criterion(hasItem(Items.SAND), conditionsFromItem(Items.SAND))
                        .criterion(hasItem(ModBlocks.ECHO_TNT.asItem()), conditionsFromItem(ModBlocks.ECHO_TNT.asItem()))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.GLOWING_SHARDS_CLUSTER, 3)
                        .pattern("   ")
                        .pattern(" s ")
                        .pattern("sbs")
                        .input('s', ModItems.GLOWING_SHARD)
                        .input('b', Items.SLIME_BALL)
                        .criterion(hasItem(ModItems.GLOWING_SHARD), conditionsFromItem(ModItems.GLOWING_SHARD))
                        .criterion(hasItem(Items.SLIME_BALL), conditionsFromItem(Items.SLIME_BALL))
                        .criterion(hasItem(ModItems.GLOWING_SHARDS_CLUSTER), conditionsFromItem(ModItems.GLOWING_SHARDS_CLUSTER))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.GLOWING_SHARD, 1)
                        .pattern(" d ")
                        .pattern("dsd")
                        .pattern(" d ")
                        .input('s', Items.ECHO_SHARD)
                        .input('d', Items.GLOWSTONE_DUST)
                        .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                        .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                        .criterion(hasItem(ModItems.GLOWING_SHARD), conditionsFromItem(ModItems.GLOWING_SHARD))
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.GOGGLES, 1)
                        .pattern("sss")
                        .pattern("s s")
                        .pattern("glg")
                        .input('s', Items.STRING)
                        .input('g', Items.TINTED_GLASS)
                        .input('l', Items.LEATHER)
                        .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                        .criterion(hasItem(Items.TINTED_GLASS), conditionsFromItem(Items.TINTED_GLASS))
                        .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                        .criterion(hasItem(ModItems.GOGGLES), conditionsFromItem(ModItems.GOGGLES))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Recipe Generator";
    }
}
