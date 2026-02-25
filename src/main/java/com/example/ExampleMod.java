package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ModInitializer {

    private static KeyBinding pearlKey;

    @Override
    public void onInitialize() {

        // Register Keybind (Default: R key)
        pearlKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.easypearl.throw",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.easypearl"
        ));

        // Listen for key press
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (pearlKey.wasPressed()) {
                usePearl(client);
            }
        });
    }

    private void usePearl(MinecraftClient client) {

        int previousSlot = client.player.getInventory().selectedSlot;

        // Search hotbar for ender pearl
        for (int i = 0; i < 9; i++) {
            if (client.player.getInventory().getStack(i).getItem() == Items.ENDER_PEARL) {

                // Switch to pearl slot
                client.player.getInventory().selectedSlot = i;

                // Throw pearl
                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);

                // Switch back to previous slot
                client.player.getInventory().selectedSlot = previousSlot;

                break;
            }
        }
    }
}