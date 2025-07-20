package net.tomeoprod.ancient_explosives.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.tomeoprod.ancient_explosives.util.ModDamageTypes;

import java.awt.*;

public class ShardsEffect extends StatusEffect {
    protected ShardsEffect() {
        super(StatusEffectCategory.HARMFUL, Color.HSBtoRGB(181, 65, 31));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity.getMovement().length() > 0.1) {
            entity.damage(world, world.getDamageSources().create(ModDamageTypes.INTERNAL_BLEEDING_DAMAGE_KEY), 0.25f * amplifier);
        }
        return super.applyUpdateEffect(world, entity, amplifier);
    }
}
