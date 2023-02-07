package org.relaymodding.petcollecting.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import org.relaymodding.petcollecting.Main;

import java.util.function.UnaryOperator;

public class MessageFunctions {
    public static void sendMessage(Player player, MutableComponent messageString) {
        sendMessage(player, messageString, false);
    }
    public static void sendMessage(Player player, MutableComponent message, boolean emptyline) {
        if (!Main.config.sendChatMessages) {
            return;
        }

        if (emptyline) {
            player.sendSystemMessage(CommonComponents.EMPTY);
        }

        player.sendSystemMessage(message);
    }

    public static void sendClientMessage(Player player, MutableComponent message) {
        if (!Main.config.sendClientMessages) {
            return;
        }

        player.displayClientMessage(message, true);
    }
}
