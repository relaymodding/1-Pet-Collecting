package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;

public class DirtPetAbility implements PetAbility {
	public static final String PROGRESS = "progress";
	
    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
    	if (itemStack.getOrCreateTag().getInt(PROGRESS) >= Main.config.dirtToStarProgress) {
    		itemStack.getTag().putInt(PROGRESS, 0);
    		//we might need a catch for a full inventory.  i can't remember if that's handled natively or not
    		player.addItem(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.config.dirtReward)))); 
    	}
    	else {
    		int adjustedHP = itemStack.getMaxDamage() - 1 - itemStack.getDamageValue();
    		itemStack.setDamageValue(itemStack.getMaxDamage() - 1);
    		itemStack.getTag().putInt(PROGRESS, itemStack.getTag().getInt(PROGRESS) + adjustedHP);
    	}
        return true;
    }

    @Override
    public String descriptionId() {
        return "dirt";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
