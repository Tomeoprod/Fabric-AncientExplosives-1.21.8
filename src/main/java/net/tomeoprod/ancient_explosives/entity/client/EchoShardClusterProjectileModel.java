package net.tomeoprod.ancient_explosives.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.tomeoprod.ancient_explosives.AncientExplosives;

public class EchoShardClusterProjectileModel extends EntityModel<EntityRenderState> {
	public static final EntityModelLayer ECHO_SHARD_CLUSTER = new EntityModelLayer(Identifier.of(AncientExplosives.MOD_ID, "echo_shard_cluster"), "main");
	private final ModelPart bb_main;


	public EchoShardClusterProjectileModel(ModelPart root) {
        super(root);
        this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(8, 14).cuboid(-2.5F, -2.0F, -2.0F, 5.0F, 2.0F, 4.1F, new Dilation(0.0F)), ModelTransform.rotation(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = bb_main.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -5.7654F, -1.1658F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.7346F, -2.8342F, 0.3927F, 0.0F, 0.0F));

		ModelPartData cube_r2 = bb_main.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.265F, -6.0758F, -1.8384F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.735F, -6.4242F, 1.8384F, -0.2618F, 0.5236F, -0.5236F));

		ModelPartData cube_r3 = bb_main.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.486F, -5.7223F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.986F, -6.7777F, 1.5F, -0.3316F, -0.6981F, 0.4712F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(EntityRenderState state) {
		super.setAngles(state);
	}
}