package org.relaymodding.petcollecting.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.relaymodding.petcollecting.items.PetItem;

public class InventoryFunctions {
    public static boolean doesInventoryContainPet(Player player, PetItem petItem) {
        ItemStack offhandItemStack = player.getOffhandItem();
        if (offhandItemStack.getItem().equals(petItem)) {
            return true;
        }

		Inventory inv = player.getInventory();
		for (int i = 0; i < 36; i++) {
			ItemStack slotItemStack = inv.getItem(i);
            if (slotItemStack.getItem().equals(petItem)) {
                return true;
            }
		}

        return false;
    }
}
