package net.tomeoprod.ancient_explosives.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tomeoprod.ancient_explosives.mixin.ParticleAccessor;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;

public class GibParticleBase extends SpriteBillboardParticle {
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private final float rotationXmod;
    private final float rotationYmod;
    private final float rotationZmod;
    private final float groundOffset;


    public GibParticleBase(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
        super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed);

        this.gravityStrength = 1f;
        this.scale = 0.1f + new Random().nextFloat(0.15f);
        this.maxAge = new Random().nextInt(100, 200);
        this.setSpriteForAge(spriteProvider);

        this.velocityX = velocityX * 10f;
        this.velocityY = velocityY * 10f;
        this.velocityZ = velocityZ * 10f;
        this.velocityMultiplier = 0.5f;

        this.rotationX = world.getRandom().nextFloat() * 360f;
        this.rotationY = world.getRandom().nextFloat() * 360f;
        this.rotationZ = world.getRandom().nextFloat() * 360f;
        this.rotationXmod = world.getRandom().nextFloat() * 10f * (random.nextBoolean() ? -1 : 1);
        this.rotationYmod = world.getRandom().nextFloat() * 10f * (random.nextBoolean() ? -1 : 1);
        this.rotationZmod = world.getRandom().nextFloat() * 10f * (random.nextBoolean() ? -1 : 1);

        this.groundOffset = world.getRandom().nextFloat() / 100f + 0.001f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickProgress) {
        Vec3d cameraPos = camera.getPos();
        float x = (float) (MathHelper.lerp(tickProgress, this.lastX, this.x) - cameraPos.getX());
        float y = (float) (MathHelper.lerp(tickProgress, this.lastY, this.y) - cameraPos.getY());
        float z = (float) (MathHelper.lerp(tickProgress, this.lastZ, this.z) - cameraPos.getZ());

        Vector3f[] Vec3fs = new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0)};
        float siZe = this.getSize(tickProgress);

        if (!this.onGround) {
            rotationX += rotationXmod;
            rotationY += rotationYmod;
            rotationZ += rotationZmod;

            for (int k = 0; k < 4; ++k) {
                Vector3f Vec3f2 = Vec3fs[k];
                Vec3f2.rotate(eulerToQuaternion(rotationX, rotationY, rotationZ));
                Vec3f2.normalize(siZe);
                Vec3f2.add(x, y, z);
            }
        } else {
            rotationX = 90f;
            rotationY = 0;

            for (int k = 0; k < 4; ++k) {
                Vector3f Vec3f2 = Vec3fs[k];
                Vec3f2.rotate(eulerToQuaternion(rotationX, rotationY, rotationZ));
                Vec3f2.normalize(siZe);
                Vec3f2.add(x, y + this.groundOffset, z);
            }
        }

        float minU = this.getMinU();
        float maxU = this.getMaxU();
        float minV = this.getMinV();
        float maxV = this.getMaxV();
        int light = this.getBrightness(tickProgress);

        vertexConsumer.vertex(Vec3fs[0].x(), Vec3fs[0].y(), Vec3fs[0].z()).texture(maxU, maxV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[1].x(), Vec3fs[1].y(), Vec3fs[1].z()).texture(maxU, minV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[2].x(), Vec3fs[2].y(), Vec3fs[2].z()).texture(minU, minV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[3].x(), Vec3fs[3].y(), Vec3fs[3].z()).texture(minU, maxV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[0].x(), Vec3fs[0].y(), Vec3fs[0].z()).texture(maxU, maxV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[3].x(), Vec3fs[3].y(), Vec3fs[3].z()).texture(maxU, minV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[2].x(), Vec3fs[2].y(), Vec3fs[2].z()).texture(minU, minV).color(red, green, blue, alpha).light(light);
        vertexConsumer.vertex(Vec3fs[1].x(), Vec3fs[1].y(), Vec3fs[1].z()).texture(minU, maxV).color(red, green, blue, alpha).light(light);
    }

    public Quaternionf eulerToQuaternion(float x, float y, float z) {
        x *= ((float) Math.PI / 180F);
        y *= ((float) Math.PI / 180F);
        z *= ((float) Math.PI / 180F);

        float f = MathHelper.sin(0.5F * x);
        float g = MathHelper.cos(0.5F * x);
        float h = MathHelper.sin(0.5F * y);
        float i = MathHelper.cos(0.5F * y);
        float j = MathHelper.sin(0.5F * z);
        float k = MathHelper.cos(0.5F * z);
        x = f * i * k + g * h * j;
        y = g * h * k - f * i * j;
        z = f * h * k + g * i * j;
        float w = g * i * k - f * h * j;

        return new Quaternionf(x, y, z, w);
    }

    @Override
    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            if (this.world.getFluidState(BlockPos.ofFloored(this.x, (this.y + 0.2), this.z)).isEmpty()) {
                if (this.world.getFluidState(BlockPos.ofFloored(this.x, (this.y - 0.01), this.z)).isIn(FluidTags.WATER)) {
                    this.onGround = true;
                    this.velocityY = 0;
                } else {
                    this.velocityY -= 0.04D * (double) this.gravityStrength;
                    ((ParticleAccessor) this).setStopped(false);
                    this.move(this.velocityX, this.velocityY, this.velocityZ);
                    if (this.ascending && this.y == this.lastY) {
                        this.velocityX *= 1.1D;
                        this.velocityZ *= 1.1D;
                    }

                    this.velocityX *= this.velocityMultiplier;
                    this.velocityY *= this.velocityMultiplier;
                    this.velocityZ *= this.velocityMultiplier;

                    this.velocityMultiplier = Math.min(0.98f, this.velocityMultiplier * 1.15f);

                    if (this.onGround) {
                        this.velocityX *= 0.699999988079071D;
                        this.velocityZ *= 0.699999988079071D;
                    }
                }
            } else {
                this.markDead();
            }
        }
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new GibParticleBase(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
        }
    }
}
