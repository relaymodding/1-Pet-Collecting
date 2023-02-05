package org.relaymodding.petcollecting.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.relaymodding.petcollecting.Main;

public class MessageFunctions {
    public static void sendMessage(Player player, String messageString, ChatFormatting colour) {
        sendMessage(player, messageString, colour, false);
    }
    public static void sendMessage(Player player, String messageString, ChatFormatting colour, boolean emptyline) {
        if (!Main.config.sendChatMessages) {
            return;
        }

        if (messageString.isEmpty()) {
            return;
        }

        MutableComponent message = Component.literal(messageString);
        message.withStyle(colour);

        if (emptyline) {
            player.sendSystemMessage(Component.literal(""));
        }

        player.sendSystemMessage(message);
    }

    public static void sendClientMessage(Player player, String messageString, ChatFormatting colour) {
        if (!Main.config.sendClientMessages) {
            return;
        }

        if (messageString.isEmpty()) {
            return;
        }

        MutableComponent message = Component.literal(messageString);
        message.withStyle(colour);

        player.displayClientMessage(message, true);
    }
}
