package net.tomeoprod.ancient_explosives;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.tomeoprod.ancient_explosives.entity.ModEntities;
import net.tomeoprod.ancient_explosives.entity.client.EchoShardClusterProjectileModel;
import net.tomeoprod.ancient_explosives.entity.client.EchoShardClusterProjectileRenderer;
import net.tomeoprod.ancient_explosives.event.KeyInputHandler;
import net.tomeoprod.ancient_explosives.item.custom.EchoAmplifierItem;
import net.tomeoprod.ancient_explosives.networking.ModMessages;
import net.tomeoprod.ancient_explosives.particle.ModParticles;

public class AncientExplosivesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModMessages.registerS2CPackets();
        ModParticles.registerClientParticles();

        EntityModelLayerRegistry.registerModelLayer(EchoShardClusterProjectileModel.ECHO_SHARD_CLUSTER, EchoShardClusterProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ECHO_SHARD_CLUSTER, EchoShardClusterProjectileRenderer::new);

    }
}
