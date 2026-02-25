package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
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
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (pearlKey != null && pearlKey.wasPressed()) {
                usePearl(client);
            }
        });
    }

    private void usePearl(MinecraftClient client) {
        if (client.player == null) return;

        int previousSlot = client.player.getInventory().selectedSlot;

        for (int i = 0; i < 9; i++) {
            if (client.player.getInventory().getStack(i).getItem() == Items.ENDER_PEARL) {
                client.player.getInventory().selectedSlot = i;
                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
                client.player.getInventory().selectedSlot = previousSlot;
                break;
            }
        }
    }
}