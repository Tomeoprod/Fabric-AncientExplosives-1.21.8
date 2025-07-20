package net.tomeoprod.ancient_explosives.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.tomeoprod.ancient_explosives.networking.packet.ChestplateActivationC2SPacket;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_ANCIENT_EXPLOSIVES = "key.category.ancient_explosives";
    public static final String KEY_CHESTPLATE_ACTIVATION = "key.ancient_explosives.chestplate_activation";

    public static KeyBinding chestplateActivationKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (chestplateActivationKey.wasPressed()) {
                ClientPlayNetworking.send(new ChestplateActivationC2SPacket(true));
            }
        });
    }

    public static void register() {
        chestplateActivationKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_CHESTPLATE_ACTIVATION,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY_ANCIENT_EXPLOSIVES
        ));

        registerKeyInputs();
    }

}
