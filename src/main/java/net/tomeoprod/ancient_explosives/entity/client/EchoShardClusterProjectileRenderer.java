package net.tomeoprod.ancient_explosives.entity.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.entity.custom.EchoShardClusterProjectileEntity;

public class EchoShardClusterProjectileRenderer extends ProjectileEntityRenderer<EchoShardClusterProjectileEntity, ProjectileEntityRenderState> {
    protected EchoShardClusterProjectileModel model;

    public EchoShardClusterProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new EchoShardClusterProjectileModel(context.getPart(EchoShardClusterProjectileModel.ECHO_SHARD_CLUSTER));
    }

    @Override
    public void render(ProjectileEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw - 90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch - 90));

        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers,
                this.model.getLayer(Identifier.of(AncientExplosives.MOD_ID, "textures/entity/echo_shard_cluster/echo_shard_cluster.png")), false, false);
        this.model.setAngles(state);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }

    @Override
    protected Identifier getTexture(ProjectileEntityRenderState state) {
        return Identifier.of(AncientExplosives.MOD_ID, "textures/entity/echo_shard_cluster/echo_shard_cluster.png");
    }

    @Override
    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }
}
