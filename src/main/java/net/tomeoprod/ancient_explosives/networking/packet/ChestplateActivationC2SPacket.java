package net.tomeoprod.ancient_explosives.networking.packet;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UseCooldownComponent;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tomeoprod.ancient_explosives.AncientExplosives;
import net.tomeoprod.ancient_explosives.component.ModComponents;
import net.tomeoprod.ancient_explosives.item.ModItems;

import java.util.List;

public record ChestplateActivationC2SPacket(boolean test) implements CustomPayload{
    public static final Identifier CHESTPLATE_ACTIVATION_ID = Identifier.of(AncientExplosives.MOD_ID, "chestplate_activation");
    public static final CustomPayload.Id<ChestplateActivationC2SPacket> ID = new CustomPayload.Id<>(CHESTPLATE_ACTIVATION_ID);
    public static final PacketCodec<RegistryByteBuf, ChestplateActivationC2SPacket> CODEC = PacketCodec.tuple(PacketCodecs.BOOLEAN, ChestplateActivationC2SPacket::test, ChestplateActivationC2SPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void sonicBoom(ServerWorld serverWorld, PlayerEntity player) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

        if (stack.isOf(ModItems.WARDEN_CHESTPLATE) && !player.getItemCooldownManager().isCoolingDown(stack)) {
            Vec3d playerPos = player.getPos().add(player.getAttachments().getPoint(EntityAttachmentType.WARDEN_CHEST, 0, player.getYaw()));
            Vec3d vec3d2 = player.getEyePos().add(player.getRotationVec(1.0F).multiply(20)).subtract(playerPos);
            Vec3d vec3d3 = vec3d2.normalize();
            int i = MathHelper.floor(vec3d2.length()) + 7;
            List<LivingEntity> livingEntities = null;

            for (int j = 1; j < i; j++) {
                Vec3d vec3d4 = playerPos.add(vec3d3.multiply(j));
                serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0);

                Box box = new Box(vec3d4.x - 1, vec3d4.y - 1, vec3d4.z - 1, vec3d4.x + 1, vec3d4.y + 1, vec3d4.z + 1);
                List<LivingEntity> livingEntitiesTemp = serverWorld.getEntitiesByClass(LivingEntity.class, box, entity -> true);
                if (livingEntities == null) {
                    livingEntities = livingEntitiesTemp;
                } else livingEntities.addAll(livingEntitiesTemp);
            }

            serverWorld.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.NEUTRAL, 3f, 1f);

            if (livingEntities != null) {
                for (LivingEntity target : livingEntities) {
                    if (target != player && target.canTakeDamage()) {
                        if (target.damage(serverWorld, serverWorld.getDamageSources().sonicBoom(player), 10.0F)) {
                            double d = (double) 0.5F * ((double) 1.0F - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
                            double e = (double) 2.5F * ((double) 1.0F - target.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
                            target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
                        }
                    }
                }
            }

            if (!player.isCreative()) {
                player.getItemCooldownManager().set(stack, 200);
            }

        }
    }
}
