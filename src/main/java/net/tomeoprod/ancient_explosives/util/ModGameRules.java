package net.tomeoprod.ancient_explosives.util;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> FUN_TIME =
            GameRuleRegistry.register("fun_time", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));

    public static void register() {}
}
